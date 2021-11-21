package com.orzechowski.saveme.imagebrowser

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.orzechowski.saveme.R

class ImageBrowserLoader(val mCallback: ActivityCallback) : Fragment(),
    ImageBrowserAdapter.FragmentCallback
{
    private lateinit var mImageBrowserAdapter: ImageBrowserAdapter
    private lateinit var mContentObserver: ContentObserver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?):
            View
    {
        val activity: FragmentActivity = requireActivity()
        val view = inflater.inflate(R.layout.fragment_image_browser, container, false)
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission
                .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 121)
        } else {
            conditionalSetup(view, activity)
            setAdapterImages()
        }
        return view
    }

    private fun conditionalSetup(view: View, activity: FragmentActivity)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mContentObserver = object: ContentObserver(Handler.createAsync(activity.mainLooper)) {
                override fun onChange(selfChange: Boolean) {
                    activity.runOnUiThread {
                        setAdapterImages()
                    }
                }
            }
        } else {
            mContentObserver = object: ContentObserver(null) {
                override fun onChange(selfChange: Boolean) {
                    activity.runOnUiThread {
                        setAdapterImages()
                    }
                }
            }
        }
        val imageRecycler: RecyclerView = view.findViewById(R.id.image_recycler_grid)
        imageRecycler.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
        mImageBrowserAdapter = ImageBrowserAdapter(activity, this)
        imageRecycler.adapter = mImageBrowserAdapter
        activity.contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, mContentObserver
        )
    }

    override fun onResume()
    {
        setAdapterImages()
        super.onResume()
    }

    private fun setAdapterImages()
    {
        mImageBrowserAdapter.setElementList(loadImages())
    }

    private fun loadImages(): List<Uri>
    {
        val uris = mutableListOf<Uri>()
        val uri = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        requireActivity().contentResolver.query(uri, arrayOf(MediaStore.Images.Media._ID),
            null, null, null
        )!!.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            while(cursor.moveToNext()) {
                val id = cursor.getInt(idColumn)
                uris.add(ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id.toLong()))
            }
            return uris
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        requireActivity().contentResolver.unregisterContentObserver(mContentObserver)
    }

    override fun imageClick(uri: Uri)
    {
        mCallback.imageSubmitted(uri)
    }

    interface ActivityCallback
    {
        fun imageSubmitted(uri: Uri)
        fun refreshGallery()
    }
}
