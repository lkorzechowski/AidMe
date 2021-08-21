package com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class VersionMultimediaRepository
{
    private final VersionMultimediaDAO mDao;

    VersionMultimediaRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.versionMultimediaDAO();
    }

    void deleteAll()
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    void insert(VersionMultimedia versionMultimedia)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.insert(versionMultimedia));
    }

    void delete(VersionMultimedia versionMultimedia)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.delete(versionMultimedia));
    }

    void update(VersionMultimedia versionMultimedia)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.update(versionMultimedia));
    }

    LiveData<List<Long>> getByVersionId(long versionId)
    {
        return mDao.getByVersionId(versionId);
    }
}
