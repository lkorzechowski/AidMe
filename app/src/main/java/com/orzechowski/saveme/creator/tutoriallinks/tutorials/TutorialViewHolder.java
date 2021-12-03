package com.orzechowski.saveme.creator.tutoriallinks.tutorials;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.database.Tutorial;

public class TutorialViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    Tutorial mTutorial;
    ImageView mMiniature;
    TextView mName;
    TutorialLinkTutorialListAdapter.FragmentCallback mCallback;

    public TutorialViewHolder(@NonNull View itemView,
                              TutorialLinkTutorialListAdapter.FragmentCallback fragmentCallback)
    {
        super(itemView);
        mCallback = fragmentCallback;
        itemView.setOnClickListener(this);
        mName = itemView.findViewById(R.id.tutorial_name);
        mMiniature = itemView.findViewById(R.id.tutorial_miniature);
    }

    @Override
    public void onClick(View v)
    {
        mCallback.selectTutorial(mTutorial);
    }
}
