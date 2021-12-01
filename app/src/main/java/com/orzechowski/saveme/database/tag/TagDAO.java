package com.orzechowski.saveme.database.tag;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TagDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Tag tag);

    @Delete
    void delete(Tag tag);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Tag tag);

    @Query("DELETE FROM tag")
    void deleteALl();

    @Query("SELECT * FROM tag")
    LiveData<List<Tag>> getAll();

    @Query("SELECT * FROM tag WHERE tagId = :tagId")
    LiveData<Tag> getByTagId(long tagId);
}
