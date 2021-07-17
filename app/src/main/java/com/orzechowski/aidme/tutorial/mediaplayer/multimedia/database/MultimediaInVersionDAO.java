package com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MultimediaInVersionDAO
{
    @Insert
    void insert(MultimediaInVersion multimediaInVersion);

    @Delete
    void delete(MultimediaInVersion multimediaInVersion);

    @Update
    void update(MultimediaInVersion multimediaInVersion);

    @Query("DELETE FROM multimediainversion")
    void deleteAll();

    @Query("SELECT multimediaId FROM multimediainversion WHERE versionId = :versionId")
    LiveData<List<Long>> getByVersionId(long versionId);
}
