package com.orzechowski.aidme.tutorial.sound

import android.app.Application
import android.content.Context
import com.orzechowski.aidme.tools.SoundPlayback

class SoundAdapter (private val mVersionId: Long,
                    private val mContext: Context,
                    private val mDelayGlobalSound: Boolean,
                    private val mVersionSounds: String)
{
    private lateinit var mSounds: List<TutorialSound>
    private val mThreads: ArrayList<Thread> = ArrayList()
    private var mInit = true
    private val soundPlayback: SoundPlayback = SoundPlayback()

    fun deploy()
    {
        val soundViewModel = TutorialSoundViewModel(Application())
        mSounds = soundViewModel.getByTutorialId(mVersionId)
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
                    try {
                        while(mSounds[i].soundLoop || mInit) {
                            mInit = false
                            soundPlayback.play("g$mVersionId"+"_$i", mContext, 0.5F)
                            Thread.sleep(mSounds[i].interval)
                            soundPlayback.release()
                        }
                    } catch (e: InterruptedException) {
                        soundPlayback.release()
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