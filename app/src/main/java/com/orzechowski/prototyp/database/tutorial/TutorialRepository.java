package com.orzechowski.prototyp.database.tutorial;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.orzechowski.prototyp.database.GlobalRoomDatabase;
import java.util.List;

public class TutorialRepository {

    private final TutorialDAO mDao;
    private final LiveData<List<Tutorial>> mTutorials;

    TutorialRepository(Application application){
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.tutorialDao();
        mTutorials = mDao.getAll();
    }

    LiveData<List<Tutorial>> getAll(){
        return mTutorials;
    }

    void deleteAll(){
        GlobalRoomDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    void insert(Tutorial tutorial){
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.insert(tutorial));
    }

    void delete(Tutorial tutorial){
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.delete(tutorial));
    }

    void update(Tutorial tutorial){
        GlobalRoomDatabase.databaseWriteExecutor.execute(()->mDao.update(tutorial));
    }

    LiveData<List<Tutorial>> getByTutorialId(Long tutorialId){
        return mDao.getByTutorialId(tutorialId);
    }
}
