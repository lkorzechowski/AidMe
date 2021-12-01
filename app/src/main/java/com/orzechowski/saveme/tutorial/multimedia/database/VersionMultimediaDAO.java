package com.orzechowski.saveme.tutorial.multimedia.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VersionMultimediaDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(VersionMultimedia versionMultimedia);

    @Delete
    void delete(VersionMultimedia versionMultimedia);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(VersionMultimedia versionMultimedia);

    @Query("DELETE FROM versionmultimedia")
    void deleteAll();

    @Query("SELECT multimediaId FROM versionmultimedia WHERE versionId = :versionId")
    LiveData<List<Long>> getByVersionId(long versionId);

    @Query("SELECT * FROM versionmultimedia WHERE versionMultimediaId = :versionMultimediaId")
    LiveData<VersionMultimedia> getByVersionMultimediaId(long versionMultimediaId);
}
