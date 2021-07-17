package com.orzechowski.aidme.browser.categories.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class CategoryRepository {
    private final CategoryDAO mDao;

    public CategoryRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.categoryDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    public void insert(Category category)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(() -> mDao.insert(category));
    }

    public void delete(Category category)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.delete(category));
    }

    public void update(Category category)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.update(category));
    }

    public LiveData<List<Category>> getByLevel(int level)
    {
        return mDao.getByLevel(level);
    }
}
