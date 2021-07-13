package com.orzechowski.aidme.database.tag;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

public class TagViewModel extends AndroidViewModel
{
    private final TagRepository mRepository;

    public TagViewModel(@NonNull @NotNull Application application)
    {
        super(application);
        mRepository = new TagRepository(application);
    }

    public void deleteAll()
    {
        mRepository.deleteAll();
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

    public LiveData<Tag> getById(long tagId)
    {
        return mRepository.getById(tagId);
    }
}
