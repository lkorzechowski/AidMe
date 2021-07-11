package com.orzechowski.aidme.tutorial.mediaplayer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tools.AssetObtainer;
import com.orzechowski.aidme.tutorial.mediaplayer.database.Multimedia;
import com.orzechowski.aidme.tutorial.mediaplayer.database.MultimediaViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MultimediaPlayer extends Fragment
{
    VideoView mVideoView;
    ImageView mImageView;
    MultimediaViewModel mMultimediaViewModel;
    Play mPlayThread;
    Activity mActivity;
    AssetObtainer assetObtainer = new AssetObtainer();
    public Long mTutorialId;
    public List<Multimedia> multimedias = new LinkedList<>();

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
        mMultimediaViewModel = new ViewModelProvider(this).get(MultimediaViewModel.class);
        mVideoView = view.findViewById(R.id.video_embed);
        mImageView = view.findViewById(R.id.image_embed);
        getPlayer(0);
    }

    private void getPlayer(int position)
    {
        if(mPlayThread != null) {
            mPlayThread.interrupt();
            position++;
        }
        if(!multimedias.isEmpty()) {
            mPlayThread = new Play(multimedias.get(position));
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
            if(currentMedia.getType()) {
                mActivity.runOnUiThread(() -> {
                    mImageView.setVisibility(View.VISIBLE);
                    mVideoView.setVisibility(View.GONE);
                    try {
                        Picasso.get().load(assetObtainer.getFileFromAssets(requireContext(), currentMedia.getFullFileName())).into(mImageView);
                    } catch (IOException ignored) {}
                });
                if(displayTime>0) {
                    try {
                        sleep(displayTime);
                        if(!loopBool) multimedias.remove(currentMedia);
                        if(position<multimedias.size()-1) {
                            getPlayer(position);
                        } else getPlayer(0);
                    } catch (InterruptedException e) {
                        mActivity.runOnUiThread(() -> mImageView.setVisibility(View.GONE));
                        interrupt();
                    }
                }
            } else {
                mActivity.runOnUiThread(() -> {
                    mVideoView.setVisibility(View.VISIBLE);
                    mImageView.setVisibility(View.GONE);
                    try {
                        mVideoView.setVideoURI(Uri.fromFile(assetObtainer.getFileFromAssets(requireContext(), currentMedia.getFullFileName())));
                    } catch (IOException ignored) {}
                    if(loopBool && multimedias.size()==1) mVideoView.setOnCompletionListener(v->getPlayer(position-1));
                    mVideoView.start();
                });
            }
        }
    }

    @Override
    public void onPause()
    {
        if(mPlayThread!=null){
            mPlayThread.interrupt();
        }
        super.onPause();
    }
}
