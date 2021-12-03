package com.orzechowski.saveme.creator.versioninstruction.versioninstructions;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;

public class InstructionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    InstructionSet mInstructionSet;
    Button mInstructionNumberButton;
    VersionInstructionInnerAdapter.FragmentCallback mCallback;
    boolean mSelected = false;

    public InstructionViewHolder(@NonNull View itemView,
                                 VersionInstructionInnerAdapter.FragmentCallback fragmentListener)
    {
        super(itemView);
        mInstructionNumberButton = itemView.findViewById(R.id.instruction_number_button);
        mInstructionNumberButton.setBackgroundColor(Color.RED);
        mCallback = fragmentListener;
        mInstructionNumberButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(!mSelected) {
            mInstructionNumberButton.setBackgroundColor(Color.GREEN);
            mCallback.select(mInstructionSet);
            mSelected = true;
        } else {
            mInstructionNumberButton.setBackgroundColor(Color.RED);
            mCallback.unselect(mInstructionSet);
            mSelected = false;
        }
    }
}
