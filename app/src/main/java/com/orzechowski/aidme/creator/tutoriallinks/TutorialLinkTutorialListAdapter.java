package com.orzechowski.aidme.creator.tutoriallinks;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.database.Tutorial;

import java.util.List;

public class TutorialLinkTutorialListAdapter
    extends RecyclerView.Adapter<TutorialLinkTutorialListAdapter.TutorialViewHolder>
{
    private List<Tutorial> mTutorials;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;

    public TutorialLinkTutorialListAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    @NonNull
    @Override
    public TutorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater
                .inflate(R.layout.row_tutorials_rv, parent, false);
        return new TutorialViewHolder(row, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialViewHolder holder, int position)
    {
        Tutorial tutorial = mTutorials.get(position);
        holder.tutorial = tutorial;
        holder.miniature.setImageURI(Uri.parse(tutorial.getMiniatureUriString()));
        holder.name.setText(tutorial.getTutorialName());
    }

    @Override
    public int getItemCount()
    {
        return(mTutorials==null) ? 0 : mTutorials.size();
    }

    public void setElementList(List<Tutorial> tutorials)
    {
        mTutorials = tutorials;
        notifyDataSetChanged();
    }

    public static class TutorialViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {
        Tutorial tutorial;
        ImageView miniature;
        TextView name;
        FragmentCallback callback;

        public TutorialViewHolder(@NonNull View itemView, FragmentCallback fragmentCallback)
        {
            super(itemView);
            callback = fragmentCallback;
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.tutorial_name);
            miniature = itemView.findViewById(R.id.tutorial_miniature);
        }

        @Override
        public void onClick(View v)
        {
            callback.selectTutorial(tutorial);
        }
    }

    public interface FragmentCallback
    {
        void selectTutorial(Tutorial tutorial);
    }
}
