package com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

public class MultimediaRepository
{
    private final MultimediaDAO mDao;

    MultimediaRepository(Application application)
    {
        GlobalRoomDatabase globalRoomDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalRoomDatabase.multimediaDAO();
    }

    void deleteAll()
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    void insert(Multimedia multimedia)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.insert(multimedia));
    }

    void delete(Multimedia multimedia)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.delete(multimedia));
    }

    void update(Multimedia multimedia)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.update(multimedia));
    }

    LiveData<Multimedia> getByMediaIdAndTutorialId(long mediaId, long tutorialId)
    {
        return mDao.getByMediaIdAndTutorialId(mediaId, tutorialId);
    }
}
