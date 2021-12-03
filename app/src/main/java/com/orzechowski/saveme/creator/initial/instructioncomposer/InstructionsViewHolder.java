package com.orzechowski.saveme.creator.initial.instructioncomposer;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;

public class InstructionsViewHolder extends RecyclerView.ViewHolder
{
    EditText mTitle, mContent, mDisplayTime;
    TextView mPosition, mNarrationFile;
    ImageView mDeleteInstruction;
    Button mUploadNarration;
    InstructionSet mInstruction;

    public InstructionsViewHolder(@NonNull View itemView,
                                  InstructionComposerAdapter.FragmentCallback callback,
                                  Activity activity)
    {
        super(itemView);
        mNarrationFile = itemView.findViewById(R.id.new_instruction_narration_uri_display);
        mTitle = itemView.findViewById(R.id.new_instruction_title);
        mContent = itemView.findViewById(R.id.new_instruction_text);
        mDisplayTime = itemView.findViewById(R.id.new_instruction_time_input);
        mPosition = itemView.findViewById(R.id.new_instruction_position);
        mUploadNarration = itemView.findViewById(R.id.new_instruction_narration_upload_button);
        mDeleteInstruction = itemView.findViewById(R.id.delete_new_instruction);
        mDeleteInstruction.setOnClickListener(v -> callback.delete(mInstruction));
        mUploadNarration.setOnClickListener(v -> {
            if(ActivityCompat.checkSelfPermission(activity, Manifest.permission
                    .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(activity,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        121);
            } else {
                callback.addNarration(mInstruction.getPosition());
            }
        });
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(mInstruction != null && callback != null) {
                    callback.modifyTitle(String.valueOf(mTitle.getText()),
                            mInstruction.getPosition());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(mInstruction != null  && callback != null) {
                    callback.modifyContent(String.valueOf(mContent.getText()),
                            mInstruction.getPosition());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        mDisplayTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String timeText = String.valueOf(mDisplayTime.getText());
                if(mInstruction != null  && callback != null && !timeText.isEmpty()) {
                    callback.modifyDisplayTime(Integer.parseInt(timeText),
                            mInstruction.getPosition());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
