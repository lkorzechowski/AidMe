package com.orzechowski.saveme.creator.initial.versioncomposer;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.version.database.Version;

public class VersionViewHolder extends RecyclerView.ViewHolder
{
    public EditText mVersionText;
    public CheckBox mSoundDelay;
    public ImageView mDeleteVersion;
    public Version mVersion;

    public VersionViewHolder(@NonNull View itemView,
                             VersionComposerAdapter.FragmentCallback callback)
    {
        super(itemView);
        mVersionText = itemView.findViewById(R.id.new_version_text);
        mSoundDelay = itemView.findViewById(R.id.new_version_delay_sound_checkbox);
        mDeleteVersion = itemView.findViewById(R.id.delete_new_version);
        mDeleteVersion.setOnClickListener(v-> callback.delete(mVersion));
        mVersionText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(mVersion != null  && callback != null) {
                    callback.modifyText(String.valueOf(mVersionText.getText()),
                            mVersion.getVersionId());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        mSoundDelay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(mVersion != null && callback != null) {
                callback.modifyDelay(isChecked, mVersion.getVersionId());
            }
        });
    }
}
