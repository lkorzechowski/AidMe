package com.orzechowski.aidme.browser.results;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.browser.userrating.Rating;
import com.orzechowski.aidme.browser.userrating.RatingViewModel;
import com.orzechowski.aidme.database.helper.Helper;
import com.orzechowski.aidme.tutorial.database.Tutorial;

import java.util.List;

public class ResultsListAdapter extends RecyclerView.Adapter<ResultsListAdapter.ResultViewHolder>
{
    private List<Tutorial> mTutorials = null;
    private final LayoutInflater mInflater;
    private final Activity mActivity;
    private List<Helper> mHelpers;
    private final OnClickListener mListener;

    public ResultsListAdapter(Activity activity, OnClickListener onClickListener)
    {
        mInflater = LayoutInflater.from(activity);
        mActivity = activity;
        mListener = onClickListener;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_results_rv, viewGroup, false);
        return new ResultViewHolder(row, mListener, mActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int rowNumber)
    {
        Tutorial tutorial = mTutorials.get(rowNumber);
        long authorId = tutorial.getAuthorId();
        for (Helper helper : mHelpers) {
            if(authorId == helper.getHelperId()) {
                holder.author.setText(String.format("Autor: %s %s %s",
                        helper.getTitle(), helper.getName(), helper.getSurname()));
            }
        }
        holder.thisResult = tutorial;
        holder.name.setText(tutorial.getTutorialName());
        Uri uri = Uri.parse(tutorial.getMiniatureUriString());
        if(uri != null) holder.image.setImageURI(uri);
        float rating = tutorial.getRating();
        if(rating>4.75) {
            holder.starFive.setImageResource(R.drawable.ic_full_star);
        } else if(rating>4.25) {
            holder.starFive.setImageResource(R.drawable.ic_half_star);
        } else {
            holder.starFive.setImageResource(R.drawable.ic_empty_star);
        }
        if(rating>3.75) {
            holder.starFour.setImageResource(R.drawable.ic_full_star);
        } else if(rating>4.25) {
            holder.starFour.setImageResource(R.drawable.ic_half_star);
        } else {
            holder.starFour.setImageResource(R.drawable.ic_empty_star);
        }
        if(rating>2.75) {
            holder.starThree.setImageResource(R.drawable.ic_full_star);
        } else if(rating>2.25) {
            holder.starThree.setImageResource(R.drawable.ic_half_star);
        } else {
            holder.starThree.setImageResource(R.drawable.ic_empty_star);
        }
        if(rating>1.75) {
            holder.starTwo.setImageResource(R.drawable.ic_full_star);
        } else if(rating>1.25) {
            holder.starTwo.setImageResource(R.drawable.ic_half_star);
        } else {
            holder.starTwo.setImageResource(R.drawable.ic_empty_star);
        }
        if(rating>0.75) {
            holder.starOne.setImageResource(R.drawable.ic_full_star);
        } else if(rating>0.25) {
            holder.starOne.setImageResource(R.drawable.ic_half_star);
        } else {
            holder.starOne.setImageResource(R.drawable.ic_empty_star);
        }
    }

    @Override
    public int getItemCount()
    {
        return (mTutorials==null) ? 0 : mTutorials.size();
    }

    public void setElementList(List<Tutorial> tutorials, List<Helper> helpers)
    {
        mTutorials = tutorials;
        mHelpers = helpers;
        notifyDataSetChanged();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView name, author;
        ImageView image, starOne, starTwo, starThree, starFour, starFive;
        OnClickListener listener;
        Tutorial thisResult;
        ConstraintLayout starLayout;

        public ResultViewHolder(@NonNull View viewForThisRow, OnClickListener listenerForThisRow,
                                Activity activity)
        {
            super(viewForThisRow);
            name = viewForThisRow.findViewById(R.id.result_name_text);
            image = viewForThisRow.findViewById(R.id.result_image);
            author = viewForThisRow.findViewById(R.id.author);
            starOne = viewForThisRow.findViewById(R.id.star_one);
            starTwo = viewForThisRow.findViewById(R.id.star_two);
            starThree = viewForThisRow.findViewById(R.id.star_three);
            starFour = viewForThisRow.findViewById(R.id.star_four);
            starFive = viewForThisRow.findViewById(R.id.star_five);
            starLayout = viewForThisRow.findViewById(R.id.star_rating_layout);
            starLayout.setOnClickListener(v -> {
                RatingViewModel ratingViewModel =
                        new ViewModelProvider((ViewModelStoreOwner) activity)
                                .get(RatingViewModel.class);
                ratingViewModel.getAll().observe((LifecycleOwner) activity, ratings -> {
                    long tutorialId = thisResult.getTutorialId();
                    boolean match = false;
                    for(Rating rating : ratings) {
                        if(rating.getTutorialId()==tutorialId) {
                            match = true;
                        }
                    }
                    if(!match) {
                        ratingViewModel.insert(new Rating(ratings.size(), tutorialId));
                    }
                });

            });
            listener = listenerForThisRow;
            viewForThisRow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            listener.onClick(thisResult);
        }
    }

    public interface OnClickListener
    {
        void onClick(Tutorial tutorial);
    }
}
