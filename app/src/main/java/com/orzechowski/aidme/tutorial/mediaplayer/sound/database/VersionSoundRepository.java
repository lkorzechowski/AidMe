package com.orzechowski.aidme.tutorial.mediaplayer.sound.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class VersionSoundRepository
{
    private final VersionSoundDAO mDao;

    VersionSoundRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.soundInVersionDAO();
    }

    void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    void insert(VersionSound versionSound)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(versionSound));
    }

    void delete(VersionSound versionSound)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(versionSound));
    }

    void update(VersionSound versionSound)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(versionSound));
    }

    LiveData<List<Long>> getByVersionId(long versionId)
    {
        return mDao.getByVersionId(versionId);
    }

    LiveData<VersionSound> getByVersionSoundId(long versionSoundId)
    {
        return mDao.getByVersionSoundId(versionSoundId);
    }
}
