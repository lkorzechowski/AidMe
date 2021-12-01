package com.orzechowski.saveme.helper.database;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface EmailDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Email email);

    @Delete
    void delete(Email email);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Email email);

    @Nullable
    @Query("SELECT * FROM email")
    Email get();
}
