package com.orzechowski.saveme.tutorial.version.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class VersionViewModel extends AndroidViewModel
{

    private final VersionRepository mRepository;

    public VersionViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new VersionRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public void insert(Version version)
    {
        mRepository.insert(version);
    }

    public void delete(Version version)
    {
        mRepository.delete(version);
    }

    public void update(Version version)
    {
        mRepository.update(version);
    }

    public LiveData<List<Version>> getBaseByTutorialId(long tutorialId)
    {
        return mRepository.getBaseByTutorialId(tutorialId);
    }

    public LiveData<List<Version>> getByParentVersionId(long parentId)
    {
        return mRepository.getByParentVersionId(parentId);
    }

    public LiveData<Version> getByVersionId(long versionId)
    {
        return mRepository.getByVersionId(versionId);
    }
}
