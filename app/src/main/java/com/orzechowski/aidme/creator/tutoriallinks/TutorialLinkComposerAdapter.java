package com.orzechowski.aidme.creator.tutoriallinks;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.database.Tutorial;
import com.orzechowski.aidme.tutorial.database.TutorialLink;

import java.util.List;

public class TutorialLinkComposerAdapter
    extends RecyclerView.Adapter<TutorialLinkComposerAdapter.LinkViewHolder>
{
    private List<TutorialLink> mLinks = null;
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
        holder.link = link;
        for(Tutorial tutorial : mTutorials) {
            if(tutorial.getTutorialId()==link.getTutorialId()) {
                holder.submittedName.setText(tutorial.getTutorialName());
                return;
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return(mLinks==null) ? 0 : mLinks.size();
    }

    public void setElementList(List<TutorialLink> links)
    {
        mLinks = links;
        notifyDataSetChanged();
    }

    public static class LinkViewHolder extends RecyclerView.ViewHolder
    {
        TutorialLink link;
        TextView submittedName;
        Button editButton, deleteButton;
        FragmentCallback callback;

        public LinkViewHolder(@NonNull View itemView, FragmentCallback fragmentCallback)
        {
            super(itemView);
            callback = fragmentCallback;
            submittedName = itemView.findViewById(R.id.link_submitted_name);
            editButton = itemView.findViewById(R.id.link_edit_button);
            deleteButton = itemView.findViewById(R.id.link_delete_button);
            editButton.setOnClickListener(v -> callback.edit(link));
            editButton.setOnClickListener(v -> callback.delete(link));
            deleteButton.setBackgroundColor(Color.argb(100, 200, 0, 0));
        }
    }

    public interface FragmentCallback
    {
        void edit(TutorialLink tutorialLink);
        void delete(TutorialLink tutorialLink);
    }
}
