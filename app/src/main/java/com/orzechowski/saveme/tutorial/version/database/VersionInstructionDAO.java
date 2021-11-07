package com.orzechowski.saveme.tutorial.version.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VersionInstructionDAO
{
    @Insert
    void insert(VersionInstruction versionInstruction);

    @Delete
    void delete(VersionInstruction versionInstruction);

    @Update
    void update(VersionInstruction versionInstruction);

    @Query("DELETE FROM versioninstruction")
    void deleteAll();

    @Query("SELECT instructionPosition FROM versioninstruction WHERE versionId = :versionId")
    LiveData<List<Integer>> getByVersionId(Long versionId);

    @Query("SELECT * FROM versioninstruction WHERE versionInstructionId = :versionInstructionId")
    LiveData<VersionInstruction> getByVersionInstructionId(long versionInstructionId);
}
