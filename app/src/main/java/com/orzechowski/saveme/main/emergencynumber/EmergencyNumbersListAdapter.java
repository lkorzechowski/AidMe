package com.orzechowski.saveme.main.emergencynumber;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;

import java.util.List;

public class EmergencyNumbersListAdapter
        extends RecyclerView.Adapter<NumberViewHolder>
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
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_phone_numbers_rv, viewGroup, false);
        return new NumberViewHolder(row, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder numbersHolder, int rowNumber)
    {
        EmergencyNumber emergencyNumber = mNumbersList.get(rowNumber);
        numbersHolder.number = emergencyNumber.getPhoneNumber();
        if(rowNumber < 4) {
            if (numbersHolder.number == 999) {
                numbersHolder.mIcon.setImageResource(R.drawable.ic_hospital);
            } else if (numbersHolder.number == 997) {
                numbersHolder.mIcon.setImageResource(R.drawable.ic_police);
            } else if (numbersHolder.number == 998) {
                numbersHolder.mIcon.setImageResource(R.drawable.ic_flame);
            } else if (numbersHolder.number == 112) {
                numbersHolder.mIcon.setImageResource(R.drawable.ic_exclamation);
            }
        } else numbersHolder.mIcon.setImageResource(R.drawable.ic_phone);
        numbersHolder.mNumberDisplay.setText(String.valueOf(numbersHolder.number));
        numbersHolder.mService.setText(emergencyNumber.getServiceName());
    }

    @Override
    public int getItemCount()
    {
        return mNumbersList.size();
    }

    public interface FragmentCallback
    {
        void onViewClick(int number);
    }
}
