package com.orzechowski.saveme.tutorial.instructions.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class InstructionSetViewModel extends AndroidViewModel
{
    private final InstructionSetDAO mDao;

    public InstructionSetViewModel(@NonNull Application application)
    {
        super(application);
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.instructionDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public void insert(InstructionSet instructionSet)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(instructionSet));
    }

    public void delete(InstructionSet instructionSet)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(instructionSet));
    }

    public void update(InstructionSet instructionSet)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(instructionSet));
    }

    public LiveData<List<InstructionSet>> getByTutorialId(long tutorialId)
    {
        return mDao.getByTutorialId(tutorialId);
    }

    public LiveData<InstructionSet> getByPositionAndTutorialId(int position, long tutorialId)
    {
        return mDao.getByPositionAndTutorialId(position, tutorialId);
    }

    public LiveData<Integer> getTutorialSize(long tutorialId)
    {
        return mDao.getTutorialSize(tutorialId);
    }

    public LiveData<List<InstructionSet>> getAll()
    {
        return mDao.getAll();
    }

    public LiveData<InstructionSet> getByInstructionSetId(long instructionSetId)
    {
        return mDao.getByInstructionSetId(instructionSetId);
    }
}
