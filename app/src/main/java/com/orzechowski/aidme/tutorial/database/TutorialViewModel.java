package com.orzechowski.aidme.tutorial.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TutorialViewModel extends AndroidViewModel
{
    private final TutorialRepository mRepository;

    public TutorialViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new TutorialRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public void insert(Tutorial tutorial)
    {
        mRepository.insert(tutorial);
    }

    public void delete(Tutorial tutorial)
    {
        mRepository.delete(tutorial);
    }

    public void update(Tutorial tutorial)
    {
        mRepository.update(tutorial);
    }

    public LiveData<Tutorial> getByTutorialId(Long tutorialId)
    {
        return mRepository.getByTutorialId(tutorialId);
    }

    public LiveData<List<Tutorial>> getByTag(String tag)
    {
        return mRepository.getByTag(tag);
    }
}
