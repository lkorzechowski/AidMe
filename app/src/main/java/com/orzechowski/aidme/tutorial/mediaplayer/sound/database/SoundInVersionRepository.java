package com.orzechowski.aidme.tutorial.mediaplayer.sound.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class SoundInVersionRepository
{
    private final SoundInVersionDAO mDao;

    SoundInVersionRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.soundInVersionDAO();
    }

    void deleteAll()
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    void insert(SoundInVersion soundInVersion)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.insert(soundInVersion));
    }

    void delete(SoundInVersion soundInVersion)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.delete(soundInVersion));
    }

    void update(SoundInVersion soundInVersion)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.update(soundInVersion));
    }

    LiveData<List<Long>> getByVersionId(long versionId)
    {
        return mDao.getByVersionId(versionId);
    }
}
