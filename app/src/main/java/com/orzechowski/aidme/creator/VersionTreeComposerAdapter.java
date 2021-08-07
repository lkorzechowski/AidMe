package com.orzechowski.aidme.creator;

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

public class VersionTreeComposerAdapter
    extends RecyclerView.Adapter<VersionTreeComposerAdapter.VersionViewHolder>
{
    private List<Version> mVersions = null;
    private final LayoutInflater mInflater;
    private final int mLevel;
    private final OnClickListener mListener;
    private boolean mIsParent = false;

    public VersionTreeComposerAdapter(Activity activity, OnClickListener listener, int level)
    {
        mInflater = LayoutInflater.from(activity);
        mLevel = level;
        mListener = listener;
    }

    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_version_tree_rv, parent, false);
        return new VersionViewHolder(row, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionViewHolder holder, int position)
    {
        Version version = mVersions.get(position);
        holder.versionButton.setText(String.valueOf(version.getVersionId()));
        if(mIsParent) holder.versionButton.setBackgroundColor(Color.RED);
        else holder.versionButton.setBackgroundColor(Color.GREEN);
        holder.version = version;
        holder.level = mLevel;
    }

    @Override
    public int getItemCount()
    {
        if(mVersions!=null) return mVersions.size();
        else return 0;
    }

    public void setParent()
    {
        mIsParent = true;
    }

    public void setElementList(List<Version> versions)
    {
        mVersions = versions;
        notifyDataSetChanged();
    }

    public void parentSet()
    {
        mIsParent = false;
        notifyDataSetChanged();
    }

    public static class VersionViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {
        Version version;
        Button versionButton;
        OnClickListener listenerForThisRow;
        int level;

        public VersionViewHolder(@NonNull View itemView, OnClickListener listener)
        {
            super(itemView);
            listenerForThisRow = listener;
            versionButton = itemView.findViewById(R.id.version_tree_button);
        }

        @Override
        public void onClick(View v)
        {
            listenerForThisRow.onClick(version, level);
        }
    }

    public interface OnClickListener
    {
        void onClick(Version version, int level);
    }
}
