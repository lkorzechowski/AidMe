package com.orzechowski.saveme.browser.categories.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel
{
    private final CategoryDAO mDao;

    public CategoryViewModel(@NonNull Application application)
    {
        super(application);
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.categoryDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public LiveData<List<Category>> getAll()
    {
        return mDao.getAll();
    }

    public void insert(Category category)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(category));
    }

    public void delete(Category category)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(category));
    }

    public void update(Category category)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(category));
    }

    public LiveData<List<Category>> getByLevel(int level)
    {
        return mDao.getByLevel(level);
    }

    public LiveData<Category> getByCategoryId(long categoryId)
    {
        return mDao.getByCategoryId(categoryId);
    }
}
