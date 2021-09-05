package com.orzechowski.aidme.tutorial.mediaplayer.sound

import android.app.Activity
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSound
import java.util.*
import kotlin.collections.ArrayList

class SoundAdapter (private val mDelayGlobalSound: Boolean,
                    private val mVersionSounds: List<Long>,
                    private val mSounds: List<TutorialSound>,
                    private val mActivity: Activity)
{
    private val mThreads: ArrayList<Thread> = ArrayList()
    private var mInit = true

    fun deploy()
    {
        for(i in mSounds) {
            if(mVersionSounds.contains(i.soundId)) {
                mThreads.add(Thread {
                    if (mDelayGlobalSound) {
                        try {
                            Thread.sleep(i.soundStart)
                        } catch (e: InterruptedException) {
                            Thread.interrupted()
                            return@Thread
                        }
                    }
                    val sound: TutorialSound = i
                    val resourceUri: Uri = Uri.parse(sound.fileUriString)
                    lateinit var player: MediaPlayer
                    try {
                        while(sound.soundLoop || mInit) {
                            player = MediaPlayer.create(mActivity, resourceUri)
                            player.setAudioAttributes(AudioAttributes.Builder()
                                .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                                .setLegacyStreamType(AudioManager.USE_DEFAULT_STREAM_TYPE)
                                .setUsage(AudioAttributes.USAGE_ALARM)
                                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                .build())
                            player.setVolume(0.5f, 0.5f)
                            player.start()
                            Thread.sleep(sound.interval)
                            player.release()
                        }
                    } catch (e: InterruptedException) {
                        player.stop()
                        player.release()
                        Thread.interrupted()
                    }
                }.also {
                    it.start()
                })
            }
        }
    }

    fun destroy()
    {
        for(thread in mThreads) thread.interrupt()
        mThreads.clear()
    }
}
