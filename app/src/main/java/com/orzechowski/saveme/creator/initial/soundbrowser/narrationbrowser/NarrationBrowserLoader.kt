package com.orzechowski.saveme.creator.initial.soundbrowser.narrationbrowser

import android.content.ContentUris
import android.database.ContentObserver
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orzechowski.saveme.R
import com.orzechowski.saveme.creator.initial.soundbrowser.Sound
import java.util.concurrent.TimeUnit

class NarrationBrowserLoader(val mCallback: ActivityCallback) : Fragment(),
    NarrationBrowserAdapter.FragmentCallback
{
    private lateinit var mNarrationbrowserAdapter: NarrationBrowserAdapter
    private val mContentObserver: ContentObserver = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean) {
            setAdapterNarrations()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?):
            View
    {
        val view = inflater.inflate(R.layout.fragment_narration_browser, container,
            false)
        val narrationRecycler: RecyclerView = view.findViewById(R.id.new_narration_picker_rv)
        narrationRecycler.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL, false)
        mNarrationbrowserAdapter = NarrationBrowserAdapter(requireActivity(), this)
        narrationRecycler.adapter = mNarrationbrowserAdapter
        requireActivity().contentResolver.registerContentObserver(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, true, mContentObserver
        )
        setAdapterNarrations()
        return view
    }

    private fun setAdapterNarrations()
    {
        mNarrationbrowserAdapter.setElementList(loadNarrations())
    }

    private fun loadNarrations() : List<Sound>
    {
        val narrations = mutableListOf<Sound>()
        val uri = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        requireActivity().contentResolver.query(uri,
            arrayOf(MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION),
            "${MediaStore.Audio.Media.DURATION} >= ?"
            , arrayOf(
                TimeUnit.MILLISECONDS.convert(20, TimeUnit.SECONDS).toString())
                , null
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            while(cursor.moveToNext()) {
                narrations.add(Sound("", ContentUris
                    .withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        cursor.getLong(idColumn))))
            }
            return narrations.toList()
        } ?: return listOf()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        requireActivity().contentResolver.unregisterContentObserver(mContentObserver)
    }

    override fun narrationClick(narration: Sound)
    {
        mCallback.narrationSubmitted(narration)
    }

    interface ActivityCallback
    {
        fun narrationSubmitted(narration: Sound)
    }
}
