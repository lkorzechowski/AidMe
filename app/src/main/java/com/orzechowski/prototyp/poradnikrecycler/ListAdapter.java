package com.orzechowski.prototyp.poradnikrecycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.poradnikrecycler.objects.Instrukcja;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.InstrukcjeViewHolder> {
    private final List<Instrukcja> instrukcje;
    private final LayoutInflater mPompka;

    public ListAdapter(Activity kontekst, List<Instrukcja> listaInstrukcji) {
        mPompka = kontekst.getLayoutInflater();
        this.instrukcje = listaInstrukcji;
    }

    @NonNull
    @Override
    public InstrukcjeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View wiersz = mPompka.inflate(R.layout.wiersz_instrukcje, null);
        return new InstrukcjeViewHolder(wiersz);
    }

    @Override
    public void onBindViewHolder(@NonNull InstrukcjeViewHolder instrukcjeholder, int numerWiersza) {
        TextView tytul = instrukcjeholder.itemView.findViewById(R.id.tytul);
        tytul.setVisibility(View.VISIBLE);
        TextView instrukcja = instrukcjeholder.itemView.findViewById(R.id.instrukcja);
        instrukcja.setVisibility(View.INVISIBLE);
        tytul.setText(instrukcje.get(numerWiersza).getTytul());
        instrukcja.setText(instrukcje.get(numerWiersza).getInstrukcja());
    }

    @Override
    public int getItemCount() {
        return instrukcje.size();
    }

    public class InstrukcjeViewHolder extends RecyclerView.ViewHolder{
        public InstrukcjeViewHolder(@NonNull View glownyElementWiersza){
            super(glownyElementWiersza);
        }
    }
}