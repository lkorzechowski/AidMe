package com.orzechowski.saveme.tutorial.multimedia.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MultimediaDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Multimedia multimedia);

    @Delete
    void delete(Multimedia multimedia);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Multimedia multimedia);

    @Query("DELETE FROM multimedia")
    void deleteAll();

    @Query("SELECT * FROM multimedia WHERE tutorialId = :tutorialId")
    LiveData<List<Multimedia>> getByTutorialId(long tutorialId);

    @Query("SELECT * FROM multimedia WHERE multimediaId = :multimediaId")
    LiveData<Multimedia> getByMultimediaId(long multimediaId);
}
