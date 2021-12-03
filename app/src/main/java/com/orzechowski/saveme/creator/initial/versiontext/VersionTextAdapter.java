package com.orzechowski.saveme.creator.initial.versiontext;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.version.database.Version;

import java.util.List;

public class VersionTextAdapter extends RecyclerView.Adapter<VersionTextViewHolder>
{
    private List<Version> mVersions;
    private final LayoutInflater mInflater;

    public VersionTextAdapter(Activity activity)
    {
        mInflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public VersionTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_version_text_rv, parent, false);
        return new VersionTextViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionTextViewHolder holder, int position)
    {
        Version version = mVersions.get(position);
        holder.mVersionNumber.setText(String.valueOf(version.getVersionId()));
        holder.mVersionText.setText(version.getText());
    }

    @Override
    public int getItemCount()
    {
        if(mVersions != null) return mVersions.size();
        else return 0;
    }

    public void setElementList(List<Version> versions)
    {
        mVersions = versions;
        notifyDataSetChanged();
    }
}
