package com.orzechowski.saveme.helper.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HelperDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Helper helper);

    @Delete
    void delete(Helper helper);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Helper helper);

    @Query("DELETE FROM helper")
    void deleteAll();

    @Query("SELECT * FROM helper")
    LiveData<List<Helper>> getAll();

    @Query("SELECT * FROM helper WHERE helperId = :helperId")
    LiveData<Helper> getByHelperId(long helperId);
}
