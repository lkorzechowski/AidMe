package com.orzechowski.aidme.database.tag;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

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
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    public void insert(TutorialTag tutorialTag)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.insert(tutorialTag));
    }

    public void delete(TutorialTag tutorialTag)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.delete(tutorialTag));
    }

    public void update(TutorialTag tutorialTag)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.update(tutorialTag));
    }

    public LiveData<TutorialTag> getByTutorialId(long tutorialId)
    {
        return mDao.getByTutorialId(tutorialId);
    }

    public LiveData<TutorialTag> getByTagId(long tagId)
    {
        return mDao.getByTagId(tagId);
    }
}
