package com.orzechowski.aidme.creator;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
    private final DeleteMultimedia mDeleteListener;

    public MultimediaComposerAdapter(Activity activity, DeleteMultimedia deleteListener)
    {
        mInflater = LayoutInflater.from(activity);
        mDeleteListener = deleteListener;
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
        holder.deleteListener = mDeleteListener;
        holder.multimedia = multimedia;
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
        ImageView deleteMultimedia;
        DeleteMultimedia deleteListener;
        Multimedia multimedia;

        public MultimediaViewHolder(@NonNull View itemView)
        {
            super(itemView);
            fileName = itemView.findViewById(R.id.new_multimedia_filename_display);
            uploadButton = itemView.findViewById(R.id.new_multimedia_upload_button);
            displayTime = itemView.findViewById(R.id.new_multimedia_display_time_input);
            loopCheckBox = itemView.findViewById(R.id.new_multimedia_loop_checkbox);
            deleteMultimedia = itemView.findViewById(R.id.delete_new_multimedia);
            deleteMultimedia.setOnClickListener(v-> deleteListener.onClick(multimedia));
        }
    }

    public interface DeleteMultimedia
    {
        void onClick(Multimedia multimedia);
    }
}
