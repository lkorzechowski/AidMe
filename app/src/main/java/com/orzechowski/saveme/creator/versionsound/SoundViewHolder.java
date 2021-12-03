package com.orzechowski.saveme.creator.versionsound;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.sound.database.TutorialSound;
import com.orzechowski.saveme.tutorial.version.database.Version;

public class SoundViewHolder extends RecyclerView.ViewHolder
        implements VersionSoundInnerAdapter.FragmentCallback
{
    TutorialSound mSound;
    RecyclerView mRecycler;
    TextView mText;
    VersionSoundInnerAdapter mAdapter;
    VersionSoundOuterAdapter.FragmentCallback mCallback;

    public SoundViewHolder(@NonNull View itemView, Activity requestActivity,
                           VersionSoundOuterAdapter.FragmentCallback callbackToFragment)
    {
        super(itemView);
        mCallback = callbackToFragment;
        mRecycler = itemView.findViewById(R.id.version_sound_inner_rv);
        mRecycler.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new VersionSoundInnerAdapter(requestActivity, this);
        mRecycler.setAdapter(mAdapter);
    }


    @Override
    public void select(Version version)
    {
        mCallback.select(mSound, version);
    }

    @Override
    public void unselect(Version version)
    {
        mCallback.unselect(mSound, version);
    }
}
