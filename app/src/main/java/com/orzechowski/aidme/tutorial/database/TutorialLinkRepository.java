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
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    void insert(TutorialLink tutorialLink)
    {
        GlobalRoomDatabase.executor.execute(()-> mDao.insert(tutorialLink));
    }

    void delete(TutorialLink tutorialLink)
    {
        GlobalRoomDatabase.executor.execute(()-> mDao.delete(tutorialLink));
    }

    void update(TutorialLink tutorialLink)
    {
        GlobalRoomDatabase.executor.execute(()-> mDao.update(tutorialLink));
    }

    LiveData<TutorialLink> getByOriginIdAndPosition(long tutorialId, int instructionNumber)
    {
        return mDao.getByOriginIdAndPosition(tutorialId, instructionNumber);
    }
}
