package com.orzechowski.aidme.browser.search.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TagKeywordDAO
{
    @Insert
    void insert(TagKeyword tagKeyword);

    @Delete
    void delete(TagKeyword tagKeyword);

    @Update
    void update(TagKeyword tagKeyword);

    @Query("DELETE FROM tagkeyword")
    void deleteAll();

    @Query("SELECT * FROM tagkeyword WHERE tagId = :tagId")
    LiveData<List<TagKeyword>> getByTagId(long tagId);
}
