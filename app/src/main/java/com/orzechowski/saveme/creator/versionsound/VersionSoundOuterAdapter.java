package com.orzechowski.saveme.creator.versionsound;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.mediaplayer.sound.database.TutorialSound;
import com.orzechowski.saveme.tutorial.version.database.Version;

import java.util.List;

public class VersionSoundOuterAdapter
        extends RecyclerView.Adapter<VersionSoundOuterAdapter.SoundViewHolder>
{
    private List<Version> mVersions = null;
    private List<TutorialSound> mSounds = null;
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
        holder.sound = sound;
        holder.adapter.setElementList(mVersions);
        holder.text.setText(sound.getFileName());
    }

    @Override
    public int getItemCount()
    {
        return (mSounds==null) ? 0 : mSounds.size();
    }

    public void setElementList(List<TutorialSound> sounds, List<Version> versions)
    {
        mSounds = sounds;
        mVersions = versions;
        notifyDataSetChanged();
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder
            implements VersionSoundInnerAdapter.FragmentCallback
    {
        TutorialSound sound;
        RecyclerView recycler;
        TextView text;
        VersionSoundInnerAdapter adapter;
        FragmentCallback callback;

        public SoundViewHolder(@NonNull View itemView, Activity requestActivity,
                               FragmentCallback callbackToFragment)
        {
            super(itemView);
            callback = callbackToFragment;
            recycler = itemView.findViewById(R.id.version_sound_inner_rv);
            recycler.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            adapter = new VersionSoundInnerAdapter(requestActivity, this);
            recycler.setAdapter(adapter);
        }


        @Override
        public void select(Version version)
        {
            callback.select(sound, version);
        }

        @Override
        public void unselect(Version version)
        {
            callback.unselect(sound, version);
        }
    }

    public interface FragmentCallback
    {
        void select(TutorialSound sound, Version version);
        void unselect(TutorialSound sound, Version version);
    }
}
