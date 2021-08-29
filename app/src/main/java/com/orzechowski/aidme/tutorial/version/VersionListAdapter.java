package com.orzechowski.aidme.tutorial.version;

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

public class VersionListAdapter extends RecyclerView.Adapter<VersionListAdapter.VersionViewHolder>
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
        versionHolder.thisVersion = mVersionList.get(rowNumber);
        versionHolder.versionButton.setText((mVersionList.get(rowNumber).getText()));
    }

    @Override
    public int getItemCount()
    {
        return (mVersionList==null) ? 0 : mVersionList.size();
    }

    public void setElementList(List<Version> versions)
    {
        mVersionList = versions;
        notifyDataSetChanged();
    }

    public static class VersionViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        ActivityCallback callback;
        Version thisVersion;
        Button versionButton;

        public VersionViewHolder(
                @NonNull View viewForThisRow, ActivityCallback fragmentCallback)
        {
            super(viewForThisRow);
            callback = fragmentCallback;
            versionButton = viewForThisRow.findViewById(R.id.version_button);
            versionButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            callback.onClick(thisVersion);
        }
    }

    public interface ActivityCallback
    {
        void onClick(Version version);
    }
}
