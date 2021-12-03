package com.orzechowski.saveme.creator.versioninstruction.instructions;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;

public class InstructionViewHolder extends RecyclerView.ViewHolder
{
    TextView mInstructionText, mInstructionNumber;

    public InstructionViewHolder(@NonNull View itemView)
    {
        super(itemView);
        mInstructionText = itemView.findViewById(R.id.instruction_text);
        mInstructionNumber = itemView.findViewById(R.id.instruction_number);
    }
}
