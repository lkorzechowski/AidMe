package com.orzechowski.saveme.creator.initial.versioncomposer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.version.database.Version;

import java.util.List;

public class VersionComposerAdapter extends RecyclerView.Adapter<VersionViewHolder>
{
    private List<Version> mVersions;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;

    public VersionComposerAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_new_version_rv, parent, false);
        return new VersionViewHolder(row, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionViewHolder holder, int position)
    {
        Version version = mVersions.get(position);
        holder.mVersionText.setText(version.getText());
        holder.mSoundDelay.setChecked(version.getDelayGlobalSound());
        holder.mVersion = version;
    }

    @Override
    public int getItemCount()
    {
        return (mVersions == null) ? 0 : mVersions.size();
    }

    public void setElementList(List<Version> versions)
    {
        mVersions = versions;
        notifyDataSetChanged();
    }

    public interface FragmentCallback
    {
        void delete(Version version);
        void modifyText(String text, Long versionId);
        void modifyDelay(boolean delay, Long versionId);
    }
}
