package com.orzechowski.aidme.browser.search.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TagKeywordViewModel extends AndroidViewModel
{
    private final TagKeywordRepository mRepository;

    public TagKeywordViewModel(@NonNull @NotNull Application application)
    {
        super(application);
        mRepository = new TagKeywordRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
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

    public LiveData<List<TagKeyword>> getByTagId(long tagId)
    {
        return mRepository.getByTagId(tagId);
    }
}
