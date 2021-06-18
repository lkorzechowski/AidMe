package com.orzechowski.aidme.tutorial.sound;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class TutorialSoundRepository {
    private final TutorialSoundDAO mDao;
    private final LiveData<List<TutorialSound>> mSounds;

    TutorialSoundRepository(Application application){
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.tutorialSoundDAO();
        mSounds = mDao.getAll();
    }

    LiveData<List<TutorialSound>> getAll(){
        return mSounds;
    }

    void deleteAll(){
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    void delete(TutorialSound tutorialSound){
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.delete(tutorialSound));
    }

    void update(TutorialSound tutorialSound){
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.update(tutorialSound));
    }

    void insert(TutorialSound tutorialSound){
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.insert(tutorialSound));
    }

    List<TutorialSound> getByTutorialId(Long tutorialId){
        return mDao.getByTutorialId(tutorialId);
    }
}
