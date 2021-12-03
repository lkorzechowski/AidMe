package com.orzechowski.saveme.creator.initial.soundbrowser;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;

import java.util.List;

public class SoundBrowserAdapter extends RecyclerView.Adapter<SoundViewHolder>
{
    private List<Sound> mSounds;
    private final FragmentCallback mCallback;
    private final LayoutInflater mInflater;

    public SoundBrowserAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    public void setElementList(List<Sound> sounds)
    {
        mSounds = sounds;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.row_sound_browser, parent, false);
        return new SoundViewHolder(view, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, int position)
    {
        Sound sound = mSounds.get(position);
        holder.mSound = sound;
        holder.mSoundNameDisplay.setText(sound.getDisplayName());
    }

    @Override
    public int getItemCount()
    {
        return (mSounds == null) ? 0 : mSounds.size();
    }

    public interface FragmentCallback
    {
        void soundClick(Sound sound);
    }
}
