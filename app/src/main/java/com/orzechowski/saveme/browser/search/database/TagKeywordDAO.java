package com.orzechowski.saveme.browser.search.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TagKeywordDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TagKeyword tagKeyword);

    @Delete
    void delete(TagKeyword tagKeyword);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(TagKeyword tagKeyword);

    @Query("DELETE FROM tagkeyword")
    void deleteAll();

    @Query("SELECT * FROM tagkeyword")
    LiveData<List<TagKeyword>> getAll();

    @Query("SELECT * FROM tagkeyword WHERE keywordId = :keywordId")
    LiveData<List<TagKeyword>> getByKeywordId(long keywordId);

    @Query("SELECT * FROM TagKeyword WHERE tagKeywordId = :tagKeywordId")
    LiveData<TagKeyword> getByTagKeywordId(long tagKeywordId);
}
