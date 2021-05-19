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
import com.orzechowski.prototyp.mainrecycler.objects.EmergencyNumber;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.NumbersViewHolder> {
    private final List<EmergencyNumber> mNumbersList;
    private final LayoutInflater mInflater;
    private final Activity mActivity;
    private final int mRequestPhoneCall = 1;

    public ListAdapter(Activity mainActivity, List<EmergencyNumber> listOfNumbers) {
        mInflater = mainActivity.getLayoutInflater();
        this.mNumbersList = listOfNumbers;
        this.mActivity = mainActivity;
    }

    @NonNull
    @Override
    public NumbersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View wiersz = mInflater.inflate(R.layout.row_phone_numbers_rv, null);
        return new NumbersViewHolder(wiersz);
    }

    @Override
    public void onBindViewHolder(@NonNull NumbersViewHolder numbersHolder, int rowNumber) {
        TextView numer = numbersHolder.itemView.findViewById(R.id.numer);
        TextView usluga = numbersHolder.itemView.findViewById(R.id.usluga);
        numer.setText(String.valueOf(mNumbersList.get(rowNumber).getPhoneNumber()));
        usluga.setText(mNumbersList.get(rowNumber).getServiceName());

        Button przyciskDzwon = numbersHolder.itemView.findViewById(R.id.przycisk_zadzwon);
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

    public static class NumbersViewHolder extends RecyclerView.ViewHolder{
        public NumbersViewHolder(@NonNull View glownyElementWiersza){
            super(glownyElementWiersza);
        }
    }
}