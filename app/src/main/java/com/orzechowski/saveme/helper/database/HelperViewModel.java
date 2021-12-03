package com.orzechowski.saveme.helper.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class HelperViewModel extends AndroidViewModel
{
    private final HelperDAO mDao;

    public HelperViewModel(@NonNull Application application)
    {
        super(application);
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.helperDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public void insert(Helper helper)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(helper));
    }

    public void delete(Helper helper)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(helper));
    }

    public void update(Helper helper)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(helper));
    }

    public LiveData<List<Helper>> getAll()
    {
        return mDao.getAll();
    }

    public LiveData<Helper> getByHelperId(long helperId)
    {
        return mDao.getByHelperId(helperId);
    }
}
