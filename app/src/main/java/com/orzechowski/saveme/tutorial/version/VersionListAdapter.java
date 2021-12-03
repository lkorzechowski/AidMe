package com.orzechowski.saveme.tutorial.version;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.version.database.Version;

import java.util.List;

public class VersionListAdapter extends RecyclerView.Adapter<VersionViewHolder>
{
    private List<Version> mVersionList;
    private final LayoutInflater mInflater;
    private final ActivityCallback mCallback;

    public VersionListAdapter(Activity activity)
    {
        mInflater = LayoutInflater.from(activity);
        mVersionList = null;
        mCallback = (ActivityCallback) activity;
    }

    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_versions_rv, viewGroup, false);
        return new VersionViewHolder(row, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionViewHolder versionHolder, int rowNumber)
    {
        versionHolder.mVersion = mVersionList.get(rowNumber);
        versionHolder.mVersionButton.setText((mVersionList.get(rowNumber).getText()));
    }

    @Override
    public int getItemCount()
    {
        return (mVersionList == null) ? 0 : mVersionList.size();
    }

    public void setElementList(List<Version> versions)
    {
        mVersionList = versions;
        notifyDataSetChanged();
    }

    public interface ActivityCallback
    {
        void onClick(Version version);
    }
}
