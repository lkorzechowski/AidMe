package com.orzechowski.aidme.creator.initial.soundbrowser;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;

import java.util.List;

public class SoundBrowserAdapter extends RecyclerView.Adapter<SoundBrowserAdapter.SoundViewHolder>
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
        holder.sound = sound;
        holder.soundNameDisplay.setText(sound.getDisplayName());
    }

    @Override
    public int getItemCount()
    {
        return (mSounds == null) ? 0 : mSounds.size();
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        Sound sound;
        TextView soundNameDisplay;
        FragmentCallback callback;

        public SoundViewHolder(@NonNull View itemView, FragmentCallback fragmentCallback)
        {
            super(itemView);
            soundNameDisplay = itemView.findViewById(R.id.sound_display_name);
            callback = fragmentCallback;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            callback.soundClick(sound);
        }
    }

    public interface FragmentCallback
    {
        void soundClick(Sound sound);
    }
}
