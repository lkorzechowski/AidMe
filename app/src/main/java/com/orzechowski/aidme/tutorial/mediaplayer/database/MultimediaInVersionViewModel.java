package com.orzechowski.aidme.tutorial.mediaplayer.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MultimediaInVersionViewModel extends AndroidViewModel
{
    private final MultimediaInVersionRepository mRepository;

    public MultimediaInVersionViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new MultimediaInVersionRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public void insert(MultimediaInVersion multimediaInVersion)
    {
        mRepository.insert(multimediaInVersion);
    }

    public void delete(MultimediaInVersion multimediaInVersion)
    {
        mRepository.delete(multimediaInVersion);
    }

    public void update(MultimediaInVersion multimediaInVersion)
    {
        mRepository.update(multimediaInVersion);
    }

    public LiveData<List<Long>> getByVersionId(long versionId)
    {
        return mRepository.getByVersionId(versionId);
    }
}
