package com.orzechowski.prototyp.poradnikrecycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;;
import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.poradnikrecycler.objects.Instrukcja;

import java.util.LinkedList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.InstrukcjeViewHolder> {
    private final List<Instrukcja> mInstructions;
    private final LayoutInflater mInflater;
    private final List<InstrukcjeViewHolder> mInstructionSetList;
    private OnViewClickListener onViewClickListener;

    public ListAdapter(Activity tutorialActivity, List<Instrukcja> instructionsList,
                       OnViewClickListener listenerFromSuperclass)
    {
        this.mInflater = tutorialActivity.getLayoutInflater();
        this.mInstructions = instructionsList;
        this.mInstructionSetList = new LinkedList<>();
        this.onViewClickListener = listenerFromSuperclass;
    }

    @NonNull
    @Override
    public InstrukcjeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View row = mInflater.inflate(R.layout.wiersz_instrukcje, null);
        return new InstrukcjeViewHolder(row, onViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InstrukcjeViewHolder instrukcjeholder, int rowNumber) {
        TextView title = instrukcjeholder.title;
        TextView instructionsView = instrukcjeholder.instruct;
        title.setVisibility(View.VISIBLE);
        instructionsView.setVisibility(View.GONE);
        title.setText(mInstructions.get(rowNumber).getTitle());
        instructionsView.setText(mInstructions.get(rowNumber).getInstructionSet());
        mInstructionSetList.add(rowNumber, instrukcjeholder);
    }

    @Override
    public int getItemCount() {
        return mInstructions.size();
    }

    public class InstrukcjeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, instruct;
        View divider;
        OnViewClickListener listenerForThisRow;

        public InstrukcjeViewHolder(@NonNull View viewForRow, OnViewClickListener listenerFromSuperClass){
            super(viewForRow);
            this.title = viewForRow.findViewById(R.id.tytul);
            this.instruct = viewForRow.findViewById(R.id.instrukcja);
            this.divider = viewForRow.findViewById(R.id.rv_divider);
            this.listenerForThisRow = listenerFromSuperClass;
            mInstructionSetList.add(this);
            viewForRow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            listenerForThisRow.onViewClick(getAdapterPosition());
        }
    }

    public void expandAt(int position){
        InstrukcjeViewHolder currentView = mInstructionSetList.get(position);
        currentView.title.setVisibility(View.GONE);
        currentView.instruct.setVisibility(View.VISIBLE);
        currentView.divider.setVisibility(View.GONE);
    }

    public void hideAt(int position){
        InstrukcjeViewHolder currentView = mInstructionSetList.get(position);
        currentView.title.setVisibility(View.VISIBLE);
        currentView.instruct.setVisibility(View.GONE);
        currentView.divider.setVisibility(View.VISIBLE);
    }

    public interface OnViewClickListener{
        void onViewClick(int position);
    }
}