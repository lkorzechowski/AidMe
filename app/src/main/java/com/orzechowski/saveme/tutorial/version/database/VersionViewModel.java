package com.orzechowski.saveme.tutorial.version.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class VersionViewModel extends AndroidViewModel
{
    private final VersionDAO mDao;

    public VersionViewModel(@NonNull Application application)
    {
        super(application);
        GlobalRoomDatabase database = GlobalRoomDatabase.getDatabase(application);
        mDao = database.versionDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public void insert(Version version)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(version));
    }

    public void delete(Version version)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(version));
    }

    public void update(Version version)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(version));
    }

    public LiveData<List<Version>> getBaseByTutorialId(long tutorialId)
    {
        return mDao.getBaseByTutorialId(tutorialId);
    }

    public LiveData<List<Version>> getByParentVersionId(long parentId)
    {
        return mDao.getByParentVersionId(parentId);
    }

    public LiveData<Version> getByVersionId(long versionId)
    {
        return mDao.getByVersionId(versionId);
    }
}
