package com.orzechowski.saveme.creator.initial;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.version.database.Version;

import java.util.List;

public class VersionTextAdapter
    extends RecyclerView.Adapter<VersionTextAdapter.VersionViewHolder>
{
    private List<Version> mVersions = null;
    private final LayoutInflater mInflater;

    public VersionTextAdapter(Activity activity)
    {
        mInflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_version_text_rv, parent, false);
        return new VersionViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionViewHolder holder, int position)
    {
        Version version = mVersions.get(position);
        holder.versionNumber.setText(String.valueOf(version.getVersionId()));
        holder.versionText.setText(version.getText());
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
        TextView versionNumber, versionText;

        public VersionViewHolder(@NonNull View itemView)
        {
            super(itemView);
            versionNumber = itemView.findViewById(R.id.version_number);
            versionText = itemView.findViewById(R.id.version_text);
        }
    }
}
