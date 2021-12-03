package com.orzechowski.saveme.browser.search.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class TagKeywordViewModel extends AndroidViewModel
{
    private final TagKeywordDAO mDao;

    public TagKeywordViewModel(@NonNull Application application)
    {
        super(application);
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

    public LiveData<TagKeyword> getByTagKeywordId(long tagKeywordId)
    {
        return mDao.getByTagKeywordId(tagKeywordId);
    }
}
