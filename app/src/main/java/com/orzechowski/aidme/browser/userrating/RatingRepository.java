package com.orzechowski.aidme.browser.userrating;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.orzechowski.aidme.database.GlobalRoomDatabase;

import java.util.List;

public class RatingRepository
{
    private final RatingDAO mDao;

    public RatingRepository(Application application)
    {
        GlobalRoomDatabase globalDatabase = GlobalRoomDatabase.getDatabase(application);
        mDao = globalDatabase.ratingDAO();
    }

    public void deleteAll()
    {
        GlobalRoomDatabase.executor.execute(mDao::deleteAll);
    }

    public void insert(Rating rating)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.insert(rating));
    }

    public void delete(Rating rating)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.delete(rating));
    }

    public void update(Rating rating)
    {
        GlobalRoomDatabase.executor.execute(()->mDao.update(rating));
    }

    public LiveData<List<Rating>> getAll()
    {
        return mDao.getAll();
    }
}
