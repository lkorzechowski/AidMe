package com.orzechowski.saveme.tutorial.instructions;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;

import java.util.List;

public class InstructionsListAdapter
        extends RecyclerView.Adapter<InstructionsListAdapter.InstructionsViewHolder>
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
        instructionsHolder.thisInstructionSet = set;
        instructionsHolder.title.setText(set.getTitle());
        instructionsHolder.brief.setText(set.getInstructions());
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

    public static class InstructionsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView title, brief;
        FragmentCallback callback;
        InstructionSet thisInstructionSet;

        public InstructionsViewHolder(@NonNull View itemView, FragmentCallback fragmentCallback)
        {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            brief = itemView.findViewById(R.id.brief);
            callback = fragmentCallback;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            callback.onClick(thisInstructionSet);
        }
    }

    public interface FragmentCallback
    {
        void onClick(InstructionSet instructionSet);
    }
}
