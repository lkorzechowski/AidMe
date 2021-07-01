package com.orzechowski.aidme.tutorial.sound

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.core.net.toUri
import java.io.File
import java.io.IOException

class SoundAdapter (private val mVersionId: Long,
                    private val mContext: Context,
                    private val mDelayGlobalSound: Boolean,
                    private val mVersionSounds: String)
{
    private lateinit var mSounds: List<TutorialSound>
    private val mThreads: ArrayList<Thread> = ArrayList()
    private var mInit = true
    private val mAssetManager: AssetManager = mContext.assets

    fun deploy()
    {
        val soundViewModel = TutorialSoundViewModel(Application())
        mSounds = soundViewModel.getByTutorialId(mVersionId)
        for(i in mSounds.indices){
            if(mVersionSounds.contains(i.toString(), true))
            {
                //public String chk_path(String filePath)
                //{
                ////create array of extensions
                //String[] ext=new String[]{".mkv",".mpg"}; //You can add more as you require
                //
                ////Iterate through array and check your path which extension with your path exists
                //
                //String path=null;
                //for(int i=0;i<ext.Length;i++)
                //{
                //  File file = new File(filePath+ext[i]);
                //  if(file.exists())
                //    {
                //     //if it exists then combine the extension
                //     path=filePath+ext[i];
                //     break;
                //    }
                //}
                //return path;
                //}
                mThreads.add(Thread {
                    if (mDelayGlobalSound) {
                        try{
                            Thread.sleep(mSounds[i].soundStart)
                        } catch (e: InterruptedException) {
                            Thread.interrupted()
                            return@Thread
                        }
                    }
                    val resourceUri: Uri = getFileFromAssets(mContext,"g$mVersionId"+"_$i"+".mp3").toUri()
                    lateinit var player: MediaPlayer
                    try {
                        while(mSounds[i].soundLoop || mInit) {
                            player = MediaPlayer.create(mContext, resourceUri)
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