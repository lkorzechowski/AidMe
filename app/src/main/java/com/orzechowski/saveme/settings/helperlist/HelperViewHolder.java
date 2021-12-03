package com.orzechowski.saveme.settings.helperlist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;

public class HelperViewHolder extends RecyclerView.ViewHolder
{
    TextView mNameDisplay;

    public HelperViewHolder(@NonNull View viewForThisRow)
    {
        super(viewForThisRow);
        mNameDisplay = viewForThisRow.findViewById(R.id.name_display);
    }
}