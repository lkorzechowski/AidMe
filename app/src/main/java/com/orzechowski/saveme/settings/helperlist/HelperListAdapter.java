package com.orzechowski.saveme.settings.helperlist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.helper.database.Helper;

import java.util.List;

public class HelperListAdapter extends RecyclerView.Adapter<HelperViewHolder>
{
    private List<Helper> mHelpers;
    private final LayoutInflater mInflater;

    public HelperListAdapter(Activity activity)
    {
        mInflater = LayoutInflater.from(activity);
        mHelpers = null;
    }

    @NonNull
    @Override
    public HelperViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_helper_rv, viewGroup, false);
        return new HelperViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull HelperViewHolder helperHolder, int rowNumber)
    {
        Helper helper = mHelpers.get(rowNumber);
        helperHolder.mNameDisplay.setText(String.format("%s %s %s",
                helper.getTitle(), helper.getName(), helper.getSurname()));
    }

    @Override
    public int getItemCount() {
        return (mHelpers == null) ? 0 : mHelpers.size();
    }

    public void setElementList(List<Helper> helpers)
    {
        mHelpers = helpers;
        notifyDataSetChanged();
    }
}
