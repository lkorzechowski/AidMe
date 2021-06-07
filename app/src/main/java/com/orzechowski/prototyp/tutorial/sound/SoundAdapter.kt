package com.orzechowski.prototyp.tutorial.sound

import android.app.Application
import android.media.MediaPlayer
import com.orzechowski.prototyp.R
import com.orzechowski.prototyp.tools.GetResId
import com.orzechowski.prototyp.version.database.Version
import com.orzechowski.prototyp.version.database.VersionViewModel

class SoundAdapter (private val mVersionId: Long)
{
    private lateinit var mSounds: List<TutorialSound>
    private lateinit var mVersion: Version

    fun deploy(){
        val soundViewModel = TutorialSoundViewModel(Application())
        val versionViewModel = VersionViewModel(Application())
        mSounds = soundViewModel.getByTutorialId(mVersionId)
        mVersion = versionViewModel.getByVersionId(mVersionId)
        for(i in mSounds.indices){
            if(mVersion.sounds.contains(i.toChar(), false)) play(mSounds[i])
        }
    }

    fun destroy(){

    }

    private fun play(tutorialSound: TutorialSound){
        val resourceId = GetResId.getResId("g$mVersionId", R.raw::class.java)

        val runnable = Thread {
            if (!mVersion.skipDelayOnGlobalSound) {
                Thread.sleep(tutorialSound.soundStart)
            }
            var player = MediaPlayer.create(Application(), resourceId)
            try {
                player.setVolume(0.5f, 0.5f)
                player.start()
                Thread.sleep(tutorialSound.interval)
                player.stop()
                while(tutorialSound.soundLoop){
                    player = MediaPlayer.create(Application(), resourceId)
                    player.setVolume(0.5f, 0.5f)
                    player.start()
                    Thread.sleep(tutorialSound.interval)
                    player.stop()
                }
            } catch (e: InterruptedException) {
                player.stop()
                player.release()
            }
        }
        runnable.start()
    }
}