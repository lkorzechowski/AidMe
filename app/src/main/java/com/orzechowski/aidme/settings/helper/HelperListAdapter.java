package com.orzechowski.aidme.settings.helper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.settings.helper.database.Helper;

import java.util.List;

public class HelperListAdapter extends RecyclerView.Adapter<HelperListAdapter.HelperViewHolder>
{
    private List<Helper> mHelpers;
    private final LayoutInflater mInflater;
    private final OnClickListener mListener;

    public HelperListAdapter(Activity activity, OnClickListener listener)
    {
        mInflater = LayoutInflater.from(activity);
        mHelpers = null;
        mListener = listener;
    }

    @NonNull
    @Override
    public HelperViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_helper_rv, viewGroup, false);
        return new HelperViewHolder(row, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HelperViewHolder helperHolder, int rowNumber)
    {
        Helper helper = mHelpers.get(rowNumber);
        helperHolder.thisHelper = helper;
        helperHolder.nameDisplay.setText(String.format("%s %s %s",
                helper.getTitle(), helper.getName(), helper.getSurname()));
    }

    @Override
    public int getItemCount() {
        if(mHelpers!=null) return mHelpers.size();
        else return 0;
    }

    public void setElementList(List<Helper> helpers)
    {
        mHelpers = helpers;
        notifyDataSetChanged();
    }

    public static class HelperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView nameDisplay;
        Helper thisHelper;
        OnClickListener listenerForThisRow;

        public HelperViewHolder(@NonNull View viewForThisRow, OnClickListener listenerFromActivity)
        {
            super(viewForThisRow);
            nameDisplay = viewForThisRow.findViewById(R.id.name_display);
            listenerForThisRow = listenerFromActivity;
        }

        @Override
        public void onClick(View v)
        {
            listenerForThisRow.onClick(thisHelper);
        }
    }

    public interface OnClickListener
    {
        void onClick(Helper helper);
    }
}
