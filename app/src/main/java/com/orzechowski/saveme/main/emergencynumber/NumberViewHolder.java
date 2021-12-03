package com.orzechowski.saveme.main.emergencynumber;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;

public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    EmergencyNumbersListAdapter.FragmentCallback mCallback;
    int number;
    TextView mNumberDisplay, mService;
    ImageView mIcon;

    public NumberViewHolder(@NonNull View itemView,
                            EmergencyNumbersListAdapter.FragmentCallback fragmentCallback)
    {
        super(itemView);
        mCallback = fragmentCallback;
        mNumberDisplay = itemView.findViewById(R.id.number);
        mService = itemView.findViewById(R.id.service);
        mIcon = itemView.findViewById(R.id.icon_phone);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        mCallback.onViewClick(number);
    }
}
