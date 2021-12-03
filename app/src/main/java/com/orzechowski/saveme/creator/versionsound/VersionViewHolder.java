package com.orzechowski.saveme.creator.versionsound;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.version.database.Version;

public class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    Version mVersion;
    Button mVersionNumberButton;
    VersionSoundInnerAdapter.FragmentCallback mCallback;
    boolean mSelected = false;

    public VersionViewHolder(@NonNull View itemView,
                             VersionSoundInnerAdapter.FragmentCallback fragmentCallback)
    {
        super(itemView);
        mVersionNumberButton = itemView.findViewById(R.id.version_number_button);
        mVersionNumberButton.setBackgroundColor(Color.GREEN);
        mCallback = fragmentCallback;
        mVersionNumberButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(!mSelected) {
            mVersionNumberButton.setBackgroundColor(Color.RED);
            mCallback.select(mVersion);
            mSelected = true;
        } else {
            mVersionNumberButton.setBackgroundColor(Color.GREEN);
            mCallback.unselect(mVersion);
            mSelected = false;
        }
    }
}
