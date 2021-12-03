package com.orzechowski.saveme.creator.initial.soundcomposer;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.sound.database.TutorialSound;

public class SoundViewHolder extends RecyclerView.ViewHolder
{
    public EditText mSoundStart, mPlayInterval;
    public CheckBox mSoundLoop;
    public TextView mFileName;
    public Button mUploadSoundButton;
    public TutorialSound mSound;
    public ImageView mDeleteSound;
    public Activity mActivity;

    public SoundViewHolder(@NonNull View itemView, Activity requestActivity, SoundComposerAdapter
            .FragmentCallback callback)
    {
        super(itemView);
        mActivity = requestActivity;
        mPlayInterval = itemView.findViewById(R.id.new_sound_interval_input);
        mUploadSoundButton = itemView.findViewById(R.id.new_sound_upload_button);
        mSoundStart = itemView.findViewById(R.id.new_sound_start_input);
        mFileName = itemView.findViewById(R.id.new_sound_filename_display);
        mSoundLoop = itemView.findViewById(R.id.new_sound_loop_checkbox);
        mDeleteSound = itemView.findViewById(R.id.delete_new_sound);
        mDeleteSound.setOnClickListener(v -> callback.delete(mSound));
        mSoundLoop.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(mSound != null && callback != null) {
                callback.modifyLoop(isChecked, mSound.getSoundId());
            }
        });
        mUploadSoundButton.setOnClickListener(v -> {
            if(ActivityCompat.checkSelfPermission(mActivity, Manifest.permission
                    .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(mActivity,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 122);
            } else {
                callback.addSound((int) mSound.getSoundId());
            }
        });
        mSoundStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String startText = String.valueOf(mSoundStart.getText());
                if(mSound != null  && callback != null && !startText.isEmpty()) {
                    callback.modifyStart(Integer.parseInt(startText), mSound.getSoundId());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        mPlayInterval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String intervalText = String.valueOf(mPlayInterval.getText());
                if(mSound != null  && callback != null && !intervalText.isEmpty()) {
                    callback.modifyInterval(Integer.parseInt(intervalText), mSound.getSoundId());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
