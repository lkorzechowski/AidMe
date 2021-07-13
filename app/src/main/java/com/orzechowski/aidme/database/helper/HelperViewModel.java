package com.orzechowski.aidme.database.helper;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HelperViewModel extends AndroidViewModel
{
    private final HelperRepository mRepository;

    public HelperViewModel(@NonNull @NotNull Application application)
    {
        super(application);
        mRepository = new HelperRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public void insert(Helper helper)
    {
        mRepository.insert(helper);
    }

    public void delete(Helper helper)
    {
        mRepository.delete(helper);
    }

    public void update(Helper helper)
    {
        mRepository.update(helper);
    }

    public LiveData<List<Helper>> getAll()
    {
        return mRepository.getAll();
    }

    public LiveData<List<Helper>> getByTags(String tags)
    {
        return mRepository.getByTags(tags);
    }
}
