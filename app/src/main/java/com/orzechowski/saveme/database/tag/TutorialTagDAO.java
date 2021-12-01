package com.orzechowski.saveme.database.tag;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TutorialTagDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TutorialTag tag);

    @Delete
    void delete(TutorialTag tag);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(TutorialTag tag);

    @Query("DELETE FROM tutorialtag")
    void deleteAll();

    @Query("SELECT * FROM tutorialTag")
    LiveData<List<TutorialTag>> getAll();

    @Query("SELECT * FROM tutorialtag WHERE tagId = :tagId")
    LiveData<List<TutorialTag>> getByTagId(long tagId);

    @Query("SELECT * FROM tutorialtag WHERE tutorialId = :tutorialId")
    LiveData<List<TutorialTag>> getByTutorialId(long tutorialId);

    @Query("SELECT * FROM tutorialtag WHERE tutorialTagId = :tutorialTagId")
    LiveData<TutorialTag> getByTutorialTagId(long tutorialTagId);
}
