package com.orzechowski.saveme.tutorial.mediaplayer.multimedia.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

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
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    void insert(Multimedia multimedia)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(multimedia));
    }

    void delete(Multimedia multimedia)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(multimedia));
    }

    void update(Multimedia multimedia)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(multimedia));
    }

    LiveData<List<Multimedia>> getByTutorialId(long tutorialId)
    {
        return mDao.getByTutorialId(tutorialId);
    }

    LiveData<Multimedia> getByMultimediaId(long multimediaId)
    {
        return mDao.getByMultimediaId(multimediaId);
    }
}
