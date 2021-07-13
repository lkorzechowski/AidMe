package com.orzechowski.aidme.database.tag;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CategoryTagDAO
{
    @Insert
    void insert(CategoryTag tag);

    @Delete
    void delete(CategoryTag tag);

    @Update
    void update(CategoryTag tag);

    @Query("DELETE FROM categorytag")
    void deleteAll();

    @Query("SELECT * FROM categorytag WHERE tagId = :tagId")
    LiveData<CategoryTag> getByTagId(long tagId);

    @Query("SELECT * FROM categorytag WHERE categoryId = :categoryId")
    LiveData<CategoryTag> getByCategoryId(long categoryId);
}
