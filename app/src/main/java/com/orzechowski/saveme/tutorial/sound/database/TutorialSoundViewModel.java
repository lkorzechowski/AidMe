package com.orzechowski.saveme.tutorial.sound.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class TutorialSoundViewModel extends AndroidViewModel
{
    private final TutorialSoundDAO mDao;

    public TutorialSoundViewModel(@NonNull Application application)
    {
        super(application);
        GlobalRoomDatabase database = GlobalRoomDatabase.getDatabase(application);
        mDao = database.tutorialSoundDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public void insert(TutorialSound tutorialSound)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(tutorialSound));
    }

    public void delete(TutorialSound tutorialSound)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(tutorialSound));
    }

    public void update(TutorialSound tutorialSound)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(tutorialSound));
    }

    public LiveData<List<TutorialSound>> getByTutorialId(long tutorialId)
    {
        return mDao.getByTutorialId(tutorialId);
    }

    public LiveData<List<TutorialSound>> getAll()
    {
        return mDao.getAll();
    }

    public LiveData<TutorialSound> getByTutorialSoundId(long tutorialSoundId)
    {
        return mDao.getByTutorialSoundId(tutorialSoundId);
    }
}
