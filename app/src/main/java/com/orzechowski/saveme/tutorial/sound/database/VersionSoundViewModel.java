package com.orzechowski.saveme.tutorial.sound.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class VersionSoundViewModel extends AndroidViewModel
{
    private final VersionSoundDAO mDao;

    public VersionSoundViewModel(@NonNull Application application)
    {
        super(application);
        GlobalRoomDatabase database = GlobalRoomDatabase.getDatabase(application);
        mDao = database.soundInVersionDAO();
    }

    public void insert(VersionSound versionSound)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(versionSound));
    }

    public void delete(VersionSound versionSound)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(versionSound));
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public void update(VersionSound versionSound)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(versionSound));
    }

    public LiveData<List<Long>> getByVersionId(long versionId)
    {
        return mDao.getByVersionId(versionId);
    }

    public LiveData<VersionSound> getByVersionSoundId(long versionSoundId)
    {
        return mDao.getByVersionSoundId(versionSoundId);
    }
}
