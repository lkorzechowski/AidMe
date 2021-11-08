package com.orzechowski.saveme.browser.search.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class KeywordViewModel extends AndroidViewModel
{
    private final KeywordRepository mRepository;

    public KeywordViewModel(@NonNull Application application)
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

    public LiveData<List<Keyword>> getAll()
    {
        return mRepository.getAll();
    }

    public LiveData<Keyword> getByKeywordId(long keywordId)
    {
        return mRepository.getByKeywordId(keywordId);
    }
}