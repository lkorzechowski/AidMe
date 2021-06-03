package com.orzechowski.prototyp

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.orzechowski.prototyp.database.tutorial.Tutorial
import com.orzechowski.prototyp.database.tutorial.TutorialViewModel
import com.orzechowski.prototyp.tutorial.InstructionsRecycler
import com.orzechowski.prototyp.tools.GetResId
import java.lang.Thread.sleep
import kotlin.properties.Delegates

class TutorialActivity : AppCompatActivity(R.layout.activity_tutorial)
{
    private var mTutorialId by Delegates.notNull<Long>()
    private var mResourceId by Delegates.notNull<Int>()
    private var mLoop by Delegates.notNull<Boolean>()
    private var mLoopInterval by Delegates.notNull<Long>()
    private var mDelay by Delegates.notNull<Long>()
    private var mTutorial: Tutorial? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_tutorial)
        supportActionBar?.hide()

        this.mTutorialId = intent.extras?.getLong("tutorialId") ?: -1L
        this.mResourceId = GetResId.getResId("g$mTutorialId", R.raw::class.java)

        Thread {
            this.mTutorial = ViewModelProvider(this).get(TutorialViewModel::class.java).getByTutorialId(this.mTutorialId)
        }

        val videoEmbed: VideoView = findViewById(R.id.video_embed)
        //val imageEmbed: ImageView = findViewById(R.id.image_embed)
        videoEmbed.setVideoPath("android.resource://" + packageName + "/" + R.raw.cpr_video)
        videoEmbed.start()
        videoEmbed.setOnCompletionListener { videoEmbed.start() }

        val bundle = intent.extras!!
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<InstructionsRecycler>(R.id.layout_instructions_list, args = bundle)
        }
        play()
    }

    private fun play(){

        if(mTutorial==null){
            Thread{
                sleep(10)
                play()
            }
            return
        }

        this.mDelay = mTutorial!!.globalSoundStart
        this.mLoop = mTutorial!!.globalSoundLoop
        this.mLoopInterval = mTutorial!!.globalSoundLoopInterval

        if(intent.extras?.getBoolean("versionSkipDelay")==true) {
            Thread{
                sleep(mDelay)
                play()
            }
            return
        }

        val globalSound = GlobalSound(mResourceId, mLoopInterval, this)
        globalSound.start()
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