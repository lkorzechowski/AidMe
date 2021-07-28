package com.orzechowski.aidme.tutorial.mediaplayer.sound.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SoundInVersionViewModel extends AndroidViewModel
{
    private final SoundInVersionRepository mRepository;

    public SoundInVersionViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new SoundInVersionRepository(application);
    }

    public void insert(SoundInVersion soundInVersion)
    {
        mRepository.insert(soundInVersion);
    }

    public void delete(SoundInVersion soundInVersion)
    {
        mRepository.delete(soundInVersion);
    }

    public void update(SoundInVersion soundInVersion)
    {
        mRepository.update(soundInVersion);
    }

    public LiveData<List<Long>> getByVersionId(long versionId)
    {
        return mRepository.getByVersionId(versionId);
    }
}
