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
    private final WybranoTytul mWybranoTytul;
    private final Activity mActivity;

    public ListAdapter(Activity kontekst, List<Instrukcja> listaInstrukcji, WybranoTytul wybranoTytul) {
        this.mPompka = kontekst.getLayoutInflater();
        this.instrukcje = listaInstrukcji;
        this.mWybranoTytul = wybranoTytul;
        this.mActivity = kontekst;
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
        instrukcja.setVisibility(View.GONE);
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
            Pokaz pokaz = new Pokaz();
            pokaz.start();
            wybranoTytul.onClick(getAdapterPosition());
        }

        public class Pokaz extends Thread {
            @Override
            public void run(){
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tytul.setVisibility(View.INVISIBLE);
                        instrukcja.setVisibility(View.VISIBLE);
                    }
                });
                try {
                    Thread.sleep(1000); //1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tytul.setVisibility(View.VISIBLE);
                        instrukcja.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public interface WybranoTytul{
        void onClick(int position);
    }
}