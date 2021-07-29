package com.orzechowski.aidme.creator;

import android.app.Activity;
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
    private final FragmentCallback mCallback;
    private final int mLevel;

    public VersionTreeComposerAdapter(Activity activity, FragmentCallback callback, int level)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
        mLevel = level;
    }

    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_version_tree_rv, parent, false);
        return new VersionViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionViewHolder holder, int position)
    {
        Version version = mVersions.get(position);
        holder.versionButton.setText(version.getText());
        holder.callback = mCallback;
        holder.version = version;
        holder.level = mLevel;
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
    {
        Version version;
        Button versionButton;
        FragmentCallback callback;
        int level;

        public VersionViewHolder(@NonNull View itemView)
        {
            super(itemView);
            versionButton = itemView.findViewById(R.id.version_tree_button);
            versionButton.setOnClickListener(v-> callback.reassign(version, level));
        }
    }

    public interface FragmentCallback
    {
        void reassign(Version version, int level);
    }
}
