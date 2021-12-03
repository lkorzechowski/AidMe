package com.orzechowski.saveme.creator.tutoriallinks.instructions;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;

public class InstructionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    InstructionSet mInstructionSet;
    TextView mInstructionNumber, mInstructionText;
    TutorialLinkInstructionAdapter.FragmentCallback mCallback;

    public InstructionViewHolder(@NonNull View itemView,
                                 TutorialLinkInstructionAdapter.FragmentCallback fragmentCallback)
    {
        super(itemView);
        mCallback = fragmentCallback;
        itemView.setOnClickListener(this);
        mInstructionNumber = itemView.findViewById(R.id.instruction_number);
        mInstructionText = itemView.findViewById(R.id.instruction_text);
    }

    @Override
    public void onClick(View v)
    {
        mCallback.selectInstruction(mInstructionSet);
    }
}
