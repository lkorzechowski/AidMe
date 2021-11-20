package com.orzechowski.saveme.creator.initial;

import android.app.Activity;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.multimedia.database.Multimedia;

import java.util.List;

public class MultimediaComposerAdapter
    extends RecyclerView.Adapter<MultimediaComposerAdapter.MultimediaViewHolder>
{
    private List<Multimedia> mMultimedias = null;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;
    private final Activity mActivity;

    public MultimediaComposerAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
        mActivity = activity;
    }

    @NonNull
    @Override
    public MultimediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_new_multimedia_rv, parent, false);
        return new MultimediaViewHolder(row, mActivity, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull MultimediaViewHolder holder, int position)
    {
        Multimedia multimedia = mMultimedias.get(position);
        holder.fileName.setText(multimedia.getFileName());
        holder.displayTime.setText(String.valueOf(multimedia.getDisplayTime()));
        holder.loopCheckBox.setChecked(multimedia.getLoop());
        holder.multimedia = multimedia;
        if(!multimedia.getFileName().isEmpty()) {
            holder.miniature.setVisibility(View.VISIBLE);
            holder.miniature.setImageURI(Uri.parse(multimedia.getFileName()));
        } else holder.miniature.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount()
    {
        return (mMultimedias==null) ? 0: mMultimedias.size();
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
        ImageView deleteMultimedia, miniature;
        Multimedia multimedia;
        Activity activity;

        public MultimediaViewHolder(@NonNull View itemView, Activity requestActivity,
                                    FragmentCallback callback)
        {
            super(itemView);
            activity = requestActivity;
            fileName = itemView.findViewById(R.id.new_multimedia_filename_display);
            uploadButton = itemView.findViewById(R.id.new_multimedia_upload_button);
            displayTime = itemView.findViewById(R.id.new_multimedia_display_time_input);
            loopCheckBox = itemView.findViewById(R.id.new_multimedia_loop_checkbox);
            miniature = itemView.findViewById(R.id.new_image_miniature);
            deleteMultimedia = itemView.findViewById(R.id.delete_new_multimedia);
            deleteMultimedia.setOnClickListener(v-> callback.delete(multimedia));
            displayTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    String timeText = String.valueOf(displayTime.getText());
                    if(multimedia!=null  && callback!=null && !timeText.isEmpty()) {
                        callback.modifyDisplayTime(Integer.parseInt(timeText),
                                multimedia.getPosition());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
            loopCheckBox.setOnCheckedChangeListener((buttonView, isChecked)-> {
                if(multimedia!=null && callback!=null) {
                    callback.modifyLoop(isChecked, multimedia.getPosition());
                }
            });
            uploadButton.setOnClickListener(v -> callback.addImage(multimedia.getPosition()));
        }
    }

    public interface FragmentCallback
    {
        void delete(Multimedia multimedia);
        void modifyDisplayTime(int time, int position);
        void modifyLoop(boolean loop, int position);
        void addImage(int position);
    }
}
