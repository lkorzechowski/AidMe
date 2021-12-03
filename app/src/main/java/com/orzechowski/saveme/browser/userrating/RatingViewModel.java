package com.orzechowski.saveme.browser.userrating;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orzechowski.saveme.database.GlobalRoomDatabase;

import java.util.List;

public class RatingViewModel extends AndroidViewModel
{
    private final RatingDAO mDao;

    public RatingViewModel(@NonNull Application application)
    {
        super(application);
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
