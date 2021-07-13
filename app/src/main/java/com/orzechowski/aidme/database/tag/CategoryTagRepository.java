package com.orzechowski.aidme.database.tag;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

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
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    public void insert(CategoryTag categoryTag)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.insert(categoryTag));
    }

    public void delete(CategoryTag categoryTag)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.delete(categoryTag));
    }

    public void update(CategoryTag categoryTag)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.update(categoryTag));
    }

    public LiveData<CategoryTag> getByCategoryId(long categoryId)
    {
        return mDao.getByCategoryId(categoryId);
    }

    public LiveData<CategoryTag> getByTagId(long tagId)
    {
        return mDao.getByTagId(tagId);
    }
}