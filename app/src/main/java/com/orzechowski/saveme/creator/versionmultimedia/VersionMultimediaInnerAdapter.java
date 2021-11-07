package com.orzechowski.saveme.creator.versionmultimedia;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.version.database.Version;

import java.util.List;

public class VersionMultimediaInnerAdapter
    extends RecyclerView.Adapter<VersionMultimediaInnerAdapter.VersionViewHolder>
{
    private List<Version> mVersions = null;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;

    public VersionMultimediaInnerAdapter(Activity activity, FragmentCallback listener)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = listener;
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
        holder.version = version;
        holder.versionNumberButton.setText(String.valueOf(version.getVersionId()));
    }

    @Override
    public int getItemCount()
    {
        return(mVersions==null) ? 0 : mVersions.size();
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
        FragmentCallback callback;
        boolean selected = false;

        public VersionViewHolder(@NonNull View itemView, FragmentCallback fragmentCallback)
        {
            super(itemView);
            versionNumberButton = itemView.findViewById(R.id.version_number_button);
            versionNumberButton.setBackgroundColor(Color.argb(100, 0, 200, 0));
            callback = fragmentCallback;
            versionNumberButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if(!selected) {
                versionNumberButton.setBackgroundColor(Color
                        .argb(100, 200, 0, 0));
                callback.select(version);
                selected = true;
            } else {
                versionNumberButton.setBackgroundColor(Color
                        .argb(100, 0, 200, 0));
                callback.unselect(version);
                selected = false;
            }
        }
    }

    public interface FragmentCallback
    {
        void select(Version version);
        void unselect(Version version);
    }
}
