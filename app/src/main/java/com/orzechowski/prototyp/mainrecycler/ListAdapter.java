package com.orzechowski.prototyp.mainrecycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.objects.NumerAlarmowy;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.NumeryViewHolder> {
    private final List<NumerAlarmowy> numery;
    private final LayoutInflater mPompka;

    public ListAdapter(Activity kontekst, List<NumerAlarmowy> listaNumerow) {
        mPompka = kontekst.getLayoutInflater();
        this.numery = listaNumerow;
    }

    @NonNull
    @Override
    public NumeryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View wiersz = mPompka.inflate(R.layout.wiersz_numery, null);
        return new NumeryViewHolder(wiersz);
    }

    @Override
    public void onBindViewHolder(@NonNull NumeryViewHolder numeryholder, int numerWiersza) {
        TextView label = numeryholder.itemView.findViewById(R.id.tekst);
        label.setText(numery.get(numerWiersza).getNazwaUslugi());
    }

    @Override
    public int getItemCount() {
        return numery.size();
    }

    public class NumeryViewHolder extends RecyclerView.ViewHolder{
        public NumeryViewHolder(@NonNull View glownyElementWiersza){
            super(glownyElementWiersza);
        }
    }
}