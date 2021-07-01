package com.orzechowski.aidme.tutorial.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MultimediaDAO
{
    @Insert
    void insert(Multimedia multimedia);

    @Delete
    void delete(Multimedia multimedia);

    @Update
    void update(Multimedia multimedia);

    @Query("DELETE FROM multimedia")
    void deleteAll();

    @Query("SELECT * FROM multimedia WHERE position = :position AND tutorialId = :tutorialId")
    LiveData<Multimedia> getByPositionAndTutorialId(int position, long tutorialId);
}