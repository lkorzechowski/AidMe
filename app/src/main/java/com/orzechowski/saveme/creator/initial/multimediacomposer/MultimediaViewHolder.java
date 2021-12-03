package com.orzechowski.saveme.creator.initial.multimediacomposer;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.multimedia.database.Multimedia;

public class MultimediaViewHolder extends RecyclerView.ViewHolder
{
    TextView mFileName;
    Button mUploadButton;
    EditText mDisplayTime;
    CheckBox mLoopCheckBox;
    ImageView mDeleteMultimedia, mMiniature;
    Multimedia mMultimedia;
    Activity mActivity;

    public MultimediaViewHolder(@NonNull View itemView, Activity requestActivity,
                                MultimediaComposerAdapter.FragmentCallback callback)
    {
        super(itemView);
        mActivity = requestActivity;
        mFileName = itemView.findViewById(R.id.new_multimedia_filename_display);
        mUploadButton = itemView.findViewById(R.id.new_multimedia_upload_button);
        mDisplayTime = itemView.findViewById(R.id.new_multimedia_display_time_input);
        mLoopCheckBox = itemView.findViewById(R.id.new_multimedia_loop_checkbox);
        mMiniature = itemView.findViewById(R.id.new_image_miniature);
        mDeleteMultimedia = itemView.findViewById(R.id.delete_new_multimedia);
        mDeleteMultimedia.setOnClickListener(v -> callback.delete(mMultimedia));
        mDisplayTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String timeText = String.valueOf(mDisplayTime.getText());
                if(mMultimedia != null  && callback != null && !timeText.isEmpty()) {
                    callback.modifyDisplayTime(Integer.parseInt(timeText),
                            mMultimedia.getPosition());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        mLoopCheckBox.setOnCheckedChangeListener((buttonView, isChecked)-> {
            if(mMultimedia != null && callback != null) {
                callback.modifyLoop(isChecked, mMultimedia.getPosition());
            }
        });
        mUploadButton.setOnClickListener(v -> callback.addImage(mMultimedia.getPosition()));
    }
}
