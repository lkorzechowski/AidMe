package com.orzechowski.aidme.browser.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDAO
{
    @Insert
    void insert(Category category);

    @Delete
    void delete(Category category);

    @Update
    void update(Category category);

    @Query("DELETE FROM category")
    void deleteAll();

    @Query("SELECT * FROM category WHERE categoryLevel = :categoryLevel AND categoryTags LIKE :categoryTags || '%'")
    LiveData<List<Category>> getByLevelAndTags(int categoryLevel, String categoryTags);

    @Query("SELECT * FROM category WHERE categoryLevel = :categoryLevel")
    LiveData<List<Category>> getByLevel(int categoryLevel);
}
