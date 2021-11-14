package com.orzechowski.saveme.tutorial.sound.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class TutorialSoundRepository
{
    private final TutorialSoundDAO mDao;

    TutorialSoundRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.tutorialSoundDAO();
    }

    void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    void delete(TutorialSound tutorialSound)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(tutorialSound));
    }

    void update(TutorialSound tutorialSound)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(tutorialSound));
    }

    void insert(TutorialSound tutorialSound)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(tutorialSound));
    }

    LiveData<List<TutorialSound>> getAll()
    {
        return mDao.getAll();
    }

    LiveData<List<TutorialSound>> getByTutorialId(long tutorialId)
    {
        return mDao.getByTutorialId(tutorialId);
    }

    LiveData<TutorialSound> getByTutorialSoundId(long tutorialSoundId)
    {
        return mDao.getByTutorialSoundId(tutorialSoundId);
    }
}
