package com.orzechowski.aidme.tutorial.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TutorialLinkDAO
{
    @Insert
    void insert(TutorialLink tutorialLink);

    @Delete
    void delete(TutorialLink tutorialLink);

    @Update
    void update(TutorialLink tutorialLink);

    @Query("DELETE FROM tutoriallink")
    void deleteAll();

    @Query("SELECT * FROM tutoriallink WHERE originId = :tutorialId")
    LiveData<List<TutorialLink>> getByOriginId(long tutorialId);
}
