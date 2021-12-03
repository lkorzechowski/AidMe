package com.orzechowski.saveme.creator.versioninstruction.versioninstructions;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;

import java.util.List;

public class VersionInstructionInnerAdapter extends RecyclerView.Adapter<InstructionViewHolder>
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
        holder.mInstructionSet = instructionSet;
        holder.mInstructionNumberButton.setText(String.valueOf(instructionSet.getPosition()));
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

    public interface FragmentCallback
    {
        void select(InstructionSet instructionSet);
        void unselect(InstructionSet instructionSet);
    }
}
