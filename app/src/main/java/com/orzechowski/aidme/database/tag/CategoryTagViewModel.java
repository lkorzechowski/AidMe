package com.orzechowski.aidme.database.tag;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

public class CategoryTagViewModel extends AndroidViewModel
{
    private final CategoryTagRepository mRepository;

    public CategoryTagViewModel(@NonNull @NotNull Application application)
    {
        super(application);
        mRepository = new CategoryTagRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public void insert(CategoryTag categoryTag)
    {
        mRepository.insert(categoryTag);
    }

    public void delete(CategoryTag categoryTag)
    {
        mRepository.delete(categoryTag);
    }

    public void update(CategoryTag categoryTag)
    {
        mRepository.update(categoryTag);
    }

    public LiveData<CategoryTag> getByCategoryId(long categoryId)
    {
        return mRepository.getByCategoryId(categoryId);
    }

    public LiveData<CategoryTag> getByTagId(long tagId)
    {
        return mRepository.getByTagId(tagId);
    }
}
