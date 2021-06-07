package com.orzechowski.prototyp.tutorial.sound;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class TutorialSoundViewModel extends AndroidViewModel {
    private final TutorialSoundRepository mRepository;

    public TutorialSoundViewModel(@NonNull Application application){
        super(application);
        mRepository = new TutorialSoundRepository(application);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void insert(TutorialSound tutorialSound){
        mRepository.insert(tutorialSound);
    }

    public void delete(TutorialSound tutorialSound){
        mRepository.delete(tutorialSound);
    }

    public void update(TutorialSound tutorialSound){
        mRepository.update(tutorialSound);
    }

    public List<TutorialSound> getByTutorialId(Long tutorialId){
        return mRepository.getByTutorialId(tutorialId);
    }
}
