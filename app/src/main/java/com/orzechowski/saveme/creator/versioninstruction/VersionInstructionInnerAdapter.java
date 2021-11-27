package com.orzechowski.saveme.creator.versioninstruction;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;

import java.util.List;

public class VersionInstructionInnerAdapter
    extends RecyclerView.Adapter<VersionInstructionInnerAdapter.InstructionViewHolder>
{
    private List<InstructionSet> mInstructions;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;

    public VersionInstructionInnerAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater
                .inflate(R.layout.row_inner_version_instruction_rv, parent, false);
        return new InstructionViewHolder(row, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position)
    {
        InstructionSet instructionSet = mInstructions.get(position);
        holder.instructionSet = instructionSet;
        holder.instructionNumberButton.setText(String.valueOf(instructionSet.getPosition()));
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

    public static class InstructionViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {
        InstructionSet instructionSet;
        Button instructionNumberButton;
        FragmentCallback callback;
        boolean selected = false;
        int red = Color.argb(100, 200, 0, 0);
        int green = Color.argb(100, 0, 200, 0);

        public InstructionViewHolder(@NonNull View itemView, FragmentCallback fragmentListener)
        {
            super(itemView);
            instructionNumberButton = itemView.findViewById(R.id.instruction_number_button);
            instructionNumberButton.setBackgroundColor(red);
            callback = fragmentListener;
            instructionNumberButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if(!selected) {
                instructionNumberButton.setBackgroundColor(green);
                callback.select(instructionSet);
                selected = true;
            } else {
                instructionNumberButton.setBackgroundColor(red);
                callback.unselect(instructionSet);
                selected = false;
            }
        }
    }

    public interface FragmentCallback
    {
        void select(InstructionSet instructionSet);
        void unselect(InstructionSet instructionSet);
    }
}
