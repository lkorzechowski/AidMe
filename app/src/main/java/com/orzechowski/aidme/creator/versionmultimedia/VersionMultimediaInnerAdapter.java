package com.orzechowski.aidme.creator.versionmultimedia;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.version.database.Version;

import java.util.List;

public class VersionMultimediaInnerAdapter
    extends RecyclerView.Adapter<VersionMultimediaInnerAdapter.VersionViewHolder>
{
    private List<Version> mVersions = null;
    private final LayoutInflater mInflater;
    private final OnClickListener mListener;

    public VersionMultimediaInnerAdapter(Activity activity, OnClickListener listener)
    {
        mInflater = LayoutInflater.from(activity);
        mListener = listener;
    }

    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater
                .inflate(R.layout.row_inner_version_multimedia_rv, parent, false);
        return new VersionViewHolder(row, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionViewHolder holder, int position)
    {
        Version version = mVersions.get(position);
    }

    @Override
    public int getItemCount()
    {
        if(mVersions!=null) return mVersions.size();
        else return 0;
    }

    public void setElementList(List<Version> versions)
    {
        mVersions = versions;
        notifyDataSetChanged();
    }

    public static class VersionViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {
        Version version;
        Button versionNumberButton;
        OnClickListener listener;
        boolean selected = false;

        public VersionViewHolder(@NonNull View itemView, OnClickListener fragmentListener)
        {
            super(itemView);
            versionNumberButton = itemView.findViewById(R.id.version_number_button);
            versionNumberButton.setBackgroundColor(Color.argb(100, 0, 200, 0));
            listener = fragmentListener;
            versionNumberButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if(!selected) {
                versionNumberButton.setBackgroundColor(Color
                        .argb(100, 200, 0, 0));
                listener.select(version);
                selected = true;
            } else {
                versionNumberButton.setBackgroundColor(Color
                        .argb(100, 0, 200, 0));
                listener.unselect(version);
                selected = false;
            }
        }
    }

    public interface OnClickListener
    {
        void select(Version version);
        void unselect(Version version);
    }
}
