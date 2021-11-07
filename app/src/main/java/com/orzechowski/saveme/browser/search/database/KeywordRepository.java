package com.orzechowski.saveme.browser.search.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

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
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public void insert(Keyword keyword)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(keyword));
    }

    public void delete(Keyword keyword)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(keyword));
    }

    public void update(Keyword keyword)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(keyword));
    }

    public LiveData<List<Keyword>> getAll()
    {
        return mDao.getAll();
    }

    public LiveData<Keyword> getByKeywordId(long keywordId)
    {
        return mDao.getByKeywordId(keywordId);
    }
}
