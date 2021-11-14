package com.orzechowski.saveme.tutorial.sound.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class VersionSoundViewModel extends AndroidViewModel
{
    private final VersionSoundRepository mRepository;

    public VersionSoundViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new VersionSoundRepository(application);
    }

    public void insert(VersionSound versionSound)
    {
        mRepository.insert(versionSound);
    }

    public void delete(VersionSound versionSound)
    {
        mRepository.delete(versionSound);
    }

    public void update(VersionSound versionSound)
    {
        mRepository.update(versionSound);
    }

    public LiveData<List<Long>> getByVersionId(long versionId)
    {
        return mRepository.getByVersionId(versionId);
    }

    public LiveData<VersionSound> getByVersionSoundId(long versionSoundId)
    {
        return mRepository.getByVersionSoundId(versionSoundId);
    }
}
