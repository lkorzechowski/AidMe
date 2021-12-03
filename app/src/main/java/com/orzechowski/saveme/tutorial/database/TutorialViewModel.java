package com.orzechowski.saveme.tutorial.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class TutorialViewModel extends AndroidViewModel
{
    private final TutorialDAO mDao;

    public TutorialViewModel(@NonNull Application application)
    {
        super(application);
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.tutorialDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public void insert(Tutorial tutorial)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(tutorial));
    }

    public void delete(Tutorial tutorial)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(tutorial));
    }

    public void update(Tutorial tutorial)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(tutorial));
    }

    public LiveData<Tutorial> getByTutorialId(long tutorialId)
    {
        return mDao.getByTutorialId(tutorialId);
    }

    public LiveData<List<Tutorial>> getAll()
    {
        return mDao.getAll();
    }
}
