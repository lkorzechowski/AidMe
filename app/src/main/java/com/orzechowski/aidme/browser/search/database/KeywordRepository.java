package com.orzechowski.aidme.browser.search.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

public class KeywordRepository
{
    private final KeywordDAO mDao;

    public KeywordRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.keywordDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    public void insert(Keyword keyword)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.insert(keyword));
    }

    public void delete(Keyword keyword)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.delete(keyword));
    }

    public void update(Keyword keyword)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()-> mDao.update(keyword));
    }

    public LiveData<Keyword> getByPartialWord(String partial)
    {
        return mDao.getByPartialWord(partial);
    }
}
