package com.orzechowski.saveme.tutorial.instructions.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InstructionSetDAO
{
    @Insert
    void insert(InstructionSet instructionSet);

    @Delete
    void delete(InstructionSet instructionSet);

    @Update
    void update(InstructionSet instructionSet);

    @Query("DELETE FROM instructionset")
    void deleteAll();

    @Query("SELECT * FROM instructionset WHERE tutorialId = :tutorialId ORDER BY position ASC")
    LiveData<List<InstructionSet>> getByTutorialId(Long tutorialId);

    @Query("SELECT * FROM instructionset WHERE position = :position AND tutorialId = :tutorialId")
    LiveData<InstructionSet> getByPositionAndTutorialId(int position, long tutorialId);

    @Query("SELECT COUNT(*) FROM instructionset WHERE tutorialId = :tutorialId")
    LiveData<Integer> getTutorialSize(long tutorialId);

    @Query("SELECT * FROM instructionset")
    LiveData<List<InstructionSet>> getAll();

    @Query("SELECT * FROM instructionset WHERE instructionSetId = :instructionSetId")
    LiveData<InstructionSet> getByInstructionSetId(long instructionSetId);
}
