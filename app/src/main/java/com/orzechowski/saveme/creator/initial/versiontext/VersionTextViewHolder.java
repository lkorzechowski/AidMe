package com.orzechowski.saveme.creator.initial.versiontext;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;

public class VersionTextViewHolder extends RecyclerView.ViewHolder
{
    public TextView mVersionNumber, mVersionText;

    public VersionTextViewHolder(@NonNull View itemView)
    {
        super(itemView);
        mVersionNumber = itemView.findViewById(R.id.version_number);
        mVersionText = itemView.findViewById(R.id.version_text);
    }
}
