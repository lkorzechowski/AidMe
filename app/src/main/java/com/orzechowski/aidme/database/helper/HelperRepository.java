package com.orzechowski.aidme.database.helper;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class HelperRepository
{
    private final HelperDAO mDao;
    private final LiveData<List<Helper>> mHelpers;

    public HelperRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.helperDao();
        mHelpers = mDao.getAll();
    }

    public LiveData<List<Helper>> getAll()
    {
        return mHelpers;
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    public void insert(Helper helper)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.insert(helper));
    }

    public void delete(Helper helper)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.delete(helper));
    }

    public void update(Helper helper)
    {
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.update(helper));
    }

    public LiveData<Helper> getById(long helperId)
    {
        return mDao.getById(helperId);
    }
}
