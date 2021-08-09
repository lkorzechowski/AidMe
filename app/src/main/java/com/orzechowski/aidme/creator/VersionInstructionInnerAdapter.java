package com.orzechowski.aidme.creator;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;

import java.util.List;

public class VersionInstructionInnerAdapter
    extends RecyclerView.Adapter<VersionInstructionInnerAdapter.InstructionViewHolder>
{
    private List<InstructionSet> mInstructions = null;
    private final LayoutInflater mInflater;

    public VersionInstructionInnerAdapter(Activity activity)
    {
        mInflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater
                .inflate(R.layout.row_inner_version_instruction_rv, parent, false);
        return new InstructionViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position)
    {
        InstructionSet instructionSet = mInstructions.get(position);
        holder.instructionSet = instructionSet;
    }

    @Override
    public int getItemCount()
    {
        if(mInstructions!=null) return mInstructions.size();
        else return 0;
    }

    public void setElementList(List<InstructionSet> instructions)
    {
        mInstructions = instructions;
        notifyDataSetChanged();
    }

    public static class InstructionViewHolder extends RecyclerView.ViewHolder
    {
        InstructionSet instructionSet;

        public InstructionViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }
}
