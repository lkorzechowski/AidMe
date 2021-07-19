package com.orzechowski.aidme.tutorial.mediaplayer.multimedia;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tools.AssetObtainer;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MultimediaPlayer extends Fragment
{
    VideoView mVideoView;
    ImageView mImageView;
    Play mPlayThread;
    Activity mActivity;
    AssetObtainer assetObtainer = new AssetObtainer();
    public Long mTutorialId;
    List<Multimedia> mMultimedias = new LinkedList<>();
    
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_multimedia_player, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        mActivity = requireActivity();
        mVideoView = view.findViewById(R.id.video_embed);
        mImageView = view.findViewById(R.id.image_embed);
    }

    public void appendMultimedia(Multimedia media)
    {
        mMultimedias.add(media);
    }

    public void getPlayer(int position)
    {
        if(mPlayThread != null) {
            mPlayThread.interrupt();
            position++;
        }

        if(!mMultimedias.isEmpty()) {
            if(position==mMultimedias.size()){
                mPlayThread = new Play(mMultimedias.get(0));
            } else mPlayThread = new Play(mMultimedias.get(position));
            mPlayThread.start();
        }
    }

    private class Play extends Thread
    {
        private final Multimedia currentMedia;

        Play(Multimedia media){
            currentMedia = media;
        }

        @Override
        public void run()
        {
            int position = currentMedia.getPosition();
            int displayTime = currentMedia.getDisplayTime();
            boolean loopBool = currentMedia.getLoop();
            int size = mMultimedias.size();
            if(!loopBool) mMultimedias.remove(currentMedia);
            if(currentMedia.getType()) {
                mActivity.runOnUiThread(() -> {
                    mImageView.setVisibility(View.VISIBLE);
                    mVideoView.setVisibility(View.GONE);
                    try {
                        mImageView.setImageURI(Uri.fromFile(assetObtainer
                                .getFileFromAssets(requireContext(), currentMedia.getFullFileName())));
                    } catch (IOException ignored) {}
                });
                if(displayTime>0) {
                    try {
                        sleep(displayTime);
                        getPlayer(position);
                    } catch (InterruptedException e) {
                        mActivity.runOnUiThread(() -> mImageView.setVisibility(View.GONE));
                        interrupt();
                    }
                }
            } else if(!currentMedia.getType()) {
                mActivity.runOnUiThread(() -> {
                    mVideoView.setVisibility(View.VISIBLE);
                    mImageView.setVisibility(View.GONE);
                    try {
                        mVideoView.setVideoURI(Uri.fromFile(assetObtainer
                                .getFileFromAssets(requireContext(), currentMedia.getFullFileName())));
                    } catch (IOException ignored) {}
                    if(loopBool && size==1) mVideoView.setOnCompletionListener(v->getPlayer(-1));
                    else {
                        if(position<size-1) mVideoView.setOnCompletionListener(v->getPlayer(position));
                        else getPlayer(-1);
                    }
                    mVideoView.start();
                });
            }
        }
    }

    @Override
    public void onPause()
    {
        if(mPlayThread!=null) {
            mPlayThread.interrupt();
        }
        super.onPause();
    }
}
