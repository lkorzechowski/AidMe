package com.orzechowski.prototyp.versionrecycler;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.versionrecycler.database.Version;
import java.util.List;

public class VersionsListAdapter extends RecyclerView.Adapter<VersionsListAdapter.VersionViewHolder> {

    private List<Version> mVersionsList;
    private final LayoutInflater mInflater;
    private final OnViewClickListener mListener;

    public VersionsListAdapter(Activity mainActivity)
    {
        this.mInflater = mainActivity.getLayoutInflater();
        this.mVersionsList = null;
        this.mListener = (OnViewClickListener) mainActivity;
    }

    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View row = mInflater.inflate(R.layout.row_versions_rv, null);
        return new VersionViewHolder(row, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionViewHolder versionsHolder, int rowNumber) {
        versionsHolder.thisVersion = mVersionsList.get(rowNumber);
        Button versionButton = versionsHolder.itemView.findViewById(R.id.version_button);
        versionButton.setText((mVersionsList.get(rowNumber).getText()));
    }

    @Override
    public int getItemCount() {
        if(mVersionsList!=null) return mVersionsList.size();
        else return 0;
    }

    public void setElementList(List<Version> versions){
        this.mVersionsList = versions;
        notifyDataSetChanged();
    }

    public static class VersionViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        OnViewClickListener listenerForThisRow;
        Version thisVersion;

        public VersionViewHolder(
                @NonNull View viewForThisRow, OnViewClickListener listenerFromActivity)
        {
            super(viewForThisRow);
            this.listenerForThisRow = listenerFromActivity;
            viewForThisRow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            listenerForThisRow.onViewClick(thisVersion);
        }
    }

    public interface OnViewClickListener{
        void onViewClick(Version version);
    }
}