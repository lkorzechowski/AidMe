package com.orzechowski.saveme.imagebrowser;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;

public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    ImageView mImageView;
    ImageBrowserAdapter.FragmentCallback mCallback;
    Uri mUri;

    public ImageViewHolder(View itemView, ImageBrowserAdapter.FragmentCallback fragmentCallback)
    {
        super(itemView);
        mImageView = itemView.findViewById(R.id.browser_rv_image_view);
        mCallback = fragmentCallback;
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        mCallback.imageClick(mUri);
    }
}