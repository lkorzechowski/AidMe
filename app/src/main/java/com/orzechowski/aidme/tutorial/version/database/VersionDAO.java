package com.orzechowski.aidme.tutorial.version.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VersionDAO
{
    @Insert
    void insert(Version version);

    @Delete
    void delete(Version prompt);

    @Update
    void update(Version prompt);

    @Query("DELETE FROM version")
    void deleteAll();

    @Query("SELECT * FROM version WHERE tutorialId = :tutorialId AND hasParent = 0")
    LiveData<List<Version>> getBaseByTutorialId(long tutorialId);

    @Query("SELECT * FROM version WHERE parentVersionId = :parentId")
    LiveData<List<Version>> getByParentVersionId(long parentId);
}
