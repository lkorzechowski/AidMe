package com.orzechowski.aidme.tutorial.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class InstructionSetRepository
{
    private final InstructionSetDAO mDao;

    public InstructionSetRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.instructionDao();
    }
    public void deleteAll()
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    public void insert(InstructionSet instructionSet)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.insert(instructionSet));
    }

    public void delete(InstructionSet instructionSet)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.delete(instructionSet));
    }

    public void update(InstructionSet instructionSet)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.update(instructionSet));
    }

    public LiveData<List<InstructionSet>> getByTutorialId(long tutorialId)
    {
        return mDao.getByTutorialId(tutorialId);
    }

    public LiveData<List<InstructionSet>> getByPositionAndTutorialId(int position, long tutorialId)
    {
        return mDao.getByPositionAndTutorialId(position, tutorialId);
    }
}
