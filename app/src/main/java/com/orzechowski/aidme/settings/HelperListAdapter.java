package com.orzechowski.aidme.settings;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.database.helper.Helper;

import java.util.List;

public class HelperListAdapter extends RecyclerView.Adapter<HelperListAdapter.HelperViewHolder>
{
    private List<Helper> mHelpers;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;

    public HelperListAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mHelpers = null;
        mCallback = callback;
    }

    @NonNull
    @Override
    public HelperViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_helper_rv, viewGroup, false);
        return new HelperViewHolder(row, mCallback);
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
        return (mHelpers==null) ? 0 : mHelpers.size();
    }

    public void setElementList(List<Helper> helpers)
    {
        mHelpers = helpers;
        notifyDataSetChanged();
    }

    public static class HelperViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView nameDisplay;
        Helper thisHelper;
        FragmentCallback callback;

        public HelperViewHolder(@NonNull View viewForThisRow, FragmentCallback fragmentCallback)
        {
            super(viewForThisRow);
            nameDisplay = viewForThisRow.findViewById(R.id.name_display);
            callback = fragmentCallback;
        }

        @Override
        public void onClick(View v)
        {
            callback.onClick(thisHelper);
        }
    }

    public interface FragmentCallback
    {
        void onClick(Helper helper);
    }
}
