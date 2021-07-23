package com.orzechowski.aidme.creator;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;

import java.util.List;

public class InstructionComposerAdapter
    extends RecyclerView.Adapter<InstructionComposerAdapter.InstructionsViewHolder>
{
    private List<InstructionSet> mInstructions = null;
    private final LayoutInflater mInflater;

    public InstructionComposerAdapter(Activity activity)
    {
        mInflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_new_instruction_rv, parent, false);
        return new InstructionsViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder holder, int position)
    {
        InstructionSet set = mInstructions.get(position);
        holder.title.setText(set.getTitle());
        holder.content.setText(set.getInstructions());
        holder.displayTime.setText(set.getTime());
        holder.position.setText(set.getPosition()+1);
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
    {
        EditText title, content, displayTime;
        TextView position;

        public InstructionsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            title = itemView.findViewById(R.id.new_instruction_title);
            content = itemView.findViewById(R.id.new_instruction_text);
            displayTime = itemView.findViewById(R.id.new_instruction_time);
            position = itemView.findViewById(R.id.new_instruction_position);
        }
    }
}
