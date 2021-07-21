package com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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

    @Query("SELECT * FROM multimedia WHERE tutorialId = :tutorialId")
    LiveData<List<Multimedia>> getByTutorialId(long tutorialId);
}
