package com.orzechowski.saveme.tutorial.version;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.version.database.Version;

public class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    VersionListAdapter.ActivityCallback mCallback;
    Version mVersion;
    Button mVersionButton;

    public VersionViewHolder(
            @NonNull View viewForThisRow, VersionListAdapter.ActivityCallback fragmentCallback)
    {
        super(viewForThisRow);
        mCallback = fragmentCallback;
        mVersionButton = viewForThisRow.findViewById(R.id.version_button);
        mVersionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        mCallback.onClick(mVersion);
    }
}