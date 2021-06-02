package com.orzechowski.prototyp.version.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.prototyp.database.GlobalRoomDatabase;

import java.util.List;

public class VersionRepository {

    private final VersionDAO mDao;
    private final LiveData<List<Version>> mVersions;

    public VersionRepository(Application application){
        GlobalRoomDatabase database = GlobalRoomDatabase.getDatabase(application);
        mDao = database.versionDao();
        mVersions = mDao.getAll();
    }

    public LiveData<List<Version>> getAll(){
        return mVersions;
    }

    public void deleteAll(){
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    public void insert(Version version){
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.insert(version));
    }

    public void delete(Version version){
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.delete(version));
    }

    public void update(Version version){
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.update(version));
    }

    public LiveData<List<Version>> getByVersionId(long versionId){
        return mDao.getByVersionId(versionId);
    }

    public LiveData<List<Version>> getByTutorialId(long tutorialId){
        return mDao.getByTutorialId(tutorialId);
    }
}
