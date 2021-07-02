package com.orzechowski.aidme.tutorial.mediaplayer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tools.AssetObtainer;
import com.orzechowski.aidme.tutorial.database.Multimedia;
import com.orzechowski.aidme.tutorial.database.MultimediaViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Player extends Fragment
{
    VideoView mVideoView;
    ImageView mImageView;
    MultimediaViewModel mMultimediaViewModel;
    Long mTutorialId;
    Play mPlayThread;
    String mVersionMultimedias;
    Activity mActivity;
    AssetObtainer assetObtainer = new AssetObtainer();

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
        Bundle bundle = requireArguments();
        mTutorialId = bundle.getLong("tutorialId");
        mVersionMultimedias = bundle.getString("versionMultimedias");
        Log.w("versionMultimedias", mVersionMultimedias);
        mMultimediaViewModel = new ViewModelProvider(this).get(MultimediaViewModel.class);
        mVideoView = view.findViewById(R.id.video_embed);
        mImageView = view.findViewById(R.id.image_embed);
        preplay(0);
    }

    private void preplay(int position)
    {
        if (mPlayThread != null) {
            mPlayThread.interrupt();
            position++;
        }
        if(mVersionMultimedias.contains(String.valueOf(position))) getPlayer(position);
    }

    public void getPlayer(int position)
    {
        mMultimediaViewModel
                .getByPositionAndTutorialId(position, mTutorialId)
                .observe(requireActivity(), selected -> {
                    mPlayThread = new Play(selected);
                    mPlayThread.start();
                });
    }

    private class Play extends Thread
    {
        private final String fileName;
        private final Multimedia currentMedia;

        Play(Multimedia media){
            fileName = "m"+mTutorialId+"_"+media.getPosition()+".mp4";
            currentMedia = media;
        }

        @Override
        public void run()
        {
            if(currentMedia.getType()){
                mImageView.setVisibility(View.VISIBLE);
                mVideoView.setVisibility(View.INVISIBLE);
            } else {
                mActivity.runOnUiThread(() -> {
                    mVideoView.setVisibility(View.VISIBLE);
                    mImageView.setVisibility(View.INVISIBLE);
                    try {
                        mVideoView.setVideoURI(Uri.fromFile(assetObtainer.getFileFromAssets(requireContext(), fileName)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mVideoView.setOnCompletionListener(v->preplay(currentMedia.getPosition()));
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
