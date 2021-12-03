package com.orzechowski.saveme.tutorial.multimedia.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class VersionMultimediaViewModel extends AndroidViewModel
{
    private final VersionMultimediaDAO mDao;

    public VersionMultimediaViewModel(@NonNull Application application)
    {
        super(application);
        GlobalRoomDatabase database = GlobalRoomDatabase.getDatabase(application);
        mDao = database.versionMultimediaDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public void insert(VersionMultimedia versionMultimedia)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(versionMultimedia));
    }

    public void delete(VersionMultimedia versionMultimedia)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(versionMultimedia));
    }

    public void update(VersionMultimedia versionMultimedia)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(versionMultimedia));
    }

    public LiveData<List<Long>> getByVersionId(long versionId)
    {
        return mDao.getByVersionId(versionId);
    }

    public LiveData<VersionMultimedia> getByVersionMultimediaId(long versionMultimediaId)
    {
        return mDao.getByVersionMultimediaId(versionMultimediaId);
    }
}
