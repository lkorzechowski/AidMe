package com.orzechowski.aidme.creator.tutoriallinks;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;

import java.util.List;

public class TutorialLinkInstructionAdapter
    extends RecyclerView.Adapter<TutorialLinkInstructionAdapter.InstructionViewHolder>
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
        holder.instructionSet = instructionSet;
        holder.instructionNumber.setText(String.valueOf(instructionSet.getPosition()));
        holder.instructionText.setText(instructionSet.getInstructions());
    }

    @Override
    public int getItemCount()
    {
        return(mInstructions==null) ? 0 : mInstructions.size();
    }

    public void setElementList(List<InstructionSet> instructions)
    {
        mInstructions = instructions;
        notifyDataSetChanged();
    }

    public static class InstructionViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        InstructionSet instructionSet;
        TextView instructionNumber, instructionText;
        FragmentCallback callback;

        public InstructionViewHolder(@NonNull View itemView, FragmentCallback fragmentCallback)
        {
            super(itemView);
            callback = fragmentCallback;
            instructionNumber = itemView.findViewById(R.id.instruction_number);
            instructionText = itemView.findViewById(R.id.instruction_text);
        }

        @Override
        public void onClick(View v)
        {
            callback.selectInstruction(instructionSet);
        }
    }

    public interface FragmentCallback
    {
        void selectInstruction(InstructionSet instructionSet);
    }
}
