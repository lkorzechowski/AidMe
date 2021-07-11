package com.orzechowski.aidme.tutorial.mediaplayer.database;

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

    @Query("SELECT * FROM multimedia WHERE multimediaId = :multimediaId AND tutorialId = :tutorialId")
    LiveData<Multimedia> getByMediaIdAndTutorialId(long multimediaId, long tutorialId);
}
