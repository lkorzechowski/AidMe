package com.orzechowski.aidme.tutorial.mediaplayer.sound

import android.app.Activity
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.core.net.toUri
import com.orzechowski.aidme.tools.AssetObtainer
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSound
import java.util.*
import kotlin.collections.ArrayList

class SoundAdapter (private val mDelayGlobalSound: Boolean,
                    private val mVersionSounds: String,
                    private val mActivity: Activity)
{
    var mSounds: List<TutorialSound> = LinkedList()
    private val mThreads: ArrayList<Thread> = ArrayList()
    private var mInit = true
    private val assetObtainer = AssetObtainer()

    fun setData(sounds: List<TutorialSound>)
    {
        mSounds = sounds
    }

    fun deploy()
    {
        for(i in mSounds.indices) {
            if(mVersionSounds.contains(i.toString(), true))
            {
                mThreads.add(Thread {
                    if (mDelayGlobalSound) {
                        try {
                            Thread.sleep(mSounds[i].soundStart)
                        } catch (e: InterruptedException) {
                            Thread.interrupted()
                            return@Thread
                        }
                    }
                    val sound: TutorialSound = mSounds[i]
                    val resourceUri: Uri = assetObtainer
                        .getFileFromAssets(mActivity, sound.fileName).toUri()
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
                })
                mThreads[i].start()
            }
        }
    }

    fun destroy()
    {
        for(thread in mThreads) thread.interrupt()
        mThreads.clear()
    }
}
