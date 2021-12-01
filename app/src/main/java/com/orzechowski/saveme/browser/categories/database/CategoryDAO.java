package com.orzechowski.saveme.browser.categories.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category category);

    @Delete
    void delete(Category category);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Category category);

    @Query("DELETE FROM category")
    void deleteAll();

    @Query("SELECT * FROM category")
    LiveData<List<Category>> getAll();

    @Query("SELECT * FROM category WHERE categoryLevel = :categoryLevel")
    LiveData<List<Category>> getByLevel(int categoryLevel);

    @Query("SELECT * FROM category WHERE categoryId = :categoryId")
    LiveData<Category> getByCategoryId(long categoryId);
}
