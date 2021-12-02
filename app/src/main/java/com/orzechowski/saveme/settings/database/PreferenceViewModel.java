package com.orzechowski.saveme.settings.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

public class PreferenceViewModel extends AndroidViewModel
{
    private final PreferenceDAO mDao;

    public PreferenceViewModel(@NonNull Application application)
    {
        super(application);
        GlobalRoomDatabase globalRoomDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalRoomDatabase.preferenceDAO();
    }

    public void insert(Preference preference)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(preference));
    }

    public void delete(Preference preference)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(preference));
    }

    public void update(Preference preference)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(preference));
    }

    public LiveData<Preference> get()
    {
        return mDao.get();
    }
}
