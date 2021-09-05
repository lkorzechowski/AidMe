package com.orzechowski.aidme.database.tag;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class TutorialTagRepository
{
    private final TutorialTagDAO mDao;

    public TutorialTagRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.tutorialTagDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public void insert(TutorialTag tutorialTag)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(tutorialTag));
    }

    public void delete(TutorialTag tutorialTag)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(tutorialTag));
    }

    public void update(TutorialTag tutorialTag)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(tutorialTag));
    }

    public LiveData<List<TutorialTag>> getByTutorialId(long tutorialId)
    {
        return mDao.getByTutorialId(tutorialId);
    }

    public LiveData<List<TutorialTag>> getByTagId(long tagId)
    {
        return mDao.getByTagId(tagId);
    }
}
