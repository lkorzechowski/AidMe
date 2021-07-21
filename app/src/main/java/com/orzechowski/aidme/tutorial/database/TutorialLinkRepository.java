package com.orzechowski.aidme.tutorial.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

public class TutorialLinkRepository
{
    private final TutorialLinkDAO mDao;

    TutorialLinkRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.tutorialLinkDAO();
    }

    void deleteAll()
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    void insert(TutorialLink tutorialLink)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.insert(tutorialLink));
    }

    void delete(TutorialLink tutorialLink)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.delete(tutorialLink));
    }

    void update(TutorialLink tutorialLink)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.update(tutorialLink));
    }

    LiveData<TutorialLink> getByOriginIdAndPosition(long tutorialId, int instructionNumber)
    {
        return mDao.getByOriginIdAndPosition(tutorialId, instructionNumber);
    }
}
