package com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class MultimediaInVersionRepository
{
    private final MultimediaInVersionDAO mDao;

    MultimediaInVersionRepository(Application application)
    {
        GlobalRoomDatabase globalRoomDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalRoomDatabase.multimediaInVersionDAO();
    }

    void deleteAll()
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    void insert(MultimediaInVersion multimediaInVersion)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.insert(multimediaInVersion));
    }

    void delete(MultimediaInVersion multimediaInVersion)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.delete(multimediaInVersion));
    }

    void update(MultimediaInVersion multimediaInVersion)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.update(multimediaInVersion));
    }

    LiveData<List<Long>> getByVersionId(long versionId)
    {
        return mDao.getByVersionId(versionId);
    }
}
