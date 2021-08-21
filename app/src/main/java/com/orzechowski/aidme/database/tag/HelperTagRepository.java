package com.orzechowski.aidme.database.tag;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class HelperTagRepository
{
    private final HelperTagDAO mDao;

    public HelperTagRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.helperTagDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    public void insert(HelperTag helperTag)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.insert(helperTag));
    }

    public void delete(HelperTag helperTag)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.delete(helperTag));
    }

    public void update(HelperTag helperTag)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.update(helperTag));
    }

    public LiveData<List<HelperTag>> getByHelperId(long helperId)
    {
        return mDao.getByHelperId(helperId);
    }

    public LiveData<List<HelperTag>> getByTagId(long tagId)
    {
        return mDao.getByTagId(tagId);
    }
}
