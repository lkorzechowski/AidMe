package com.orzechowski.aidme.database.tag;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

public class TagRepository
{
    private final TagDAO mDao;

    public TagRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.tagDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteALl);
    }

    public void insert(Tag tag)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(tag));
    }

    public void delete(Tag tag)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(tag));
    }

    public void update(Tag tag)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(tag));
    }

    public LiveData<Tag> getById(long tagId)
    {
        return mDao.getById(tagId);
    }
}
