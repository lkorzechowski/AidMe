package com.orzechowski.aidme.tutorial.recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.database.InstructionSet;
import java.util.List;

public class InstructionsListAdapter extends RecyclerView.Adapter<InstructionsListAdapter.InstructionsViewHolder> {

    private List<InstructionSet> mInstructions;
    private final LayoutInflater mInflater;
    private final OnClickListener mListener;

    public InstructionsListAdapter(Activity activity, OnClickListener listener)
    {
        mInflater = LayoutInflater.from(activity);
        mListener = listener;
        mInstructions = null;
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View row = mInflater.inflate(R.layout.row_instructions_rv, viewGroup, false);
        return new InstructionsViewHolder(row, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder instrukcjeholder, int rowNumber) {
        InstructionSet set = mInstructions.get(rowNumber);
        instrukcjeholder.thisInstructionSet = set;
        instrukcjeholder.title.setText(set.getTitle());
        instrukcjeholder.brief.setText(set.getInstructions());
    }

    @Override
    public int getItemCount() {
        if(mInstructions!=null) return mInstructions.size();
        else return 0;
    }

    public void setElementList(List<InstructionSet> instructions){
        mInstructions = instructions;
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
            title = viewForThisRow.findViewById(R.id.title);
            brief = viewForThisRow.findViewById(R.id.brief);
            listenerForThisRow = listenerFromActivity;
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