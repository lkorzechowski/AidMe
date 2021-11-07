package com.orzechowski.saveme.tutorial.version.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class VersionInstructionViewModel extends AndroidViewModel
{
    private final VersionInstructionRepository mRepository;

    public VersionInstructionViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new VersionInstructionRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public void insert(VersionInstruction versionInstruction)
    {
        mRepository.insert(versionInstruction);
    }

    public void delete(VersionInstruction versionInstruction)
    {
        mRepository.delete(versionInstruction);
    }

    public void update(VersionInstruction versionInstruction)
    {
        mRepository.update(versionInstruction);
    }

    public LiveData<List<Integer>> getByVersionId(long versionId)
    {
        return mRepository.getByVersionId(versionId);
    }

    public LiveData<VersionInstruction> getByVersionInstructionId(long versionInstructionId)
    {
        return mRepository.getByVersionInstructionId(versionInstructionId);
    }
}
