package com.orzechowski.prototyp.database.tutorial;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TutorialViewModel extends AndroidViewModel {

    private final TutorialRepository mRepository;
    private final LiveData<List<Tutorial>> mTutorials;

    public TutorialViewModel(@NonNull Application application){
        super(application);
        mRepository = new TutorialRepository(application);
        mTutorials = mRepository.getAll();
    }

    public LiveData<List<Tutorial>> getAll(){
        return mTutorials;
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void insert(Tutorial tutorial){
        mRepository.insert(tutorial);
    }

    public void delete(Tutorial tutorial){
        mRepository.delete(tutorial);
    }

    public void update(Tutorial tutorial){
        mRepository.update(tutorial);
    }
}
