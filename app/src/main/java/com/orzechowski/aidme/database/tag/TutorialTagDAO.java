package com.orzechowski.aidme.database.tag;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TutorialTagDAO
{
    @Insert
    void insert(TutorialTag tag);

    @Delete
    void delete(TutorialTag tag);

    @Update
    void update(TutorialTag tag);

    @Query("DELETE FROM tutorialtag")
    void deleteAll();

    @Query("SELECT * FROM tutorialtag WHERE tagId = :tagId")
    LiveData<TutorialTag> getByTagId(long tagId);

    @Query("SELECT * FROM helpertag WHERE helperId = :tutorialId")
    LiveData<TutorialTag> getByTutorialId(long tutorialId);
}
