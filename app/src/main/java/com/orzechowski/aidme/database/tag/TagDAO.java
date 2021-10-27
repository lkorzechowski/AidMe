package com.orzechowski.aidme.database.tag;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TagDAO
{
    @Insert
    void insert(Tag tag);

    @Delete
    void delete(Tag tag);

    @Update
    void update(Tag tag);

    @Query("DELETE FROM tag")
    void deleteALl();

    @Query("SELECT * FROM tag")
    LiveData<List<Tag>> getAll();

    @Query("SELECT * FROM tag WHERE tagId = :tagId")
    LiveData<Tag> getByTagId(long tagId);
}
