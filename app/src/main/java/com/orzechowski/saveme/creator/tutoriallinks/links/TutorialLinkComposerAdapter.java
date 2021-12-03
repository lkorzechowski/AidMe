package com.orzechowski.saveme.creator.tutoriallinks.links;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.database.Tutorial;
import com.orzechowski.saveme.tutorial.database.TutorialLink;

import java.util.List;

public class TutorialLinkComposerAdapter extends RecyclerView.Adapter<LinkViewHolder>
{
    private List<TutorialLink> mLinks;
    private final List<Tutorial> mTutorials;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;

    public TutorialLinkComposerAdapter(Activity activity, FragmentCallback callback,
                                       List<Tutorial> tutorials)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
        mTutorials = tutorials;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater
                .inflate(R.layout.row_tutorial_link_rv, parent, false);
        return new LinkViewHolder(row, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position)
    {
        TutorialLink link = mLinks.get(position);
        holder.mLink = link;
        for(Tutorial tutorial : mTutorials) {
            if(tutorial.getTutorialId()==link.getTutorialId()) {
                holder.mSubmittedName.setText(tutorial.getTutorialName());
                return;
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return(mLinks == null) ? 0 : mLinks.size();
    }

    public void setElementList(List<TutorialLink> links)
    {
        mLinks = links;
        notifyDataSetChanged();
    }

    public interface FragmentCallback
    {
        void edit(TutorialLink tutorialLink);
        void delete(TutorialLink tutorialLink);
    }
}
