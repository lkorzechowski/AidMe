package com.orzechowski.saveme.creator.versioninstruction.instructions;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;

import java.util.List;

public class InstructionTextAdapter extends RecyclerView.Adapter<InstructionViewHolder>
{
    private List<InstructionSet> mInstructions;
    private final LayoutInflater mInflater;

    public InstructionTextAdapter(Activity activity)
    {
        mInflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_instruction_text_rv, parent, false);
        return new InstructionViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position)
    {
        InstructionSet instruction = mInstructions.get(position);
        holder.mInstructionNumber.setText(String.valueOf(instruction.getPosition()));
        holder.mInstructionText.setText(instruction.getInstructions());
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
}
