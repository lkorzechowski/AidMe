package com.orzechowski.saveme.creator.versionsound;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.sound.database.TutorialSound;
import com.orzechowski.saveme.tutorial.version.database.Version;

import java.util.List;

public class VersionSoundOuterAdapter extends RecyclerView.Adapter<SoundViewHolder>
{
    private List<Version> mVersions;
    private List<TutorialSound> mSounds;
    private final LayoutInflater mInflater;
    private final Activity mActivity;
    private final FragmentCallback mCallback;

    public VersionSoundOuterAdapter(Activity activity, FragmentCallback callback)
    {
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    @NonNull
    @Override
    public SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_outer_version_sound_rv, parent, false);
        return new SoundViewHolder(row, mActivity, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, int position)
    {
        TutorialSound sound = mSounds.get(position);
        holder.mSound = sound;
        holder.mAdapter.setElementList(mVersions);
        holder.mText.setText(sound.getFileName());
    }

    @Override
    public int getItemCount()
    {
        return (mSounds == null) ? 0 : mSounds.size();
    }

    public void setElementList(List<TutorialSound> sounds, List<Version> versions)
    {
        mSounds = sounds;
        mVersions = versions;
        notifyDataSetChanged();
    }

    public interface FragmentCallback
    {
        void select(TutorialSound sound, Version version);
        void unselect(TutorialSound sound, Version version);
    }
}
