package com.orzechowski.saveme.main.emergencynumber;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;

import java.util.List;

public class EmergencyNumbersListAdapter
        extends RecyclerView.Adapter<EmergencyNumbersListAdapter.NumbersViewHolder>
{
    private final List<EmergencyNumber> mNumbersList;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;

    public EmergencyNumbersListAdapter(Activity mainActivity, List<EmergencyNumber> listOfNumbers,
                                       FragmentCallback callback)
    {
        mInflater = mainActivity.getLayoutInflater();
        mNumbersList = listOfNumbers;
        mCallback = callback;
    }

    @NonNull
    @Override
    public NumbersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_phone_numbers_rv, viewGroup, false);
        return new NumbersViewHolder(row, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull NumbersViewHolder numbersHolder, int rowNumber)
    {
        TextView numer = numbersHolder.itemView.findViewById(R.id.number);
        TextView usluga = numbersHolder.itemView.findViewById(R.id.service);
        ImageView icon = numbersHolder.itemView.findViewById(R.id.icon_phone);
        EmergencyNumber emergencyNumber = mNumbersList.get(rowNumber);
        int number = emergencyNumber.getPhoneNumber();
        if(rowNumber < 4) {
            if (number == 999) {
                icon.setImageResource(R.drawable.ic_hospital);
            } else if (number == 997) {
                icon.setImageResource(R.drawable.ic_police);
            } else if (number == 998) {
                icon.setImageResource(R.drawable.ic_flame);
            } else if (number == 112) {
                icon.setImageResource(R.drawable.ic_exclamation);
            }
        } else icon.setImageResource(R.drawable.ic_phone);
        numer.setText(String.valueOf(number));
        usluga.setText(emergencyNumber.getServiceName());
    }

    @Override
    public int getItemCount()
    {
        return mNumbersList.size();
    }

    public static class NumbersViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        FragmentCallback callback;

        public NumbersViewHolder(@NonNull View itemView, FragmentCallback fragmentCallback)
        {
            super(itemView);
            callback = fragmentCallback;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            callback.onViewClick(getAdapterPosition());
        }
    }

    public interface FragmentCallback
    {
        void onViewClick(int position);
    }
}
