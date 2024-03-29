package com.orzechowski.saveme.creator.tutoriallinks.instructions;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;

import java.util.List;

public class TutorialLinkInstructionAdapter extends RecyclerView.Adapter<InstructionViewHolder>
{
    private List<InstructionSet> mInstructions;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;

    public TutorialLinkInstructionAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater
                .inflate(R.layout.row_instruction_text_rv, parent, false);
        return new InstructionViewHolder(row, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position)
    {
        InstructionSet instructionSet = mInstructions.get(position);
        holder.mInstructionSet = instructionSet;
        holder.mInstructionNumber.setText(String.valueOf(instructionSet.getPosition()));
        holder.mInstructionText.setText(instructionSet.getInstructions());
    }

    @Override
    public int getItemCount()
    {
        return(mInstructions == null) ? 0 : mInstructions.size();
    }

    public void setElementList(List<InstructionSet> instructions)
    {
        mInstructions = instructions;
        notifyDataSetChanged();
    }

    public interface FragmentCallback
    {
        void selectInstruction(InstructionSet instructionSet);
    }
}
