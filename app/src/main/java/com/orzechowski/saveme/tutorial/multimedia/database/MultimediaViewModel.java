package com.orzechowski.saveme.tutorial.multimedia.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class MultimediaViewModel extends AndroidViewModel
{
    private final MultimediaDAO mDao;

    public MultimediaViewModel(@NonNull Application application)
    {
        super(application);
        GlobalRoomDatabase globalRoomDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalRoomDatabase.multimediaDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public void insert(Multimedia multimedia)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(multimedia));
    }

    public void delete(Multimedia multimedia)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(multimedia));
    }

    public void update(Multimedia multimedia)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(multimedia));
    }

    public LiveData<List<Multimedia>> getByTutorialId(long tutorialId)
    {
        return mDao.getByTutorialId(tutorialId);
    }

    public LiveData<Multimedia> getByMultimediaId(long multimediaId)
    {
        return mDao.getByMultimediaId(multimediaId);
    }
}
