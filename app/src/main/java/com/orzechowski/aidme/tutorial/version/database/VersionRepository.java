package com.orzechowski.aidme.tutorial.version.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class VersionRepository
{
    private final VersionDAO mDao;

    public VersionRepository(Application application)
    {
        GlobalRoomDatabase database = GlobalRoomDatabase.getDatabase(application);
        mDao = database.versionDao();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    public void insert(Version version)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.insert(version));
    }

    public void delete(Version version)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.delete(version));
    }

    public void update(Version version)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.update(version));
    }

    public LiveData<List<Version>> getByTutorialId(long tutorialId)
    {
        return mDao.getByTutorialId(tutorialId);
    }

    public LiveData<List<Version>> getByParentVersionId(long parentId)
    {
        return mDao.getByParentVersionId(parentId);
    }
}
