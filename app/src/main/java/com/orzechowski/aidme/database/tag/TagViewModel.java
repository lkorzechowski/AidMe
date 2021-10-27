package com.orzechowski.aidme.database.tag;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TagViewModel extends AndroidViewModel
{
    private final TagRepository mRepository;

    public TagViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new TagRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
    }

    public LiveData<List<Tag>> getAll()
    {
        return mRepository.getAll();
    }

    public void insert(Tag tag)
    {
        mRepository.insert(tag);
    }

    public void delete(Tag tag)
    {
        mRepository.delete(tag);
    }

    public void update(Tag tag)
    {
        mRepository.update(tag);
    }

    public LiveData<Tag> getByTagId(long tagId)
    {
        return mRepository.getByTagId(tagId);
    }
}
