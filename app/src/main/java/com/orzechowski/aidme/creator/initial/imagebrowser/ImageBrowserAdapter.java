package com.orzechowski.aidme.creator.initial.imagebrowser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;

import java.util.List;

public class ImageBrowserAdapter extends RecyclerView.Adapter<ImageBrowserAdapter.ImageViewHolder>
{
    private List<Image> mImages;
    private final FragmentCallback mCallback;

    public ImageBrowserAdapter(FragmentCallback callback)
    {
        mCallback = callback;
    }

    public void setElementList(List<Image> images)
    {
        mImages = images;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_image_browser, parent, false);
        return new ImageViewHolder(view, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position)
    {
        Image image = mImages.get(position);
        holder.image = image;
        holder.imageView.setImageURI(image.getContentUri());
    }

    @Override
    public int getItemCount()
    {
        return (mImages == null) ? 0 : mImages.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        ImageView imageView;
        Image image;
        FragmentCallback callback;

        public ImageViewHolder(View itemView, FragmentCallback fragmentCallback)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.browser_rv_image_view);
            callback = fragmentCallback;
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            callback.imageClick(image);
        }
    }

    public interface FragmentCallback
    {
        void imageClick(Image image);
    }
}
