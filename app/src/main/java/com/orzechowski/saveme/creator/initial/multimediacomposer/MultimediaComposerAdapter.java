package com.orzechowski.saveme.creator.initial.multimediacomposer;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.multimedia.database.Multimedia;

import java.util.List;

public class MultimediaComposerAdapter
    extends RecyclerView.Adapter<MultimediaViewHolder>
{
    private List<Multimedia> mMultimedias;
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
        holder.mFileName.setText(multimedia.getFileName());
        holder.mDisplayTime.setText(String.valueOf(multimedia.getDisplayTime()));
        holder.mLoopCheckBox.setChecked(multimedia.getLoop());
        holder.mMultimedia = multimedia;
        if(!multimedia.getFileName().isEmpty()) {
            holder.mMiniature.setVisibility(View.VISIBLE);
            holder.mMiniature.setImageURI(Uri.parse(multimedia.getFileName()));
        } else holder.mMiniature.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount()
    {
        return (mMultimedias == null) ? 0: mMultimedias.size();
    }

    public void setElementList(List<Multimedia> multimedias)
    {
        mMultimedias = multimedias;
        notifyDataSetChanged();
    }

    public interface FragmentCallback
    {
        void delete(Multimedia multimedia);
        void modifyDisplayTime(int time, int position);
        void modifyLoop(boolean loop, int position);
        void addImage(int position);
    }
}
