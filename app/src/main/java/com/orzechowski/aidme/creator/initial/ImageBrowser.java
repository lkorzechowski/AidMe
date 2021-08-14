package com.orzechowski.aidme.creator.initial;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.orzechowski.aidme.R;

public class ImageBrowser extends Fragment
{
    private final Cursor mCursor;

    public ImageBrowser(Cursor cursor)
    {
        mCursor = cursor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.fragment_image_browser, container, false);
        GridView gridView = view.findViewById(R.id.image_grid);
        gridView.setAdapter(new ImageAdapter(requireContext(), mCursor));
        return view;
    }

    private static class ImageAdapter extends BaseAdapter
    {
        private final Context context;
        private final Cursor cursor;

        public ImageAdapter(Context requestContext, Cursor requestCursor)
        {
            context = requestContext;
            cursor = requestCursor;
        }

        @Override
        public int getCount()
        {
            return cursor.getCount();
        }

        @Override
        public Object getItem(int position)
        {
            return cursor.getBlob(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView = new ImageView(context);
            cursor.moveToPosition(position);
            Bitmap bitmap = BitmapFactory.decodeFile(cursor.getString(position));
            imageView.setImageBitmap(bitmap);
            imageView.setLayoutParams(new AbsListView.LayoutParams(300, 300));
            return imageView;
        }
    }
}
