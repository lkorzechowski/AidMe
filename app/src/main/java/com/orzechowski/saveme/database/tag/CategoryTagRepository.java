package com.orzechowski.saveme.database.tag;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class CategoryTagRepository
{
    private final CategoryTagDAO mDao;

    public CategoryTagRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.categoryTagDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public LiveData<List<CategoryTag>> getAll()
    {
        return mDao.getAll();
    }

    public void insert(CategoryTag categoryTag)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(categoryTag));
    }

    public void delete(CategoryTag categoryTag)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(categoryTag));
    }

    public void update(CategoryTag categoryTag)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(categoryTag));
    }

    public LiveData<List<CategoryTag>> getByCategoryId(long categoryId)
    {
        return mDao.getByCategoryId(categoryId);
    }

    public LiveData<List<CategoryTag>> getByTagId(long tagId)
    {
        return mDao.getByTagId(tagId);
    }

    public LiveData<CategoryTag> getByCategoryTagId(long categoryTagId)
    {
        return mDao.getByCategoryTagId(categoryTagId);
    }
}
