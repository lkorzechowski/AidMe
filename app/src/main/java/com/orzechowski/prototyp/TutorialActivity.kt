package com.orzechowski.prototyp

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.orzechowski.prototyp.database.tutorial.TutorialViewModel
import com.orzechowski.prototyp.instructions.InstructionsRecycler
import com.orzechowski.prototyp.tools.GetResId
import kotlin.properties.Delegates

class TutorialActivity : AppCompatActivity(R.layout.activity_tutorial)
{
    private var mTutorialId by Delegates.notNull<Long>()
    private var mResourceId by Delegates.notNull<Int>()
    private var mLoop = false
    private var mLoopInterval = 0L
    private var mDelay = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_tutorial)
        supportActionBar?.hide()

        this.mTutorialId = intent.extras?.getLong("tutorialId") ?: -1L

        val videoEmbed: VideoView = findViewById(R.id.video_embed)
        val imageEmbed: ImageView = findViewById(R.id.image_embed)
        videoEmbed.setVideoPath("android.resource://" + packageName + "/" + R.raw.cpr_video)
        videoEmbed.start()
        videoEmbed.setOnCompletionListener { videoEmbed.start() }

        val viewModel: TutorialViewModel = ViewModelProvider(this@TutorialActivity)
            .get(TutorialViewModel::class.java)

        viewModel.getByTutorialId(mTutorialId)
            .observe(this, { tutorial ->
                mDelay = tutorial[0].globalSoundStart.toLong()
                mLoop = tutorial[0].globalSoundLoop
                mLoopInterval = tutorial[0].globalSoundLoopInterval.toLong()
            })

        mResourceId = GetResId.getResId("g$mTutorialId", R.raw::class.java)

        if(intent.extras?.getBoolean("versionSkipDelay")==true) {
            val sleepOutDelay = SleepOutDelay(mDelay, this)
            sleepOutDelay.start()
        } else play()

        val bundle = intent.extras!!
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<InstructionsRecycler>(R.id.layout_instructions_list, args = bundle)
        }
    }

    private fun play(){
        val globalSound = GlobalSound(mResourceId, mLoopInterval, this)
        globalSound.start()
    }

    class SleepOutDelay(private val delay: Long, private val activity: TutorialActivity) : Thread(){
        override fun start(){
            try {
                sleep(delay)
                activity.play()
            } catch (e: IllegalStateException) {
                interrupt()
            }
        }
    }

    class GlobalSound(private val resourceId: Int, private val interval: Long,
                      private val activity: TutorialActivity) : Thread()
    {

        override fun start(){
            val mPlayer: MediaPlayer = MediaPlayer.create(activity, resourceId)
            mPlayer.isLooping = false
            mPlayer.setVolume(1f, 1f)
            try {
                mPlayer.start()
                sleep(interval)
                if(activity.mLoop) activity.play()
                mPlayer.stop()
                interrupt()
            } catch (e: IllegalStateException) {
                mPlayer.stop()
                interrupt()
            } catch (e: InterruptedException) {
                mPlayer.stop()
                interrupt()
            }
        }
    }
}