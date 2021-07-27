package com.orzechowski.aidme.creator;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.version.database.Version;

import java.util.List;

public class VersionComposerAdapter
    extends RecyclerView.Adapter<VersionComposerAdapter.VersionViewHolder>
{
    private List<Version> mVersions = null;
    private final LayoutInflater mInflater;
    private final DeleteVersion mDeleteListener;

    public VersionComposerAdapter(Activity activity, DeleteVersion deleteListener)
    {
        mInflater = LayoutInflater.from(activity);
        mDeleteListener = deleteListener;
    }

    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_new_version_rv, parent, false);
        return new VersionViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionViewHolder holder, int position)
    {
        Version version = mVersions.get(position);
        holder.versionText.setText(version.getText());
        holder.soundDelay.setChecked(version.getDelayGlobalSound());
        holder.version = version;
        holder.deleteListener = mDeleteListener;
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
        EditText versionText;
        CheckBox soundDelay;
        ImageView deleteVersion;
        DeleteVersion deleteListener;
        Version version;

        public VersionViewHolder(@NonNull View itemView)
        {
            super(itemView);
            versionText = itemView.findViewById(R.id.new_version_text);
            soundDelay = itemView.findViewById(R.id.new_version_delay_sound_checkbox);
            deleteVersion = itemView.findViewById(R.id.delete_new_version);
            deleteVersion.setOnClickListener(v-> deleteListener.onClick(version));
        }
    }

    public interface DeleteVersion
    {
        void onClick(Version version);
    }
}
