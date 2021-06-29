package com.orzechowski.aidme.tutorial.mediaplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.database.Multimedia;
import com.orzechowski.aidme.tutorial.database.MultimediaViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

import static com.orzechowski.aidme.tools.GetResId.getResId;

public class Player extends Fragment
{
    VideoView mVideoView;
    ImageView mImageView;
    List<Multimedia> mMedias = new LinkedList<>();
    MultimediaViewModel mMultimediaViewModel;
    Long mTutorialId;
    Play mPlayThread;

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Bundle bundle = requireArguments();
        mTutorialId = bundle.getLong("tutorialId");
        String versionMultimedias = bundle.getString("versionMultimedias");
        View view = inflater.inflate(R.layout.fragment_multimedia_player, container, false);
        mVideoView = view.findViewById(R.id.video_embed);
        mImageView = view.findViewById(R.id.image_embed);
        mMultimediaViewModel = new ViewModelProvider(this).get(MultimediaViewModel.class);
        mMultimediaViewModel.getByTutorialId(mTutorialId).observe(requireActivity(), medias->mMedias=medias);
        for(Multimedia media : mMedias){
            if(!versionMultimedias.contains(String.valueOf(media.getPosition()))){
                mMedias.remove(media);
            }
        }
        preplay(0);
        return view;
    }

    private void preplay(int position)
    {
        if (mPlayThread != null) {
            mPlayThread.interrupt();
            position++;
        }
        if(!mMedias.isEmpty()){
            mPlayThread = new Play(mMedias.get(position));
            mPlayThread.start();
        }
    }

    private class Play extends Thread
    {
        private final String currentId;
        private final Multimedia currentMedia;

        Play(Multimedia media){
            currentId = "m"+mTutorialId+"_"+media.getPosition();
            currentMedia = media;
        }

        @Override
        public void run()
        {
            int resourceId = getResId(currentId, R.raw.class);
            if(currentMedia.getType()){
                mImageView.setVisibility(View.VISIBLE);
                mVideoView.setVisibility(View.GONE);
            } else {
                mVideoView.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.GONE);
                mVideoView.setVideoPath("android.resource://com.orzechowski.aidme/"+resourceId);
                mVideoView.start();
                mVideoView.setOnCompletionListener(v->preplay(currentMedia.getPosition()+1));
            }
            /*String idFinal = "s" + mTutorialId + "_" + position;
             int resourceId = getResId(idFinal, R.raw.class);
             videoEmbed.setVideoPath("android.resource://" + packageName + "/" + R.raw.m0_0)
             */
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
