package com.orzechowski.aidme.creator.versioninstruction;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;
import com.orzechowski.aidme.tutorial.version.database.Version;

import java.util.List;

public class VersionInstructionOuterAdapter
        extends RecyclerView.Adapter<VersionInstructionOuterAdapter.VersionViewHolder>
{
    private List<Version> mVersions = null;
    private List<InstructionSet> mInstructions = null;
    private final LayoutInflater mInflater;
    private final Activity mActivity;
    private final FragmentCallback mCallback;

    public VersionInstructionOuterAdapter(Activity activity, FragmentCallback callback)
    {
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater
                .inflate(R.layout.row_outer_version_instruction_rv, parent, false);
        return new VersionViewHolder(row, mActivity, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionViewHolder holder, int position)
    {
        Version version = mVersions.get(position);
        holder.version = version;
        holder.adapter.setElementList(mInstructions);
        holder.label.setText(String.format("Treść wersji: %s", version.getText()));
    }

    @Override
    public int getItemCount()
    {
        return(mVersions==null) ? 0 : mVersions.size();
    }

    public void setElementList(List<Version> versions, List<InstructionSet> instructions)
    {
        mInstructions = instructions;
        mVersions = versions;
        notifyDataSetChanged();
    }

    public static class VersionViewHolder extends RecyclerView.ViewHolder
            implements VersionInstructionInnerAdapter.FragmentCallback
    {
        Version version;
        RecyclerView recycler;
        TextView label;
        VersionInstructionInnerAdapter adapter;
        FragmentCallback callback;

        public VersionViewHolder(@NonNull View itemView, Activity requestActivity,
                                 FragmentCallback fragmentCallback)
        {
            super(itemView);
            callback = fragmentCallback;
            label = itemView.findViewById(R.id.version_instruction_label);
            recycler = itemView.findViewById(R.id.version_instruction_inner_rv);
            recycler.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            adapter = new VersionInstructionInnerAdapter(requestActivity, this);
            recycler.setAdapter(adapter);
        }

        @Override
        public void select(InstructionSet instructionSet)
        {
            callback.select(instructionSet, version);
        }

        @Override
        public void unselect(InstructionSet instructionSet)
        {
            callback.unselect(instructionSet, version);
        }
    }

    public interface FragmentCallback
    {
        void select(InstructionSet instructionSet, Version version);
        void unselect(InstructionSet instructionSet, Version version);
    }
}
