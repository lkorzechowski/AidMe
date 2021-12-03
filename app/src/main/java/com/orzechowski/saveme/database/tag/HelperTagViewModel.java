package com.orzechowski.saveme.database.tag;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class HelperTagViewModel extends AndroidViewModel
{
    private final HelperTagDAO mDao;

    public HelperTagViewModel(@NonNull Application application)
    {
        super(application);
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.helperTagDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public LiveData<List<HelperTag>> getAll()
    {
        return mDao.getAll();
    }

    public void insert(HelperTag helperTag)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(helperTag));
    }

    public void delete(HelperTag helperTag)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(helperTag));
    }

    public void update(HelperTag helperTag)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(helperTag));
    }

    public LiveData<List<HelperTag>> getByTagId(long tagId)
    {
        return mDao.getByTagId(tagId);
    }

    public LiveData<HelperTag> getByHelperTagId(long helperTagId)
    {
        return mDao.getByHelperTagId(helperTagId);
    }
}
