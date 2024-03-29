package com.orzechowski.saveme.tutorial.version.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VersionDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Version version);

    @Delete
    void delete(Version prompt);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Version prompt);

    @Query("DELETE FROM version")
    void deleteAll();

    @Query("SELECT * FROM version WHERE tutorialId = :tutorialId AND hasParent = 0")
    LiveData<List<Version>> getBaseByTutorialId(long tutorialId);

    @Query("SELECT * FROM version WHERE parentVersionId = :parentId")
    LiveData<List<Version>> getByParentVersionId(long parentId);

    @Query("SELECT * FROM version WHERE versionId = :versionId")
    LiveData<Version> getByVersionId(long versionId);
}
