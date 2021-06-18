package com.orzechowski.aidme.tutorial.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InstructionSetDAO {

    @Insert()
    void insert(InstructionSet instructionSet);

    @Delete()
    void delete(InstructionSet instructionSet);

    @Update()
    void update(InstructionSet instructionSet);

    @Query("SELECT * FROM instructionset")
    LiveData<List<InstructionSet>> getAll();

    @Query("DELETE FROM instructionset")
    void deleteAll();

    @Query("SELECT * FROM instructionset WHERE tutorialId = :tutorialId ORDER BY tutorialId ASC")
    LiveData<List<InstructionSet>> getByTutorialId(Long tutorialId);

    @Query("SELECT * FROM instructionset WHERE position = :position AND tutorialId = :tutorialId")
    LiveData<List<InstructionSet>> getByPositionAndTutorialId(int position, long tutorialId);
}
