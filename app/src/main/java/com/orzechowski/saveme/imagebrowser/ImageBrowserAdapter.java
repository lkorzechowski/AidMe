package com.orzechowski.saveme.imagebrowser;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;

import java.util.List;

public class ImageBrowserAdapter extends RecyclerView.Adapter<ImageViewHolder>
{
    private List<Uri> mUris;
    private final FragmentCallback mCallback;
    private final LayoutInflater mInflater;

    public ImageBrowserAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    public void setElementList(List<Uri> uris)
    {
        mUris = uris;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.row_image_browser, parent, false);
        return new ImageViewHolder(view, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position)
    {
        Uri uri = mUris.get(position);
        holder.mUri = uri;
        holder.mImageView.setImageURI(uri);
    }

    @Override
    public int getItemCount()
    {
        return (mUris == null) ? 0 : mUris.size();
    }

    public interface FragmentCallback
    {
        void imageClick(Uri uri);
    }
}
