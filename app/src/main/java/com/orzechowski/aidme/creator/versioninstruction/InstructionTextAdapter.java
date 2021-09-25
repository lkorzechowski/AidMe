package com.orzechowski.aidme.creator.versioninstruction;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;

import java.util.List;

public class InstructionTextAdapter
    extends RecyclerView.Adapter<InstructionTextAdapter.InstructionViewHolder>
{
    private List<InstructionSet> mInstructions = null;
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
        holder.instructionNumber.setText(String.valueOf(instruction.getPosition()));
        holder.instructionText.setText(instruction.getInstructions());
    }

    @Override
    public int getItemCount()
    {
        return (mInstructions==null) ? 0 : mInstructions.size();
    }

    public void setElementList(List<InstructionSet> instructions)
    {
        mInstructions = instructions;
        notifyDataSetChanged();
    }

    public static class InstructionViewHolder extends RecyclerView.ViewHolder
    {
        TextView instructionText, instructionNumber;

        public InstructionViewHolder(@NonNull View itemView)
        {
            super(itemView);
            instructionText = itemView.findViewById(R.id.instruction_text);
            instructionNumber = itemView.findViewById(R.id.instruction_number);
        }
    }
}
