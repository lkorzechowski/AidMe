package com.orzechowski.saveme.creator.tutoriallinks.links;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.database.TutorialLink;

public class LinkViewHolder extends RecyclerView.ViewHolder
{
    TutorialLink mLink;
    TextView mSubmittedName;
    Button mEditButton, mDeleteButton;
    TutorialLinkComposerAdapter.FragmentCallback mCallback;

    public LinkViewHolder(@NonNull View itemView,
                          TutorialLinkComposerAdapter.FragmentCallback fragmentCallback)
    {
        super(itemView);
        mCallback = fragmentCallback;
        mSubmittedName = itemView.findViewById(R.id.link_submitted_name);
        mEditButton = itemView.findViewById(R.id.link_edit_button);
        mDeleteButton = itemView.findViewById(R.id.link_delete_button);
        mEditButton.setOnClickListener(v -> mCallback.edit(mLink));
        mDeleteButton.setOnClickListener(v -> mCallback.delete(mLink));
        mDeleteButton.setBackgroundColor(Color.RED);
    }
}