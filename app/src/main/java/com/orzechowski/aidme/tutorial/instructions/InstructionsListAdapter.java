package com.orzechowski.aidme.tutorial.instructions;

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

public class InstructionsListAdapter
        extends RecyclerView.Adapter<InstructionsListAdapter.InstructionsViewHolder>
{
    private List<InstructionSet> mInstructions = null;
    private final LayoutInflater mInflater;
    private final OnClickListener mListener;

    public InstructionsListAdapter(Activity activity, OnClickListener listener)
    {
        mInflater = LayoutInflater.from(activity);
        mListener = listener;
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_instructions_rv, parent, false);
        return new InstructionsViewHolder(row, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder instructionsHolder, int rowNumber)
    {
        InstructionSet set = mInstructions.get(rowNumber);
        instructionsHolder.thisInstructionSet = set;
        instructionsHolder.title.setText(set.getTitle());
        instructionsHolder.brief.setText(set.getInstructions());
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

    public static class InstructionsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView title, brief;
        OnClickListener listener;
        InstructionSet thisInstructionSet;

        public InstructionsViewHolder(@NonNull View itemView, OnClickListener fragmentListener)
        {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            brief = itemView.findViewById(R.id.brief);
            listener = fragmentListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            listener.onClick(thisInstructionSet);
        }
    }

    public interface OnClickListener
    {
        void onClick(InstructionSet instructionSet);
    }
}
