package com.orzechowski.saveme.creator.versionmultimedia;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.version.database.Version;

import java.util.List;

public class VersionMultimediaInnerAdapter extends RecyclerView.Adapter<VersionViewHolder>
{
    private List<Version> mVersions;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;

    public VersionMultimediaInnerAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater
                .inflate(R.layout.row_inner_version_multimedia_rv, parent, false);
        return new VersionViewHolder(row, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionViewHolder holder, int position)
    {
        Version version = mVersions.get(position);
        holder.mVersion = version;
        holder.mVersionNumberButton.setText(String.valueOf(version.getVersionId()));
    }

    @Override
    public int getItemCount()
    {
        return(mVersions == null) ? 0 : mVersions.size();
    }

    public void setElementList(List<Version> versions)
    {
        mVersions = versions;
        notifyDataSetChanged();
    }

    public interface FragmentCallback
    {
        void select(Version version);
        void unselect(Version version);
    }
}
