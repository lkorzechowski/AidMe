package com.orzechowski.aidme.creator.initial;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;

import java.io.File;

public class ImageBrowserAdapter extends RecyclerView.Adapter<ImageBrowserAdapter.ViewHolder>
{
    private Cursor mCursor;
    private final Activity mActivity;
    private final OnClickThumbListener mOnClickThumbListener;

    public interface OnClickThumbListener
    {
        void OnClickImage(Uri imageUri);
        void OnClickVideo(Uri videoUri);
    }

    public ImageBrowserAdapter(Activity activity, OnClickThumbListener listener)
    {
        mActivity = activity;
        mOnClickThumbListener = listener;
    }

    public void setElementList(Cursor cursor)
    {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_image_browser, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Bitmap bitmap = getBitmapFromMediaStore(position);
        if(bitmap!=null) holder.imageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount()
    {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView imageView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.browser_rv_image_view);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            getOnClickUri(getAdapterPosition());
        }
    }

    private Bitmap getBitmapFromMediaStore(int position)
    {
        mCursor.moveToPosition(position);
        switch(mCursor.getInt(getMediaTypeIndex())) {
            case MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE:
                return MediaStore.Images.Thumbnails.getThumbnail(
                        mActivity.getContentResolver(),
                        mCursor.getLong(getIdIndex()),
                        MediaStore.Images.Thumbnails.MICRO_KIND,
                        null
                );
            case MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO:
                return MediaStore.Video.Thumbnails.getThumbnail(
                        mActivity.getContentResolver(),
                        mCursor.getLong(getIdIndex()),
                        MediaStore.Video.Thumbnails.MICRO_KIND,
                        null
                );
            default:
                return null;
        }
    }

    private void getOnClickUri(int position)
    {
        mCursor.moveToPosition(position);
        String dataString = mCursor.getString(getDataIndex());
        String authorities = mActivity.getPackageName() + ".fileprovider";
        Uri mediaUri = FileProvider.getUriForFile(mActivity, authorities, new File(dataString));
        switch(mCursor.getInt(getMediaTypeIndex())) {
            case MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE:
                mOnClickThumbListener.OnClickImage(mediaUri);
                break;
            case MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO:
                mOnClickThumbListener.OnClickVideo(mediaUri);
                break;
            default:
        }
    }

    private int getMediaTypeIndex()
    {
        return mCursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE);
    }

    private int getDataIndex()
    {
        return mCursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
    }

    private int getIdIndex()
    {
        return mCursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
    }
}
