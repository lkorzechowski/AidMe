package com.orzechowski.saveme.creator.tutoriallinks.tutorials;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.database.Tutorial;

import java.util.List;

public class TutorialLinkTutorialListAdapter extends RecyclerView.Adapter<TutorialViewHolder>
{
    private List<Tutorial> mTutorials;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;
    private final String mPathBase;

    public TutorialLinkTutorialListAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
        mPathBase = activity.getFilesDir().getAbsolutePath()+"/";
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
        holder.mTutorial = tutorial;
        holder.mMiniature.setImageURI(Uri.parse(mPathBase+tutorial.getMiniatureName()));
        holder.mName.setText(tutorial.getTutorialName());
    }

    @Override
    public int getItemCount()
    {
        return(mTutorials == null) ? 0 : mTutorials.size();
    }

    public void setElementList(List<Tutorial> tutorials)
    {
        mTutorials = tutorials;
        notifyDataSetChanged();
    }

    public interface FragmentCallback
    {
        void selectTutorial(Tutorial tutorial);
    }
}
