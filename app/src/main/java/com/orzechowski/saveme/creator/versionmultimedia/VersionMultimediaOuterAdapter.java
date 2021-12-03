package com.orzechowski.saveme.creator.versionmultimedia;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.multimedia.database.Multimedia;
import com.orzechowski.saveme.tutorial.version.database.Version;

import java.util.List;

public class VersionMultimediaOuterAdapter extends RecyclerView.Adapter<MultimediaViewHolder>
{
    private List<Version> mVersions;
    private List<Multimedia> mMultimedias;
    private final LayoutInflater mInflater;
    private final Activity mActivity;
    private final FragmentCallback mCallback;

    public VersionMultimediaOuterAdapter(Activity activity, FragmentCallback callback)
    {
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    @NonNull
    @Override
    public MultimediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater
                .inflate(R.layout.row_outer_version_multimedia_rv, parent, false);
        return new MultimediaViewHolder(row, mActivity, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull MultimediaViewHolder holder, int position)
    {
        Multimedia multimedia = mMultimedias.get(position);
        holder.mMultimedia = multimedia;
        if(multimedia.getType()) {
            holder.mImageView.setVisibility(View.VISIBLE);
            holder.mImageView.setImageURI(Uri.parse(multimedia.getFileName()));
        } else {
            holder.mVideoView.setVisibility(View.VISIBLE);
            holder.mVideoView.setVideoURI(Uri.parse(multimedia.getFileName()));
        }
        holder.mAdapter.setElementList(mVersions);
    }

    @Override
    public int getItemCount()
    {
        return(mMultimedias == null) ? 0 : mMultimedias.size();
    }

    public void setElementList(List<Multimedia> multimedia, List<Version> versions)
    {
        mMultimedias = multimedia;
        mVersions = versions;
        notifyDataSetChanged();
    }

    public interface FragmentCallback
    {
        void select(Multimedia multimedia, Version version);
        void unselect(Multimedia multimedia, Version version);
    }
}
