package com.orzechowski.aidme.settings.helper.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HelperDAO
{
    @Insert()
    void insert(Helper helper);

    @Delete()
    void delete(Helper helper);

    @Update()
    void update(Helper helper);

    @Query("DELETE FROM helper")
    void deleteAll();

    @Query("SELECT * FROM helper")
    LiveData<List<Helper>> getAll();
}