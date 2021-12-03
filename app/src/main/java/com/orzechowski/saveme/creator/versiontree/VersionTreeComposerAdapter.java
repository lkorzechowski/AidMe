package com.orzechowski.saveme.creator.versiontree;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.version.database.Version;

import java.util.List;

public class VersionTreeComposerAdapter extends RecyclerView.Adapter<VersionViewHolder>
{
    private List<Version> mVersions = null;
    private final LayoutInflater mInflater;
    private final int mLevel;
    private final FragmentCallback mCallback;

    public VersionTreeComposerAdapter(Activity activity, int level, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mLevel = level;
        mCallback = callback;
    }

    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_version_tree_rv, parent, false);
        return new VersionViewHolder(row, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionViewHolder holder, int position)
    {
        Version version = mVersions.get(position);
        holder.mVersionButton.setText(String.valueOf(version.getVersionId()));
        holder.mVersion = version;
        holder.mLevel = mLevel;
    }

    @Override
    public int getItemCount()
    {
        return (mVersions == null) ? 0 : mVersions.size();
    }

    public void setElementList(List<Version> versions)
    {
        mVersions = versions;
        notifyDataSetChanged();
    }

    public interface FragmentCallback
    {
        void callback(Version version);
    }
}
