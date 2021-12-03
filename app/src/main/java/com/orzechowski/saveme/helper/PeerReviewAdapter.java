package com.orzechowski.saveme.helper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.database.Tutorial;

import java.util.List;

public class PeerReviewAdapter extends RecyclerView.Adapter<TutorialViewHolder>
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
            holder.mTutorial = tutorial;
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

    public interface FragmentCallback
    {
        void playTutorial(Tutorial tutorial);
        void accept(Tutorial tutorial);
        void reject(Tutorial tutorial);
    }
}
