package com.orzechowski.saveme.browser.userrating;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RatingViewModel extends AndroidViewModel
{
    private final RatingRepository mRepository;

    public RatingViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new RatingRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public void insert(Rating rating)
    {
        mRepository.insert(rating);
    }

    public void delete(Rating rating)
    {
        mRepository.delete(rating);
    }

    public void update(Rating rating)
    {
        mRepository.update(rating);
    }

    public LiveData<List<Rating>> getAll()
    {
        return mRepository.getAll();
    }
}
