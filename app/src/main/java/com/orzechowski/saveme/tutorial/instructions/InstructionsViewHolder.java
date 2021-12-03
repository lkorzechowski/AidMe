package com.orzechowski.saveme.tutorial.instructions;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;

public class InstructionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    TextView mTitle, mBrief;
    InstructionsListAdapter.FragmentCallback mCallback;
    InstructionSet mThisInstructionSet;

    public InstructionsViewHolder(@NonNull View itemView,
                                  InstructionsListAdapter.FragmentCallback fragmentCallback)
    {
        super(itemView);
        mTitle = itemView.findViewById(R.id.title);
        mBrief = itemView.findViewById(R.id.brief);
        mCallback = fragmentCallback;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        mCallback.onClick(mThisInstructionSet);
    }
}
