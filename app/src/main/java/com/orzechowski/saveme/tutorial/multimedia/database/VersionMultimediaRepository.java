package com.orzechowski.saveme.tutorial.multimedia.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

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
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    void insert(VersionMultimedia versionMultimedia)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(versionMultimedia));
    }

    void delete(VersionMultimedia versionMultimedia)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(versionMultimedia));
    }

    void update(VersionMultimedia versionMultimedia)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(versionMultimedia));
    }

    LiveData<List<Long>> getByVersionId(long versionId)
    {
        return mDao.getByVersionId(versionId);
    }

    LiveData<VersionMultimedia> getByVersionMultimediaId(long versionMultimediaId)
    {
        return mDao.getByVersionMultimediaId(versionMultimediaId);
    }
}
