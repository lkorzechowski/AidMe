package com.orzechowski.aidme.browser;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.settings.helper.database.Helper;
import com.orzechowski.aidme.tools.AssetObtainer;
import com.orzechowski.aidme.tutorial.database.Tutorial;

import java.io.IOException;
import java.util.List;

public class ResultsListAdapter extends RecyclerView.Adapter<ResultsListAdapter.ResultViewHolder>
{
    private List<Tutorial> mTutorials = null;
    private final LayoutInflater mInflater;
    private final OnClickListener mListener;
    private final AssetObtainer assetObtainer = new AssetObtainer();
    private final Context mContext;
    private List<Helper> mHelpers;

    public ResultsListAdapter(Activity activity, OnClickListener listener)
    {
        mInflater = LayoutInflater.from(activity);
        mListener = listener;
        mContext = activity.getBaseContext();
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_results_rv, viewGroup, false);
        return new ResultViewHolder(row, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder resultHolder, int rowNumber)
    {
        Tutorial tutorial = mTutorials.get(rowNumber);
        long authorId = tutorial.getAuthorId();
        for (Helper helper : mHelpers) {
            if (authorId == helper.getHelperId()) {
                resultHolder.author.setText(String.format("Autor: %s %s %s",
                        helper.getTitle(), helper.getName(), helper.getSurname()));
            }
        }
        resultHolder.thisResult = tutorial;
        resultHolder.name.setText(tutorial.getTutorialName());
        Uri uri = null;
        try {
            uri = Uri.fromFile(assetObtainer.getFileFromAssets(mContext, tutorial.getMiniatureName()));
        } catch (IOException ignored) {}
        if(uri != null) resultHolder.image.setImageURI(uri);
    }

    @Override
    public int getItemCount()
    {
        if(mTutorials!=null) return mTutorials.size();
        else return 0;
    }

    public void setElementList(List<Tutorial> tutorials, List<Helper> helpers)
    {
        mTutorials = tutorials;
        mHelpers = helpers;
        notifyDataSetChanged();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView name, author;
        ImageView image;
        OnClickListener listenerForThisRow;
        Tutorial thisResult;

        public ResultViewHolder(@NonNull View viewForThisRow, OnClickListener listener)
        {
            super(viewForThisRow);
            name = viewForThisRow.findViewById(R.id.result_name_text);
            image = viewForThisRow.findViewById(R.id.result_image);
            author = viewForThisRow.findViewById(R.id.author);
            listenerForThisRow = listener;
            viewForThisRow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            listenerForThisRow.onClick(thisResult);
        }
    }

    public interface OnClickListener
    {
        void onClick(Tutorial tutorial);
    }
}
