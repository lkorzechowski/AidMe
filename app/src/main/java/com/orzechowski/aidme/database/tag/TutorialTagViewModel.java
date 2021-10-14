package com.orzechowski.aidme.database.tag;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TutorialTagViewModel extends AndroidViewModel
{
    private final TutorialTagRepository mRepository;

    public TutorialTagViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new TutorialTagRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public LiveData<List<TutorialTag>> getAll()
    {
        return mRepository.getAll();
    }

    public void insert(TutorialTag tutorialTag)
    {
        mRepository.insert(tutorialTag);
    }

    public void delete(TutorialTag tutorialTag)
    {
        mRepository.delete(tutorialTag);
    }

    public void update(TutorialTag tutorialTag)
    {
        mRepository.update(tutorialTag);
    }

    public LiveData<List<TutorialTag>> getByTutorialId(long tutorialId)
    {
        return mRepository.getByTutorialId(tutorialId);
    }

    public LiveData<List<TutorialTag>> getByTagId(long tagId)
    {
        return mRepository.getByTagId(tagId);
    }
}
