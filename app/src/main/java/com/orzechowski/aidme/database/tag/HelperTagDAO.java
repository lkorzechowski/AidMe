package com.orzechowski.aidme.database.tag;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HelperTagDAO
{
    @Insert
    void insert(HelperTag tag);

    @Delete
    void delete(HelperTag tag);

    @Update
    void update(HelperTag tag);

    @Query("DELETE FROM helpertag")
    void deleteAll();

    @Query("SELECT * FROM helpertag")
    LiveData<List<HelperTag>> getAll();

    @Query("SELECT * FROM helpertag WHERE tagId = :tagId")
    LiveData<List<HelperTag>> getByTagId(long tagId);

    @Query("SELECT * FROM helpertag WHERE helperId = :helperId")
    LiveData<List<HelperTag>> getByHelperId(long helperId);

    @Query("SELECT * FROM helpertag WHERE helperTagId = :helperTagId")
    LiveData<HelperTag> getByHelperTagId(long helperTagId);
}
