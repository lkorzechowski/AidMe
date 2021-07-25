package com.orzechowski.aidme.creator;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia;

import java.util.List;

public class MultimediaComposerAdapter
    extends RecyclerView.Adapter<MultimediaComposerAdapter.MultimediaViewHolder>
{
    private List<Multimedia> mMultimedias = null;
    private final LayoutInflater mInflater;

    public MultimediaComposerAdapter(Activity activity)
    {
        mInflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public MultimediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_new_multimedia_rv, parent, false);
        return new MultimediaViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MultimediaViewHolder holder, int position)
    {
        Multimedia multimedia = mMultimedias.get(position);
        holder.fileName.setText(multimedia.getFullFileName());
        holder.displayTime.setText(String.valueOf(multimedia.getDisplayTime()));
        holder.loopCheckBox.setChecked(multimedia.getLoop());
    }

    @Override
    public int getItemCount()
    {
        if(mMultimedias!=null) return mMultimedias.size();
        else return 0;
    }

    public void setElementList(List<Multimedia> multimedias)
    {
        mMultimedias = multimedias;
        notifyDataSetChanged();
    }

    public static class MultimediaViewHolder extends RecyclerView.ViewHolder
    {
        TextView fileName;
        Button uploadButton;
        EditText displayTime;
        CheckBox loopCheckBox;

        public MultimediaViewHolder(@NonNull View itemView)
        {
            super(itemView);
            fileName = itemView.findViewById(R.id.new_multimedia_filename_display);
            uploadButton = itemView.findViewById(R.id.new_multimedia_uplaod_button);
            displayTime = itemView.findViewById(R.id.new_multimedia_display_time_input);
            loopCheckBox = itemView.findViewById(R.id.new_multimedia_loop_checkbox);
        }
    }
}
