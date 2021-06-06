package com.orzechowski.prototyp

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.orzechowski.prototyp.tutorial.database.Tutorial
import com.orzechowski.prototyp.tutorial.database.TutorialViewModel
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
    private var mDelayBool by Delegates.notNull<Boolean>()
    private var mBootup: Boolean = true
    private lateinit var runnable: Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_tutorial)
        supportActionBar?.hide()

        this.mTutorialId = intent.extras?.getLong("tutorialId") ?: -1L
        this.mDelayBool = intent.extras?.getBoolean("versionSkipDelay") ?: false
        this.mResourceId = GetResId.getResId("g$mTutorialId", R.raw::class.java)

        Thread {
            this.mTutorial = ViewModelProvider(this)
                .get(TutorialViewModel::class.java)
                .getByTutorialId(this.mTutorialId)
        }.start()

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
                sleep(4)
                play()
            }.start()
        } else if (mBootup) {
            this.mDelay = mTutorial!!.globalSoundStart
            this.mLoop = mTutorial!!.globalSoundLoop
            this.mLoopInterval = mTutorial!!.globalSoundLoopInterval
            mBootup = false
            play()
        } else {
            runnable = Thread {
                if (!mDelayBool) {
                    sleep(mDelay)
                    mDelayBool=false
                }
                val player = MediaPlayer.create(this, mResourceId)
                try {
                    player.setVolume(0.5f, 0.5f)
                    player.start()
                    sleep(mLoopInterval)
                    player.stop()
                    player.release()
                    if (mLoop) play()
                } catch (e: InterruptedException) {
                    player.stop()
                    player.release()
                }
            }
            runnable.start()
        }
    }

    override fun onStop() {
        super.onStop()
        if(runnable.isAlive) runnable.interrupt()
    }
}