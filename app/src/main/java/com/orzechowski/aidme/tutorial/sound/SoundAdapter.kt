package com.orzechowski.aidme.tutorial.sound

import android.app.Activity
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.core.net.toUri
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class SoundAdapter (private val mVersionId: Long,
                    private val mDelayGlobalSound: Boolean,
                    private val mVersionSounds: String,
                    private val mActivity: Activity) {
    var mSounds: List<TutorialSound> = LinkedList()
    private val mThreads: ArrayList<Thread> = ArrayList()
    private var mInit = true

    fun setData(sounds: List<TutorialSound>)
    {
        mSounds = sounds
    }

    fun deploy()
    {
        for(i in mSounds.indices){
            if(mVersionSounds.contains(i.toString(), true))
            {
                mThreads.add(Thread {
                    if (mDelayGlobalSound) {
                        try{
                            Thread.sleep(mSounds[i].soundStart)
                        } catch (e: InterruptedException) {
                            Thread.interrupted()
                            return@Thread
                        }
                    }
                    val resourceUri: Uri = getFileFromAssets(mActivity,"g$mVersionId"+"_$i"+".mp3").toUri()
                    lateinit var player: MediaPlayer
                    try {
                        while(mSounds[i].soundLoop || mInit) {
                            player = MediaPlayer.create(mActivity, resourceUri)
                            player.setAudioAttributes(AudioAttributes.Builder()
                                .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                                .setLegacyStreamType(AudioManager.USE_DEFAULT_STREAM_TYPE)
                                .setUsage(AudioAttributes.USAGE_ALARM)
                                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                .build())
                            player.setVolume(0.5f, 0.5f)
                            player.start()
                            Thread.sleep(mSounds[i].interval)
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

    @Throws(IOException::class)
    fun getFileFromAssets(context: Context, fileName: String): File = File(context.cacheDir, fileName)
        .also {
            if (!it.exists()) {
                it.outputStream().use { cache ->
                    context.assets.open(fileName).use { inputStream ->
                        inputStream.copyTo(cache)
                    }
                }
            }
        }
}