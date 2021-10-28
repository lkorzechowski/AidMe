package com.orzechowski.aidme.tutorial.mediaplayer.sound.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VersionSoundDAO
{
    @Insert
    void insert(VersionSound versionSound);

    @Delete
    void delete(VersionSound versionSound);

    @Update
    void update(VersionSound versionSound);

    @Query("DELETE FROM versionSound")
    void deleteAll();

    @Query("SELECT tutorialSoundId FROM VersionSound WHERE versionId = :versionId")
    LiveData<List<Long>> getByVersionId(long versionId);

    @Query("SELECT * FROM  VersionSound WHERE versionSoundId = :versionSoundId")
    LiveData<VersionSound> getByVersionSoundId(long versionSoundId);
}
