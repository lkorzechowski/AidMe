package com.orzechowski.prototyp.instructionsrecycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.instructionsrecycler.database.InstructionSet;

import java.util.LinkedList;
import java.util.List;

public class InstructionsListAdapter extends RecyclerView.Adapter<InstructionsListAdapter.InstructionsViewHolder> {

    private final List<InstructionSet> mInstructions;
    private final LayoutInflater mInflater;
    private final OnClickListener mListener;
    private final List<Integer> mVersion;

    public InstructionsListAdapter(Activity activity, String versionRaw)
    {
        this.mInflater = activity.getLayoutInflater();
        this.mListener = (OnClickListener) activity;
        this.mVersion = new LinkedList<>();
        this.mInstructions = new LinkedList<>();
        for(int i = 0; i < versionRaw.length(); i++){
            mVersion.add((int) versionRaw.charAt(i));
        }
    }

    public InstructionSet getInstructionSet(int position){
        return mInstructions.get(position);
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View row = mInflater.inflate(R.layout.row_instructions_rv, viewGroup, false);
        return new InstructionsViewHolder(row, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder instrukcjeholder, int rowNumber) {
        instrukcjeholder.thisInstructionSet = mInstructions.get(rowNumber);
        instrukcjeholder.title.setText(mInstructions.get(rowNumber).getTitle());
        instrukcjeholder.brief.setText(mInstructions.get(rowNumber).getInstructions().substring(3));
    }

    @Override
    public int getItemCount() {
        return mInstructions.size();
    }

    public void setElementList(List<InstructionSet> instructions){
        for(InstructionSet instruction : instructions){
            if(mVersion.contains(instruction.getPosition())){
                mInstructions.add(instruction);
            }
        }
        notifyDataSetChanged();
    }

    public static class InstructionsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView title, brief;
        OnClickListener listenerForThisRow;
        InstructionSet thisInstructionSet;

        public InstructionsViewHolder(@NonNull View viewForThisRow,
                                      OnClickListener listenerFromActivity)
        {
            super(viewForThisRow);
            this.title = viewForThisRow.findViewById(R.id.title);
            this.brief = viewForThisRow.findViewById(R.id.brief);
            this.listenerForThisRow = listenerFromActivity;
            viewForThisRow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            listenerForThisRow.onClick(thisInstructionSet);
        }
    }

    public interface OnClickListener{
        void onClick(InstructionSet instructionSet);
    }
}