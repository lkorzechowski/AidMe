package com.orzechowski.saveme.helper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.database.Tutorial;

import java.util.List;

public class PeerReviewAdapter extends RecyclerView.Adapter<PeerReviewAdapter.TutorialViewHolder>
{
    private List<Tutorial> mTutorials;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;

    public PeerReviewAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    @NonNull
    @Override
    public TutorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new TutorialViewHolder(mInflater.inflate(R.layout.row_peer_review_rv, parent,
                false), mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialViewHolder holder, int position)
    {
        Tutorial tutorial = mTutorials.get(position);
        if(tutorial != null) {
            holder.tutorial = tutorial;
        }
    }

    @Override
    public int getItemCount()
    {
        return (mTutorials == null) ? 0 : mTutorials.size();
    }

    public void setElementList(List<Tutorial> tutorials)
    {
        mTutorials = tutorials;
        notifyDataSetChanged();
    }

    public static class TutorialViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener
    {
        FragmentCallback callback;
        Tutorial tutorial;
        ImageView image;
        TextView name;
        Button accept, reject;

        public TutorialViewHolder(@NonNull View itemView, FragmentCallback activityCallback)
        {
            super(itemView);
            callback = activityCallback;
            itemView.setOnClickListener(this);
            image = itemView.findViewById(R.id.tutorial_image);
            name = itemView.findViewById(R.id.tutorial_name);
            accept = itemView.findViewById(R.id.review_accept_button);
            reject = itemView.findViewById(R.id.review_reject_button);
            accept.setOnClickListener(v -> callback.accept(tutorial));
            reject.setOnClickListener(v -> callback.reject(tutorial));
        }

        @Override
        public void onClick(View v)
        {
            callback.playTutorial(tutorial);
        }
    }

    public interface FragmentCallback
    {
        void playTutorial(Tutorial tutorial);
        void accept(Tutorial tutorial);
        void reject(Tutorial tutorial);
    }
}
