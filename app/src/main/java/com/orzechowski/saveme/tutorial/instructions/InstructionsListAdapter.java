package com.orzechowski.saveme.tutorial.instructions;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;

import java.util.List;

public class InstructionsListAdapter extends RecyclerView.Adapter<InstructionsViewHolder>
{
    private List<InstructionSet> mInstructions;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;

    public InstructionsListAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_instructions_rv, parent, false);
        return new InstructionsViewHolder(row, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder instructionsHolder, int rowNumber)
    {
        InstructionSet set = mInstructions.get(rowNumber);
        instructionsHolder.mThisInstructionSet = set;
        instructionsHolder.mTitle.setText(set.getTitle());
        instructionsHolder.mBrief.setText(set.getInstructions());
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

    public interface FragmentCallback
    {
        void onClick(InstructionSet instructionSet);
    }
}
