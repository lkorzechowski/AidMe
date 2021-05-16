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
    private final Activity mActivity;
    private final List<InstrukcjeViewHolder> viewHolders;
    private Player currentPlayer;

    public ListAdapter(Activity tutorialActivity, List<Instrukcja> instructionsList) {
        this.mInflater = tutorialActivity.getLayoutInflater();
        this.mInstructions = instructionsList;
        this.mActivity = tutorialActivity;
        this.viewHolders = new LinkedList<>();
    }

    @NonNull
    @Override
    public InstrukcjeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View row = mInflater.inflate(R.layout.wiersz_instrukcje, null);
        return new InstrukcjeViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull InstrukcjeViewHolder instrukcjeholder, int rowNumber) {
        TextView title = instrukcjeholder.title;
        TextView instructionsView = instrukcjeholder.instruct;
        title.setVisibility(View.VISIBLE);
        instructionsView.setVisibility(View.GONE);
        title.setText(mInstructions.get(rowNumber).getTytul());
        instructionsView.setText(mInstructions.get(rowNumber).getInstrukcja());
    }

    @Override
    public int getItemCount() {
        return mInstructions.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull InstrukcjeViewHolder holder) {
        if(holder.getAdapterPosition()==1) play(0);
    }

    public class InstrukcjeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, instruct;
        View divider;

        public InstrukcjeViewHolder(@NonNull View viewForRow){
            super(viewForRow);
            this.title = viewForRow.findViewById(R.id.tytul);
            this.instruct = viewForRow.findViewById(R.id.instrukcja);
            this.divider= viewForRow.findViewById(R.id.rv_divider);
            viewForRow.setOnClickListener(this);
            viewHolders.add(this);
        }

        @Override
        public void onClick(View v) {
            play(getAdapterPosition());
        }
    }

    public class Player extends Thread {
        private final int time, line;
        private final TextView title, instruction;
        private final View divider;

        Player(int position){
            line = position;
            time = mInstructions.get(line).getCzas();
            InstrukcjeViewHolder currentView = viewHolders.get(line);
            title = currentView.title;
            instruction = currentView.instruct;
            divider = currentView.divider;
        }

        @Override
        public void run(){
            mActivity.runOnUiThread(() -> {
                title.setVisibility(View.INVISIBLE);
                instruction.setVisibility(View.VISIBLE);
                divider.setVisibility(View.INVISIBLE);
            });
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            mActivity.runOnUiThread(() -> {
                title.setVisibility(View.VISIBLE);
                instruction.setVisibility(View.GONE);
                divider.setVisibility(View.VISIBLE);
            });
            mActivity.runOnUiThread(() -> play(line+1));
        }
    }

    public void play(int position){
        if(currentPlayer!=null){
            currentPlayer.title.setVisibility(View.VISIBLE);
            currentPlayer.instruction.setVisibility(View.GONE);
            currentPlayer.interrupt();
        }
        if(position < viewHolders.size()) {
            currentPlayer = new Player(position);
            currentPlayer.start();
        }
    }
}