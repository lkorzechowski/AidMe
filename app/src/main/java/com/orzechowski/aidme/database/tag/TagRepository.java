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
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteALl);
    }

    public void insert(Tag tag)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.insert(tag));
    }

    public void delete(Tag tag)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.delete(tag));
    }

    public void update(Tag tag)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.update(tag));
    }

    public LiveData<Tag> getById(long tagId)
    {
        return mDao.getById(tagId);
    }
}
