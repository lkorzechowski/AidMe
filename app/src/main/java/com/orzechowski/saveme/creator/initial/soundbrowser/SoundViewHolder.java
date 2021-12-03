package com.orzechowski.saveme.creator.initial.soundbrowser;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;

public class SoundViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    Sound mSound;
    TextView mSoundNameDisplay;
    SoundBrowserAdapter.FragmentCallback mCallback;

    public SoundViewHolder(@NonNull View itemView,
                           SoundBrowserAdapter.FragmentCallback fragmentCallback)
    {
        super(itemView);
        mSoundNameDisplay = itemView.findViewById(R.id.sound_display_name);
        mCallback = fragmentCallback;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        mCallback.soundClick(mSound);
    }
}