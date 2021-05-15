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
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.InstrukcjeViewHolder> {
    private final List<Instrukcja> instrukcje;
    private final LayoutInflater mPompka;
    private WybranoTytul mWybranoTytul;

    public ListAdapter(Activity kontekst, List<Instrukcja> listaInstrukcji, WybranoTytul wybranoTytul) {
        mPompka = kontekst.getLayoutInflater();
        this.instrukcje = listaInstrukcji;
        this.mWybranoTytul = wybranoTytul;
    }

    @NonNull
    @Override
    public InstrukcjeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View wiersz = mPompka.inflate(R.layout.wiersz_instrukcje, null);
        return new InstrukcjeViewHolder(wiersz, mWybranoTytul);
    }

    @Override
    public void onBindViewHolder(@NonNull InstrukcjeViewHolder instrukcjeholder, int numerWiersza) {
        TextView tytul = instrukcjeholder.tytul;
        TextView instrukcja = instrukcjeholder.instrukcja;
        tytul.setVisibility(View.VISIBLE);
        instrukcja.setVisibility(View.INVISIBLE);
        tytul.setText(instrukcje.get(numerWiersza).getTytul());
        instrukcja.setText(instrukcje.get(numerWiersza).getInstrukcja());
    }

    @Override
    public int getItemCount() {
        return instrukcje.size();
    }

    public class InstrukcjeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tytul, instrukcja;
        WybranoTytul wybranoTytul;

        public InstrukcjeViewHolder(@NonNull View glownyElementWiersza, WybranoTytul wybranoTytul){
            super(glownyElementWiersza);
            tytul = glownyElementWiersza.findViewById(R.id.tytul);
            instrukcja = glownyElementWiersza.findViewById(R.id.instrukcja);
            this.wybranoTytul = wybranoTytul;
            glownyElementWiersza.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            tytul.setVisibility(View.INVISIBLE);
            instrukcja.setVisibility(View.VISIBLE);
            wybranoTytul.onClick(getAdapterPosition());
        }
    }

    public interface WybranoTytul{
        void onClick(int position);
    }
}