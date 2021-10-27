package com.orzechowski.aidme.browser.categories.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel
{
    private final CategoryRepository mRepository;

    public CategoryViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new CategoryRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public LiveData<List<Category>> getAll()
    {
        return mRepository.getAll();
    }

    public void insert(Category category)
    {
        mRepository.insert(category);
    }

    public void delete(Category category)
    {
        mRepository.delete(category);
    }

    public void update(Category category)
    {
        mRepository.update(category);
    }

    public LiveData<List<Category>> getByLevel(int level)
    {
        return mRepository.getByLevel(level);
    }

    public LiveData<Category> getByCategoryId(long categoryId)
    {
        return mRepository.getByCategoryId(categoryId);
    }
}
