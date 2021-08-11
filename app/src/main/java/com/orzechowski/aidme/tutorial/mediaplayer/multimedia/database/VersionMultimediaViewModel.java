package com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class VersionMultimediaViewModel extends AndroidViewModel
{
    private final VersionMultimediaRepository mRepository;

    public VersionMultimediaViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new VersionMultimediaRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public void insert(VersionMultimedia versionMultimedia)
    {
        mRepository.insert(versionMultimedia);
    }

    public void delete(VersionMultimedia versionMultimedia)
    {
        mRepository.delete(versionMultimedia);
    }

    public void update(VersionMultimedia versionMultimedia)
    {
        mRepository.update(versionMultimedia);
    }

    public LiveData<List<Long>> getByVersionId(long versionId)
    {
        return mRepository.getByVersionId(versionId);
    }
}
