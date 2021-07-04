package com.orzechowski.aidme.tutorial.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TutorialDAO
{
    @Insert
    void insert(Tutorial tutorial);

    @Delete
    void delete(Tutorial tutorial);

    @Update
    void update(Tutorial tutorial);

    @Query("DELETE FROM tutorial")
    void deleteAll();

    @Query("SELECT * FROM tutorial")
    LiveData<List<Tutorial>> getAll();

    @Query("SELECT * FROM tutorial WHERE tutorialId = :tutorialId")
    Tutorial getByTutorialId(Long tutorialId);

    @Query("SELECT * FROM tutorial WHERE tags LIKE '%' || :tag || '%'")
    LiveData<List<Tutorial>> getByTag(String tag);
}
