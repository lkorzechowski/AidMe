package com.orzechowski.saveme.creator.initial.soundbrowser.narrationbrowser;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.creator.initial.soundbrowser.Sound;

public class NarrationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    Sound mNarration;
    TextView mNarrationNameDisplay;
    NarrationBrowserAdapter.FragmentCallback mCallback;

    public NarrationViewHolder(@NonNull View itemView,
                               NarrationBrowserAdapter.FragmentCallback fragmentCallback)
    {
        super(itemView);
        mNarrationNameDisplay = itemView.findViewById(R.id.sound_display_name);
        mCallback = fragmentCallback;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        mCallback.narrationClick(mNarration);
    }
}