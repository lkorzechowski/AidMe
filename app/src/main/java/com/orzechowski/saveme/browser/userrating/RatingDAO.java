package com.orzechowski.saveme.browser.userrating;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RatingDAO
{
    @Insert
    void insert(Rating rating);

    @Delete
    void delete(Rating rating);

    @Update
    void update(Rating rating);

    @Query("DELETE FROM rating")
    void deleteAll();

    @Query("SELECT * FROM rating")
    LiveData<List<Rating>> getAll();
}
