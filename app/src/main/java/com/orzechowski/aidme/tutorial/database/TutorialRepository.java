package com.orzechowski.aidme.tutorial.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class TutorialRepository
{
    private final TutorialDAO mDao;

    TutorialRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.tutorialDao();
    }

    void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    void insert(Tutorial tutorial)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(tutorial));
    }

    void delete(Tutorial tutorial)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(tutorial));
    }

    void update(Tutorial tutorial)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(tutorial));
    }

    LiveData<Tutorial> getByTutorialId(long tutorialId)
    {
        return mDao.getByTutorialId(tutorialId);
    }

    LiveData<List<Tutorial>> getAll()
    {
        return mDao.getAll();
    }
}
