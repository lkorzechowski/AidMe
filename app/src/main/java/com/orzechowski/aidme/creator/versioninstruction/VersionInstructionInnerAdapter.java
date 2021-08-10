package com.orzechowski.aidme.creator.versioninstruction;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;
import com.orzechowski.aidme.tutorial.version.database.Version;

import java.util.List;

public class VersionInstructionInnerAdapter
    extends RecyclerView.Adapter<VersionInstructionInnerAdapter.InstructionViewHolder>
{
    private List<InstructionSet> mInstructions = null;
    private final LayoutInflater mInflater;
    private final Version mVersion;
    private final OnClickListener mListener;

    public VersionInstructionInnerAdapter(Activity activity, Version version,
                                          OnClickListener listener)
    {
        mInflater = LayoutInflater.from(activity);
        mVersion = version;
        mListener = listener;
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater
                .inflate(R.layout.row_inner_version_instruction_rv, parent, false);
        return new InstructionViewHolder(row, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position)
    {
        InstructionSet instructionSet = mInstructions.get(position);
        holder.instructionSet = instructionSet;
        holder.instructionNumberButton.setText(String.valueOf(instructionSet.getPosition()));
        holder.version = mVersion;
    }

    @Override
    public int getItemCount()
    {
        if(mInstructions!=null) return mInstructions.size();
        else return 0;
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
        Button instructionNumberButton;
        OnClickListener listener;
        Version version;

        public InstructionViewHolder(@NonNull View itemView, OnClickListener fragmentListener)
        {
            super(itemView);
            instructionNumberButton = itemView.findViewById(R.id.instruction_number_button);
            listener = fragmentListener;
            instructionNumberButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            instructionNumberButton.setBackgroundColor(Color.RED);
            listener.callback(instructionSet, version);
        }
    }

    public interface OnClickListener
    {
        void callback(InstructionSet instructionSet, Version version);
    }
}
