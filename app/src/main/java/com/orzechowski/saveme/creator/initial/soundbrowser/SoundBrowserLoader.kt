package com.orzechowski.saveme.creator.initial.soundbrowser

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

class SoundBrowserLoader(val mCallback: ActivityCallback) : Fragment(),
        SoundBrowserAdapter.FragmentCallback
{
    private lateinit var mSoundBrowserAdapter: SoundBrowserAdapter
    private val mContentObserver: ContentObserver = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean) {
            setAdapterSounds()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?):
            View
    {
        val view = inflater.inflate(R.layout.fragment_sound_browser, container, false)
        val soundRecycler: RecyclerView = view.findViewById(R.id.new_sound_picker_rv)
        soundRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,
                false)
        mSoundBrowserAdapter = SoundBrowserAdapter(requireActivity(), this)
        soundRecycler.adapter = mSoundBrowserAdapter
        requireActivity().contentResolver.registerContentObserver(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, true, mContentObserver
        )
        setAdapterSounds()
        return view
    }

    private fun setAdapterSounds()
    {
        mSoundBrowserAdapter.setElementList(loadSounds())
    }

    private fun loadSounds() : List<Sound>
    {
        val sounds = mutableListOf<Sound>()
        val uri = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        requireActivity().contentResolver.query(uri,
                arrayOf(MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME),
                null, null, null
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val idName = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            while(cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(idName)
                sounds.add(Sound(name, ContentUris
                        .withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)))
            }
            return sounds.toList()
        } ?: return listOf()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        requireActivity().contentResolver.unregisterContentObserver(mContentObserver)
    }

    override fun soundClick(sound: Sound)
    {
        mCallback.soundSubmitted(sound)
    }

    interface ActivityCallback
    {
        fun soundSubmitted(sound: Sound)
    }
}
