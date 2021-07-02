package com.orzechowski.aidme.tools;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.File;

public class SoundPlayback {

    AssetFinder assetFinder = new AssetFinder();
    MediaPlayer mediaPlayer;

    public void play(String fileNameNoExtension, Context context, float volume)
    {
        int attempt = 0;
        boolean matchFound = false;
        while(attempt < 2 && !matchFound){
            File file = assetFinder.getFileFromAssets(context, fileNameNoExtension, attempt, 0);
            Uri uri = Uri.fromFile(file);
            try{
                mediaPlayer = MediaPlayer.create(context, uri);
                matchFound = true;
            } catch(Exception e) {
                Log.w("ATTEMPT", String.valueOf(attempt));
                e.printStackTrace();
            }
            attempt++;
        }
        if(attempt==2) return;
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                .setLegacyStreamType(AudioManager.USE_DEFAULT_STREAM_TYPE)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build());
        mediaPlayer.setLooping(false);
        mediaPlayer.setVolume(volume, volume);
        mediaPlayer.start();
    }

    public void release(){
        if(mediaPlayer!=null) mediaPlayer.release();
    }
}
