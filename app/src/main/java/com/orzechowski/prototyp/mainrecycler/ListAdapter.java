package com.orzechowski.prototyp.mainrecycler;

import android.Manifest;
import android.app.Activity;
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
import com.orzechowski.prototyp.mainrecycler.objects.NumerAlarmowy;
import java.util.List;



public class ListAdapter extends RecyclerView.Adapter<ListAdapter.NumeryViewHolder> {
    private final List<NumerAlarmowy> mNumbersList;
    private final LayoutInflater mInflater;
    private final Activity mActivity;
    private final int mRequestPhoneCall = 1;

    public ListAdapter(Activity kontekst, List<NumerAlarmowy> listaNumerow) {
        mInflater = kontekst.getLayoutInflater();
        this.mNumbersList = listaNumerow;
        this.mActivity = kontekst;
    }

    @NonNull
    @Override
    public NumeryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View wiersz = mInflater.inflate(R.layout.wiersz_numery, null);
        return new NumeryViewHolder(wiersz);
    }

    @Override
    public void onBindViewHolder(@NonNull NumeryViewHolder numeryholder, int numerWiersza) {
        TextView numer = numeryholder.itemView.findViewById(R.id.numer);
        TextView usluga = numeryholder.itemView.findViewById(R.id.usluga);
        numer.setText(String.valueOf(mNumbersList.get(numerWiersza).getPhoneNumber()));
        usluga.setText(mNumbersList.get(numerWiersza).getServiceName());

        Button przyciskDzwon = numeryholder.itemView.findViewById(R.id.przycisk_zadzwon);
        przyciskDzwon.setOnClickListener(v -> {
            Intent phone_intent = new Intent(Intent.ACTION_CALL);
            phone_intent.setData(Uri.parse("tel:" + 123));
            if (ContextCompat.checkSelfPermission(mActivity.getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CALL_PHONE}, mRequestPhoneCall);
            } else {
                mActivity.startActivity(phone_intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNumbersList.size();
    }

    public class NumeryViewHolder extends RecyclerView.ViewHolder{
        public NumeryViewHolder(@NonNull View glownyElementWiersza){
            super(glownyElementWiersza);
        }
    }
}