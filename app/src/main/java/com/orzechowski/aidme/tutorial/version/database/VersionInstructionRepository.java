package com.orzechowski.aidme.tutorial.version.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class VersionInstructionRepository
{
    private  final VersionInstructionDAO mDao;

    public VersionInstructionRepository(Application application)
    {
        GlobalRoomDatabase database = GlobalRoomDatabase.getDatabase(application);
        mDao = database.versionInstructionDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public void insert(VersionInstruction versionInstruction)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(versionInstruction));
    }

    public void delete(VersionInstruction versionInstruction)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(versionInstruction));
    }

    public void update(VersionInstruction versionInstruction)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(versionInstruction));
    }

    public LiveData<List<Integer>> getByVersionId(long versionId)
    {
        return mDao.getByVersionId(versionId);
    }
}
