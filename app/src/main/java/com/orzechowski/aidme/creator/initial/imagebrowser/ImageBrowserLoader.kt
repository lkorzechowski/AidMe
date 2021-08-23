package com.orzechowski.aidme.creator.initial.imagebrowser

import android.content.ContentUris
import android.database.ContentObserver
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.orzechowski.aidme.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageBrowserLoader(val mCallback: ActivityCallback) : Fragment(),
    ImageBrowserAdapter.FragmentCallback
{
    private var mImageBrowserAdapter = ImageBrowserAdapter(this)
    private val mContentObserver: ContentObserver = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean) {
            setAdapterImages()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?):
            View
    {
        val view = inflater.inflate(R.layout.fragment_image_browser, container, false)
        val imageRecycler: RecyclerView = view.findViewById(R.id.image_recycler_grid)
        imageRecycler.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
        imageRecycler.adapter = mImageBrowserAdapter
        requireActivity().contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, mContentObserver
        )
        setAdapterImages()
        return view
    }

    private fun setAdapterImages()
    {
        lifecycleScope.launch {
            mImageBrowserAdapter.setElementList(loadImages())
        }
    }

    private suspend fun loadImages(): List<Image>
    {
        return withContext(Dispatchers.IO) {
            val uri = if(Build.VERSION.SDK_INT >= 29) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            requireActivity().contentResolver.query(uri, arrayOf(MediaStore.Images.Media._ID),
                null, null, "${MediaStore.Images.Media.DATE_ADDED} ASC"
            )?.use { cursor ->
                val photos = mutableListOf<Image>()
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while(cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    photos.add(Image(id, contentUri))
                }
                photos
            } ?: listOf()
        }
    }

    override fun imageClick(image: Image)
    {
        mCallback.imageSubmitted(image)
    }

    interface ActivityCallback
    {
        fun imageSubmitted(image: Image)
    }
}
