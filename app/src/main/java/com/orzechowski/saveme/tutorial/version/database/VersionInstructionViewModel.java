package com.orzechowski.saveme.tutorial.version.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class VersionInstructionViewModel extends AndroidViewModel
{
    private final VersionInstructionDAO mDao;

    public VersionInstructionViewModel(@NonNull Application application)
    {
        super(application);
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

    public LiveData<VersionInstruction> getByVersionInstructionId(long versionInstructionId)
    {
        return mDao.getByVersionInstructionId(versionInstructionId);
    }
}
