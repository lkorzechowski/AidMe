package com.orzechowski.saveme.creator.initial.soundcomposer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.sound.database.TutorialSound;

import java.util.List;

public class SoundComposerAdapter
    extends RecyclerView.Adapter<SoundViewHolder>
{
    private List<TutorialSound> mSounds;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;
    private final Activity mActivity;

    public SoundComposerAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
        mActivity = activity;
    }

    @NonNull
    @Override
    public SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_new_sound_rv, parent, false);
        return new SoundViewHolder(row, mActivity, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, int position)
    {
        TutorialSound sound = mSounds.get(position);
        holder.mSoundStart.setText(String.valueOf(sound.getSoundStart()));
        holder.mSoundLoop.setChecked(sound.getSoundLoop());
        holder.mPlayInterval.setText(String.valueOf(sound.getInterval()));
        holder.mFileName.setText(sound.getFileName());
        holder.mSound = sound;
    }

    @Override
    public int getItemCount()
    {
        return (mSounds == null) ? 0 : mSounds.size();
    }

    public void setElementList(List<TutorialSound> sounds)
    {
        mSounds = sounds;
        notifyDataSetChanged();
    }

    public interface FragmentCallback
    {
        void delete(TutorialSound tutorialSound);
        void modifyLoop(boolean loop, Long soundId);
        void modifyStart(int start, Long soundId);
        void modifyInterval(int interval, Long soundId);
        void addSound(int position);
    }
}
