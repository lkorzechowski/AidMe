package com.orzechowski.saveme.helper;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.database.Tutorial;

public class TutorialViewHolder extends RecyclerView.ViewHolder
{
    PeerReviewAdapter.FragmentCallback mCallback;
    Tutorial mTutorial;
    ImageView mImage;
    TextView mName;
    Button mAccept, mReject, mPlay;

    public TutorialViewHolder(@NonNull View itemView,
                              PeerReviewAdapter.FragmentCallback activityCallback)
    {
        super(itemView);
        mCallback = activityCallback;
        mImage = itemView.findViewById(R.id.tutorial_image);
        mName = itemView.findViewById(R.id.tutorial_name);
        mAccept = itemView.findViewById(R.id.review_accept_button);
        mReject = itemView.findViewById(R.id.review_reject_button);
        mPlay = itemView.findViewById(R.id.review_play_button);
        mAccept.setOnClickListener(v -> mCallback.accept(mTutorial));
        mReject.setOnClickListener(v -> mCallback.reject(mTutorial));
        mPlay.setOnClickListener(v -> mCallback.playTutorial(mTutorial));
    }
}
