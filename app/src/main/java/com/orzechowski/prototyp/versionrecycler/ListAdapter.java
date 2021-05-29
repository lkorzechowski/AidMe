package com.orzechowski.prototyp.versionrecycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.versionrecycler.database.Version;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.VersionViewHolder> {

    private final List<Version> mVersionsList;
    private final LayoutInflater mInflater;
    private final OnViewClickListener mListener;

    public ListAdapter(Activity mainActivity, List<Version> listOfNumbers,
                       OnViewClickListener listenerFromSuperClass)
    {
        mInflater = mainActivity.getLayoutInflater();
        this.mVersionsList = listOfNumbers;
        this.mListener = listenerFromSuperClass;
    }

    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View row = mInflater.inflate(R.layout.row_phone_numbers_rv, null);
        return new VersionViewHolder(row, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionViewHolder numbersHolder, int rowNumber) {
        TextView versionButton = numbersHolder.itemView.findViewById(R.id.version_button);
        versionButton.setText((mVersionsList.get(rowNumber).getText()));
    }

    @Override
    public int getItemCount() {
        return mVersionsList.size();
    }

    public static class VersionViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        OnViewClickListener listenerForThisRow;

        public VersionViewHolder(
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