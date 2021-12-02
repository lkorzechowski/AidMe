package com.orzechowski.saveme.settings.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PreferenceDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Preference preference);

    @Delete
    void delete(Preference preference);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Preference preference);

    @Query("SELECT * FROM preference")
    LiveData<Preference> get();
}
