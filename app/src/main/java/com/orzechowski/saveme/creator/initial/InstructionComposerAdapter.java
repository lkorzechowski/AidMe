package com.orzechowski.saveme.creator.initial;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;

import java.util.List;

public class InstructionComposerAdapter
    extends RecyclerView.Adapter<InstructionComposerAdapter.InstructionsViewHolder>
{
    private List<InstructionSet> mInstructions;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;
    private final Activity mActivity;

    public InstructionComposerAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
        mActivity = activity;
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_new_instruction_rv, parent, false);
        return new InstructionsViewHolder(row, mCallback, mActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder holder, int position)
    {
        InstructionSet set = mInstructions.get(position);
        holder.title.setText(set.getTitle());
        holder.instruction = set;
        holder.content.setText(set.getInstructions());
        holder.displayTime.setText(String.valueOf(set.getTime()));
        holder.position.setText(String.valueOf(set.getPosition()+1));
        holder.narrationFile.setText(set.getNarrationFile());
    }

    @Override
    public int getItemCount()
    {
        return (mInstructions == null) ? 0 : mInstructions.size();
    }

    public void setElementList(List<InstructionSet> instructions)
    {
        mInstructions = instructions;
        notifyDataSetChanged();
    }

    public static class InstructionsViewHolder extends RecyclerView.ViewHolder
    {
        EditText title, content, displayTime;
        TextView position, narrationFile;
        ImageView deleteInstruction;
        Button uploadNarration;
        InstructionSet instruction;

        public InstructionsViewHolder(@NonNull View itemView, FragmentCallback callback,
                                      Activity activity)
        {
            super(itemView);
            narrationFile = itemView.findViewById(R.id.new_instruction_narration_uri_display);
            title = itemView.findViewById(R.id.new_instruction_title);
            content = itemView.findViewById(R.id.new_instruction_text);
            displayTime = itemView.findViewById(R.id.new_instruction_time_input);
            position = itemView.findViewById(R.id.new_instruction_position);
            uploadNarration = itemView.findViewById(R.id.new_instruction_narration_upload_button);
            deleteInstruction = itemView.findViewById(R.id.delete_new_instruction);
            deleteInstruction.setOnClickListener(v -> callback.delete(instruction));
            uploadNarration.setOnClickListener(v -> {
                if(ActivityCompat.checkSelfPermission(activity, Manifest.permission
                        .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(activity,
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            121);
                } else {
                    callback.addNarration(instruction.getPosition());
                }
            });
            title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if(instruction != null && callback != null) {
                        callback.modifyTitle(String.valueOf(title.getText()),
                                instruction.getPosition());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
            content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if(instruction != null  && callback != null) {
                        callback.modifyContent(String.valueOf(content.getText()),
                                instruction.getPosition());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
            displayTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    String timeText = String.valueOf(displayTime.getText());
                    if(instruction != null  && callback != null && !timeText.isEmpty()) {
                        callback.modifyDisplayTime(Integer.parseInt(timeText),
                                instruction.getPosition());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    public interface FragmentCallback
    {
        void delete(InstructionSet instructionSet);
        void modifyTitle(String title, int position);
        void modifyContent(String content, int position);
        void modifyDisplayTime(int time, int position);
        void addNarration(int position);
    }
}
