package com.orzechowski.saveme.creator.versionmultimedia;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.multimedia.database.Multimedia;
import com.orzechowski.saveme.tutorial.version.database.Version;

public class MultimediaViewHolder extends RecyclerView.ViewHolder
        implements VersionMultimediaInnerAdapter.FragmentCallback
{
    Multimedia mMultimedia;
    RecyclerView mRecycler;
    ImageView mImageView;
    VideoView mVideoView;
    VersionMultimediaInnerAdapter mAdapter;
    VersionMultimediaOuterAdapter.FragmentCallback mCallback;

    public MultimediaViewHolder(@NonNull View itemView, Activity requestActivity,
                                VersionMultimediaOuterAdapter.FragmentCallback callbackToFragment)
    {
        super(itemView);
        mCallback = callbackToFragment;
        mRecycler = itemView.findViewById(R.id.version_multimedia_inner_rv);
        mImageView = itemView.findViewById(R.id.new_image_embed);
        mVideoView = itemView.findViewById(R.id.new_video_embed);
        mRecycler.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new VersionMultimediaInnerAdapter(requestActivity, this);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void select(Version version)
    {
        mCallback.select(mMultimedia, version);
    }

    @Override
    public void unselect(Version version)
    {
        mCallback.unselect(mMultimedia, version);
    }
}
