package com.orzechowski.aidme.browser.search.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class TagKeywordRepository
{
    private final TagKeywordDAO mDao;

    public TagKeywordRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.tagKeywordDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public LiveData<List<TagKeyword>> getAll()
    {
        return mDao.getAll();
    }

    public void insert(TagKeyword tagKeyword)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(tagKeyword));
    }

    public void delete(TagKeyword tagKeyword)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(tagKeyword));
    }

    public void update(TagKeyword tagKeyword)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(tagKeyword));
    }

    public LiveData<List<TagKeyword>> getByKeywordId(long keywordId)
    {
        return mDao.getByKeywordId(keywordId);
    }
}
