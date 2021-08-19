package com.orzechowski.aidme.creator.initial.imagebrowser;

import android.app.Activity;
import android.content.ContentUris;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.orzechowski.aidme.R;

import java.util.LinkedList;
import java.util.List;

public class ImageBrowserLoader extends Fragment
{
    private ImageBrowserAdapter mImageBrowserAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        Activity activity = requireActivity();
        View view = inflater.inflate(R.layout.fragment_image_browser, container, false);
        RecyclerView imageRecycler = view.findViewById(R.id.image_recycler_grid);
        imageRecycler.setLayoutManager(
                new StaggeredGridLayoutManager(3, RecyclerView.VERTICAL));
        mImageBrowserAdapter = new ImageBrowserAdapter();
        imageRecycler.setAdapter(mImageBrowserAdapter);
        ContentObserver contentObserver = new ContentObserver(null) {
            @Override
            public void onChange(boolean selfChange) {
                loadPhotos();
            }
        };
        activity.getContentResolver().registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, contentObserver
        );
        return view;
    }

    public void loadPhotos()
    {
        Uri uri;
        if(Build.VERSION.SDK_INT >= 29) uri =
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        else uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = requireActivity().getContentResolver().query(
                uri,
                new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.WIDTH, MediaStore.Images.Media.HEIGHT},
                null,
                null,
                "${MediaStore.Images.Media.DISPLAY_NAME} ASC");
        List<Image> images = new LinkedList<>();
        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
            images.add(new Image(id,
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media
                            .DISPLAY_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)),
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)));
        }
        mImageBrowserAdapter.setElementList(images);
        cursor.close();
    }
}
