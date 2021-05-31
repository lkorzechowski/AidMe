package com.orzechowski.prototyp.emergencynumbers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.orzechowski.prototyp.R;

import java.util.List;

public class EmergencyNumbersListAdapter extends RecyclerView.Adapter<EmergencyNumbersListAdapter.NumbersViewHolder> {
    private final List<EmergencyNumber> mNumbersList;
    private final LayoutInflater mInflater;
    private final OnViewClickListener mListener;

    public EmergencyNumbersListAdapter(Activity mainActivity, List<EmergencyNumber> listOfNumbers,
                                       OnViewClickListener listenerFromSuperClass)
    {
        mInflater = mainActivity.getLayoutInflater();
        this.mNumbersList = listOfNumbers;
        this.mListener = listenerFromSuperClass;
    }

    @NonNull
    @Override
    public NumbersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View row = mInflater.inflate(R.layout.row_phone_numbers_rv, viewGroup, false);
        return new NumbersViewHolder(row, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NumbersViewHolder numbersHolder, int rowNumber) {
        TextView numer = numbersHolder.itemView.findViewById(R.id.number);
        TextView usluga = numbersHolder.itemView.findViewById(R.id.service);
        numer.setText(String.valueOf(mNumbersList.get(rowNumber).getPhoneNumber()));
        usluga.setText(mNumbersList.get(rowNumber).getServiceName());
    }

    @Override
    public int getItemCount() {
        return mNumbersList.size();
    }

    public static class NumbersViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        OnViewClickListener listenerForThisRow;

        public NumbersViewHolder(
                @NonNull View viewForThisRow, OnViewClickListener listenerFromSuperClass)
        {
            super(viewForThisRow);
            this.listenerForThisRow = listenerFromSuperClass;
            viewForThisRow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            listenerForThisRow.onViewClick(getAdapterPosition());
        }
    }

    public interface OnViewClickListener{
        void onViewClick(int position);
    }
}