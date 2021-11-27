package com.orzechowski.saveme.creator.initial;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.version.database.Version;

import java.util.List;

public class VersionComposerAdapter
    extends RecyclerView.Adapter<VersionComposerAdapter.VersionViewHolder>
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
        holder.versionText.setText(version.getText());
        holder.soundDelay.setChecked(version.getDelayGlobalSound());
        holder.version = version;
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

    public static class VersionViewHolder extends RecyclerView.ViewHolder
    {
        EditText versionText;
        CheckBox soundDelay;
        ImageView deleteVersion;
        Version version;

        public VersionViewHolder(@NonNull View itemView, FragmentCallback callback)
        {
            super(itemView);
            versionText = itemView.findViewById(R.id.new_version_text);
            soundDelay = itemView.findViewById(R.id.new_version_delay_sound_checkbox);
            deleteVersion = itemView.findViewById(R.id.delete_new_version);
            deleteVersion.setOnClickListener(v-> callback.delete(version));
            versionText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if(version!=null  && callback!=null) {
                        callback.modifyText(String.valueOf(versionText.getText()),
                                version.getVersionId());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
            soundDelay.setOnCheckedChangeListener((buttonView, isChecked)-> {
                if(version!=null && callback!=null) {
                    callback.modifyDelay(isChecked, version.getVersionId());
                }
            });
        }
    }

    public interface FragmentCallback
    {
        void delete(Version version);
        void modifyText(String text, Long versionId);
        void modifyDelay(boolean delay, Long versionId);
    }
}
