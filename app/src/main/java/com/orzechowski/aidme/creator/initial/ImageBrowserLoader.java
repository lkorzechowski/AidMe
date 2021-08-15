package com.orzechowski.aidme.creator.initial;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;

public class ImageBrowserLoader extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, ImageBrowserAdapter.OnClickThumbListener
{
    private ImageBrowserAdapter mImageBrowserAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        Activity activity = requireActivity();
        View view = inflater.inflate(R.layout.fragment_image_browser, container, false);
        RecyclerView imageRecycler = view.findViewById(R.id.image_recycler_grid);
        imageRecycler.setLayoutManager(new GridLayoutManager(activity, 3));
        mImageBrowserAdapter = new ImageBrowserAdapter(activity, this);
        imageRecycler.setAdapter(mImageBrowserAdapter);
        return view;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args)
    {
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.MEDIA_TYPE
        };
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
        return new CursorLoader(
                requireContext(),
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                null,
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data)
    {
        mImageBrowserAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader)
    {
        mImageBrowserAdapter.changeCursor(null);
    }

    @Override
    public void OnClickImage(Uri imageUri)
    {

    }

    @Override
    public void OnClickVideo(Uri videoUri)
    {

    }
}
