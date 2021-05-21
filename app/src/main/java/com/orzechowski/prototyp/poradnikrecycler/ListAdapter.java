package com.orzechowski.prototyp.poradnikrecycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.poradnikrecycler.objects.InstructionSet;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.InstructionsViewHolder> {
    private final List<InstructionSet> mInstructions;
    private final LayoutInflater mInflater;
    private final OnViewClickListener mListener;

    public ListAdapter(Activity tutorialActivity, List<InstructionSet> instructions,
                       OnViewClickListener listenerFromSuperclass)
    {
        this.mInflater = tutorialActivity.getLayoutInflater();
        this.mInstructions = instructions;
        this.mListener = listenerFromSuperclass;
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View row = mInflater.inflate(R.layout.row_instructions_rv, null);
        return new InstructionsViewHolder(row, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder instrukcjeholder, int rowNumber) {
        instrukcjeholder.title.setText(mInstructions.get(rowNumber).getTitle());
        instrukcjeholder.brief.setText(mInstructions.get(rowNumber).getInstructions().substring(3));
    }

    @Override
    public int getItemCount() {
        return mInstructions.size();
    }

    public static class InstructionsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView title, brief;
        OnViewClickListener listenerForThisRow;

        public InstructionsViewHolder(@NonNull View viewForThisRow,
                                      OnViewClickListener listenerFromSuperClass)
        {
            super(viewForThisRow);
            this.title = viewForThisRow.findViewById(R.id.title);
            this.brief = viewForThisRow.findViewById(R.id.brief);
            this.listenerForThisRow = listenerFromSuperClass;
            viewForThisRow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            listenerForThisRow.onViewClick(getAdapterPosition());
        }
    }

    public interface OnViewClickListener{
        void onViewClick(int position);
    }
}