package com.orzechowski.aidme.browser.search.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

public class KeywordViewModel extends AndroidViewModel
{
    private final KeywordRepository mRepository;

    public KeywordViewModel(@NonNull @NotNull Application application)
    {
        super(application);
        mRepository = new KeywordRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public void insert(Keyword keyword)
    {
        mRepository.insert(keyword);
    }

    public void delete(Keyword keyword)
    {
        mRepository.delete(keyword);
    }

    public void update(Keyword keyword)
    {
        mRepository.update(keyword);
    }

    public LiveData<Keyword> getByPartialWord(String partial)
    {
        return mRepository.getByPartialWord(partial);
    }
}
