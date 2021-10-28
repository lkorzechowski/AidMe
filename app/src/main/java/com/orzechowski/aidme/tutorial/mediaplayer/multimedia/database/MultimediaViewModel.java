package com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MultimediaViewModel extends AndroidViewModel
{
    private final MultimediaRepository mRepository;

    public MultimediaViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new MultimediaRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public void insert(Multimedia multimedia)
    {
        mRepository.insert(multimedia);
    }

    public void delete(Multimedia multimedia)
    {
        mRepository.delete(multimedia);
    }

    public void update(Multimedia multimedia)
    {
        mRepository.update(multimedia);
    }

    public LiveData<List<Multimedia>> getByTutorialId(long tutorialId)
    {
        return mRepository.getByTutorialId(tutorialId);
    }

    public LiveData<Multimedia> getByMultimediaId(long multimediaId)
    {
        return mRepository.getByMultimediaId(multimediaId);
    }
}
