package com.orzechowski.aidme.creator.initial;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContentResolverCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia;

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
        return new MultimediaViewHolder(row, mActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull MultimediaViewHolder holder, int position)
    {
        Multimedia multimedia = mMultimedias.get(position);
        holder.fileName.setText(multimedia.getFullFileName());
        holder.displayTime.setText(String.valueOf(multimedia.getDisplayTime()));
        holder.loopCheckBox.setChecked(multimedia.getLoop());
        holder.callback = mCallback;
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
        FragmentCallback callback;
        Multimedia multimedia;
        Activity activity;

        public MultimediaViewHolder(@NonNull View itemView, Activity requestActivity)
        {
            super(itemView);
            activity = requestActivity;
            fileName = itemView.findViewById(R.id.new_multimedia_filename_display);
            uploadButton = itemView.findViewById(R.id.new_multimedia_upload_button);
            displayTime = itemView.findViewById(R.id.new_multimedia_display_time_input);
            loopCheckBox = itemView.findViewById(R.id.new_multimedia_loop_checkbox);
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
            uploadButton.setOnClickListener(v -> {
                if(ActivityCompat.checkSelfPermission(activity, Manifest.permission
                        .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(activity,
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            121);
                } else {
                    Cursor cursor = ContentResolverCompat.query(activity.getContentResolver(),
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            new String[]{MediaStore.Images.Thumbnails.DATA},
                            null, null, null, null);
                    Log.w("turkusowy", String.valueOf(cursor.getCount()));
                    callback.addImage(cursor, multimedia.getPosition());
                }
            });
        }
    }

    public interface FragmentCallback
    {
        void delete(Multimedia multimedia);
        void modifyDisplayTime(int time, int position);
        void modifyLoop(boolean loop, int position);
        void addImage(Cursor cursor, int position);
    }
}
