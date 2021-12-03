package com.orzechowski.saveme.creator.versiontree;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.version.database.Version;

public class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    Version mVersion;
    Button mVersionButton;
    int mLevel;
    VersionTreeComposerAdapter.FragmentCallback mCallback;

    public VersionViewHolder(@NonNull View itemView,
                             VersionTreeComposerAdapter.FragmentCallback fragmentCallback)
    {
        super(itemView);
        mVersionButton = itemView.findViewById(R.id.version_tree_button);
        mCallback = fragmentCallback;
        mVersionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(mLevel == 0) mCallback.callback(mVersion);
    }
}
