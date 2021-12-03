package com.orzechowski.saveme.creator.versioninstruction.versioninstructions;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;
import com.orzechowski.saveme.tutorial.version.database.Version;

import java.util.List;

public class VersionInstructionOuterAdapter extends RecyclerView.Adapter<VersionViewHolder>
{
    private List<Version> mVersions;
    private List<InstructionSet> mInstructions;
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
        holder.mVersion = version;
        holder.mAdapter.setElementList(mInstructions);
        holder.mLabel.setText(String.format("%s%s", mActivity.getString(R.string.version_text),
                version.getText()));
    }

    @Override
    public int getItemCount()
    {
        return(mVersions == null) ? 0 : mVersions.size();
    }

    public void setElementList(List<Version> versions, List<InstructionSet> instructions)
    {
        mInstructions = instructions;
        mVersions = versions;
        notifyDataSetChanged();
    }

    public interface FragmentCallback
    {
        void select(InstructionSet instructionSet, Version version);
        void unselect(InstructionSet instructionSet, Version version);
    }
}
