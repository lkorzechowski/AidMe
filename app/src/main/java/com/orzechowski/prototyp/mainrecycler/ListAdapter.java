package com.orzechowski.prototyp.mainrecycler;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.objects.NumerAlarmowy;
import java.util.List;



public class ListAdapter extends RecyclerView.Adapter<ListAdapter.NumeryViewHolder> {
    private final List<NumerAlarmowy> numery;
    private final LayoutInflater mPompka;
    private final Context context;
    private final int REQUEST_PHONE_CALL = 1;

    public ListAdapter(Activity kontekst, List<NumerAlarmowy> listaNumerow, Context appApcontext) {
        mPompka = kontekst.getLayoutInflater();
        this.numery = listaNumerow;
        this.context = appApcontext;
    }

    @NonNull
    @Override
    public NumeryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View wiersz = mPompka.inflate(R.layout.wiersz_numery, null);
        return new NumeryViewHolder(wiersz);
    }

    @Override
    public void onBindViewHolder(@NonNull NumeryViewHolder numeryholder, int numerWiersza) {
        TextView numer = numeryholder.itemView.findViewById(R.id.numer);
        TextView usluga = numeryholder.itemView.findViewById(R.id.usluga);
        numer.setText(String.valueOf(numery.get(numerWiersza).getNumerTelefonu()));
        usluga.setText(numery.get(numerWiersza).getNazwaUslugi());

        Button przyciskDzwon = numeryholder.itemView.findViewById(R.id.przycisk_zadzwon);
        przyciskDzwon.setOnClickListener(v -> {
            Intent phone_intent = new Intent(Intent.ACTION_CALL);
            phone_intent.setData(Uri.parse("tel:" + 123));
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
            } else {
                context.startActivity(phone_intent);
            }
        });
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