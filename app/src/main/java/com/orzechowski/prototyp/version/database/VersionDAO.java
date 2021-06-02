package com.orzechowski.prototyp.version.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VersionDAO {

    @Insert()
    void insert(Version version);

    @Delete()
    void delete(Version prompt);

    @Update()
    void update(Version prompt);

    @Query("DELETE FROM version")
    void deleteAll();

    @Query("SELECT * FROM version")
    LiveData<List<Version>> getAll();

    @Query("SELECT * FROM version WHERE tutorialId = :tutorialId")
    LiveData<List<Version>> getByTutorialId(Long tutorialId);

    @Query("SELECT * FROM version WHERE versionId = :versionId")
    LiveData<List<Version>> getByVersionId(Long versionId);
}
