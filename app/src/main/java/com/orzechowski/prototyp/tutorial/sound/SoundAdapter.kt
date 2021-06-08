package com.orzechowski.prototyp.tutorial.sound

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import com.orzechowski.prototyp.R
import com.orzechowski.prototyp.tools.GetResId

class SoundAdapter (private val mVersionId: Long,
                    private val mContext: Context,
                    private val mDelayGlobalSound: Boolean,
                    private val mVersionSounds: String)
{
    private lateinit var mSounds: List<TutorialSound>
    private val mThreads: ArrayList<Thread> = ArrayList()

    fun deploy(){
        val soundViewModel = TutorialSoundViewModel(Application())
        mSounds = soundViewModel.getByTutorialId(mVersionId)
        for(i in mSounds.indices){
            if(mVersionSounds.contains(i.toString(), true))
            {
                val resourceId = GetResId.getResId("g$mVersionId"+"_"+i, R.raw::class.java)
                mThreads.add(Thread {
                    val loop = mSounds[i].soundLoop
                    if (mDelayGlobalSound) {
                        try{
                            Thread.sleep(mSounds[i].soundStart)
                        } catch (e: InterruptedException){
                            Thread.interrupted()
                        }
                    }
                    var player = MediaPlayer.create(mContext, resourceId)
                    try {
                        player.setVolume(0.5f, 0.5f)
                        player.start()
                        Thread.sleep(mSounds[i].interval)
                        player.release()
                        while(loop){
                            player = MediaPlayer.create(mContext, resourceId)
                            player.setVolume(0.5f, 0.5f)
                            player.start()
                            Thread.sleep(mSounds[i].interval)
                            player.release()
                        }
                    } catch (e: InterruptedException) {
                        player.release()
                        Thread.interrupted()
                    }
                })
                mThreads[i].start()
            }
        }
    }

    fun destroy(){
        for(thread in mThreads) thread.interrupt()
    }
}