package com.orzechowski.aidme.creator;

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

    public VersionInstructionOuterAdapter(Activity activity)
    {
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater
                .inflate(R.layout.row_outer_version_instruction_rv, parent, false);
        return new VersionViewHolder(row, mActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionViewHolder holder, int position)
    {
        Version version = mVersions.get(position);
        holder.version = version;
        holder.instructions = mInstructions;
        holder.label.setText(String.format("Treść wersji: %s", version.getText()));
    }

    @Override
    public int getItemCount()
    {
        if(mVersions!=null) return mVersions.size();
        else return 0;
    }

    public void setElementList(List<Version> versions, List<InstructionSet> instructions)
    {
        mInstructions = instructions;
        mVersions = versions;
        notifyDataSetChanged();
    }

    public static class VersionViewHolder extends RecyclerView.ViewHolder
    {
        Version version;
        List<InstructionSet> instructions;
        RecyclerView recycler;
        TextView label;
        VersionInstructionInnerAdapter adapter;

        public VersionViewHolder(@NonNull View itemView, Activity requestActivity)
        {
            super(itemView);
            label = itemView.findViewById(R.id.version_instruction_label);
            recycler = itemView.findViewById(R.id.version_instruction_inner_rv);
            recycler.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            adapter = new VersionInstructionInnerAdapter(requestActivity);
            recycler.setAdapter(adapter);
            adapter.setElementList(instructions);
        }
    }
}
