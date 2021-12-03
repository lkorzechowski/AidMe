package com.orzechowski.saveme.creator.versioninstruction.versioninstructions;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;
import com.orzechowski.saveme.tutorial.version.database.Version;

public class VersionViewHolder extends RecyclerView.ViewHolder
        implements VersionInstructionInnerAdapter.FragmentCallback
{
    Version mVersion;
    RecyclerView mRecycler;
    TextView mLabel;
    VersionInstructionInnerAdapter mAdapter;
    VersionInstructionOuterAdapter.FragmentCallback mCallback;

    public VersionViewHolder(@NonNull View itemView, Activity requestActivity,
                             VersionInstructionOuterAdapter.FragmentCallback fragmentCallback)
    {
        super(itemView);
        mCallback = fragmentCallback;
        mLabel = itemView.findViewById(R.id.version_instruction_label);
        mRecycler = itemView.findViewById(R.id.version_instruction_inner_rv);
        mRecycler.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new VersionInstructionInnerAdapter(requestActivity, this);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void select(InstructionSet instructionSet)
    {
        mCallback.select(instructionSet, mVersion);
    }

    @Override
    public void unselect(InstructionSet instructionSet)
    {
        mCallback.unselect(instructionSet, mVersion);
    }
}