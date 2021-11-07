package com.orzechowski.saveme.tutorial.instructions.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class InstructionSetViewModel extends AndroidViewModel
{
    private final InstructionSetRepository mRepository;

    public InstructionSetViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new InstructionSetRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public void insert(InstructionSet instructionSet)
    {
        mRepository.insert(instructionSet);
    }

    public void delete(InstructionSet instructionSet)
    {
        mRepository.insert(instructionSet);
    }

    public void update(InstructionSet instructionSet)
    {
        mRepository.update(instructionSet);
    }

    public LiveData<List<InstructionSet>> getByTutorialId(long tutorialId)
    {
        return mRepository.getByTutorialId(tutorialId);
    }

    public LiveData<InstructionSet> getByPositionAndTutorialId(int position, long tutorialId)
    {
        return mRepository.getByPositionAndTutorialId(position, tutorialId);
    }

    public LiveData<Integer> getTutorialSize(long tutorialId)
    {
        return mRepository.getTutorialSize(tutorialId);
    }

    public LiveData<List<InstructionSet>> getAll()
    {
        return mRepository.getAll();
    }

    public LiveData<InstructionSet> getByInstructionSetId(long instructionSetId)
    {
        return mRepository.getByInstructionSetId(instructionSetId);
    }
}
