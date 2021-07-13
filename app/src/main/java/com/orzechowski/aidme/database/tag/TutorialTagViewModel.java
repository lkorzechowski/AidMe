package com.orzechowski.aidme.database.tag;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

public class TutorialTagViewModel extends AndroidViewModel
{
    private final TutorialTagRepository mRepository;

    public TutorialTagViewModel(@NonNull @NotNull Application application)
    {
        super(application);
        mRepository = new TutorialTagRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
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

    public LiveData<TutorialTag> getByTutorialId(long tutorialId)
    {
        return mRepository.getByTutorialId(tutorialId);
    }

    public LiveData<TutorialTag> getByTagId(long tagId)
    {
        return mRepository.getByTagId(tagId);
    }
}
