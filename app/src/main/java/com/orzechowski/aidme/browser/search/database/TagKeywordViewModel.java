package com.orzechowski.aidme.browser.search.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TagKeywordViewModel extends AndroidViewModel
{
    private final TagKeywordRepository mRepository;

    public TagKeywordViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new TagKeywordRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public LiveData<List<TagKeyword>> getAll()
    {
        return mRepository.getAll();
    }

    public void insert(TagKeyword tagKeyword)
    {
        mRepository.insert(tagKeyword);
    }

    public void delete(TagKeyword tagKeyword)
    {
        mRepository.delete(tagKeyword);
    }

    public void update(TagKeyword tagKeyword)
    {
        mRepository.update(tagKeyword);
    }

    public LiveData<List<TagKeyword>> getByKeywordId(long keywordId)
    {
        return mRepository.getByKeywordId(keywordId);
    }

    public LiveData<TagKeyword> getByTagKeywordId(long tagKeywordId)
    {
        return mRepository.getByTagKeywordId(tagKeywordId);
    }
}
