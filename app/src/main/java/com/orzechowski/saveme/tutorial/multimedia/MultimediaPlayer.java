package com.orzechowski.saveme.tutorial.multimedia;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.database.TutorialViewModel;
import com.orzechowski.saveme.tutorial.multimedia.database.Multimedia;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class MultimediaPlayer extends Fragment
{
    private VideoView mVideoView;
    private ImageView mImageView;
    private Play mPlayThread;
    public Long mTutorialId;
    List<Multimedia> mMultimedias = new LinkedList<>();
    private String mPathBase;
    
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mPathBase = requireActivity().getFilesDir().getAbsolutePath()+"/";
        return inflater.inflate(R.layout.fragment_multimedia_player, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        mVideoView = view.findViewById(R.id.video_embed);
        mImageView = view.findViewById(R.id.image_embed);
        getPlayer(0);
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
            if(position==mMultimedias.size()) {
                mPlayThread = new Play(mMultimedias.get(0));
            } else mPlayThread = new Play(mMultimedias.get(position));
            mPlayThread.start();
        } else {
            TutorialViewModel tutorialViewModel = new ViewModelProvider(this)
                    .get(TutorialViewModel.class);
            requireActivity().runOnUiThread(() ->
                tutorialViewModel.getByTutorialId(mTutorialId).observe(requireActivity(),
                        tutorial -> {
                    mPlayThread = new Play(new Multimedia(0, 0, 120000,
                            true, requireActivity().getFilesDir() + "/"
                            + tutorial.getMiniatureName(), true, 0));
                    mPlayThread.start();
                })
            );
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
            FragmentActivity activity = requireActivity();
            int position = currentMedia.getPosition();
            int displayTime = currentMedia.getDisplayTime();
            boolean loopBool = currentMedia.getLoop();
            int size = mMultimedias.size();
            if(!loopBool) mMultimedias.remove(currentMedia);
            if(currentMedia.getType()) {
                activity.runOnUiThread(() -> {
                    mImageView.setVisibility(View.VISIBLE);
                    mVideoView.setVisibility(View.GONE);
                    mImageView.setMinimumWidth(600);
                    mImageView.setMinimumHeight(400);
                    mImageView.setImageURI(Uri.parse(mPathBase+currentMedia.getFileName()));
                });
                if(displayTime>0) {
                    try {
                        sleep(displayTime);
                        getPlayer(position);
                    } catch (InterruptedException e) {
                        activity.runOnUiThread(() -> mImageView.setVisibility(View.GONE));
                        interrupt();
                    }
                }
            } else {
                activity.runOnUiThread(() -> {
                    mVideoView.setVisibility(View.VISIBLE);
                    mImageView.setVisibility(View.GONE);
                    mVideoView.setVideoURI(Uri.parse(mPathBase+currentMedia.getFileName()));
                    if(loopBool && size==1) mVideoView.setOnCompletionListener(v->
                            getPlayer(-1));
                    else {
                        if(position < size-1) mVideoView.setOnCompletionListener(v->
                                getPlayer(position));
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
