package com.orzechowski.saveme.tutorial.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class TutorialLinkViewModel extends AndroidViewModel
{
    private final TutorialLinkDAO mDao;

    public TutorialLinkViewModel(@NonNull Application application)
    {
        super(application);
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.tutorialLinkDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public void insert(TutorialLink tutorialLink)
    {
        GlobalRoomDatabase.executor.execute(()-> mDao.insert(tutorialLink));
    }

    public void delete(TutorialLink tutorialLink)
    {
        GlobalRoomDatabase.executor.execute(()-> mDao.delete(tutorialLink));
    }

    public void update(TutorialLink tutorialLink)
    {
        GlobalRoomDatabase.executor.execute(()-> mDao.update(tutorialLink));
    }

    public LiveData<List<TutorialLink>> getAll()
    {
        return mDao.getAll();
    }

    public LiveData<TutorialLink> getByOriginIdAndPosition(long tutorialId, int instructionNumber)
    {
        return mDao.getByOriginIdAndPosition(tutorialId, instructionNumber);
    }

    public LiveData<TutorialLink> getByTutorialLinkId(long tutorialLinkId)
    {
        return mDao.getByTutorialLinkId(tutorialLinkId);
    }
}
