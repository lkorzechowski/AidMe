package com.orzechowski.aidme.browser.search.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface KeywordDAO
{
    @Insert
    void insert(Keyword keyword);

    @Delete
    void delete(Keyword keyword);

    @Update
    void update(Keyword keyword);

    @Query("DELETE FROM keyword")
    void deleteAll();

    @Query("SELECT * FROM keyword WHERE word LIKE '%' || :partial || '%'")
    LiveData<Keyword> getByPartialWord(String partial);
}
