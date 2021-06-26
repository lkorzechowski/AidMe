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
    private final OnClickListener mListener;

    public VersionListAdapter(Activity activity)
    {
        mInflater = LayoutInflater.from(activity);
        mVersionList = null;
        mListener = (OnClickListener) activity;
    }

    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_versions_rv, viewGroup, false);
        return new VersionViewHolder(row, mListener);
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
        if(mVersionList!=null) return mVersionList.size();
        else return 0;
    }

    public void setElementList(List<Version> versions)
    {
        mVersionList = versions;
        notifyDataSetChanged();
    }

    public static class VersionViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        OnClickListener listenerForThisRow;
        Version thisVersion;
        Button versionButton;

        public VersionViewHolder(
                @NonNull View viewForThisRow, OnClickListener listenerFromActivity)
        {
            super(viewForThisRow);
            listenerForThisRow = listenerFromActivity;
            versionButton = viewForThisRow.findViewById(R.id.version_button);
            versionButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            listenerForThisRow.onClick(thisVersion);
        }
    }

    public interface OnClickListener
    {
        void onClick(Version version);
    }
}