package com.orzechowski.aidme.creator.versionmultimedia;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia;
import com.orzechowski.aidme.tutorial.version.database.Version;

import java.util.List;

public class VersionMultimediaOuterAdapter
    extends RecyclerView.Adapter<VersionMultimediaOuterAdapter.MultimediaViewHolder>
{
    private List<Version> mVersions = null;
    private List<Multimedia> mMultimedias = null;
    private final LayoutInflater mInflater;
    private final Activity mActivity;
    private final CallbackToFragment mCallback;

    public VersionMultimediaOuterAdapter(Activity activity, CallbackToFragment callback)
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
        holder.multimedia = multimedia;
        holder.adapter.setElementList(mVersions);
    }

    @Override
    public int getItemCount()
    {
        return(mMultimedias==null) ? 0 : mMultimedias.size();
    }

    public void setElementList(List<Multimedia> multimedia, List<Version> versions)
    {
        mMultimedias = multimedia;
        mVersions = versions;
        notifyDataSetChanged();
    }

    public static class MultimediaViewHolder extends RecyclerView.ViewHolder
        implements VersionMultimediaInnerAdapter.OnClickListener
    {
        Multimedia multimedia;
        RecyclerView recycler;
        ImageView imageView;
        VideoView videoView;
        VersionMultimediaInnerAdapter adapter;
        CallbackToFragment callback;

        public MultimediaViewHolder(@NonNull View itemView, Activity requestActivity,
                                    CallbackToFragment callbackToFragment)
        {
            super(itemView);
            callback = callbackToFragment;
            recycler = itemView.findViewById(R.id.version_multimedia_inner_rv);
            recycler.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            adapter = new VersionMultimediaInnerAdapter(requestActivity, this);
            recycler.setAdapter(adapter);
        }

        @Override
        public void select(Version version)
        {
            callback.select(multimedia, version);
        }

        @Override
        public void unselect(Version version)
        {
            callback.unselect(multimedia, version);
        }
    }

    public interface CallbackToFragment
    {
        void select(Multimedia multimedia, Version version);
        void unselect(Multimedia multimedia, Version version);
    }
}
