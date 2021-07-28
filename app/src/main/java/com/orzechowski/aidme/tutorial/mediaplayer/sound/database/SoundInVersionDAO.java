package com.orzechowski.aidme.tutorial.mediaplayer.sound.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SoundInVersionDAO
{
    @Insert
    void insert(SoundInVersion soundInVersion);

    @Delete
    void delete(SoundInVersion soundInVersion);

    @Update
    void update(SoundInVersion soundInVersion);

    @Query("DELETE FROM soundinversion")
    void deleteAll();

    @Query("SELECT tutorialSoundId FROM soundinversion WHERE versionId = :versionId")
    LiveData<List<Long>> getByVersionId(long versionId);
}
