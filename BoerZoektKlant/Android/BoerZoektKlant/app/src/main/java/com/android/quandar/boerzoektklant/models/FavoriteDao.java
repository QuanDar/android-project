package com.android.quandar.boerzoektklant.models;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void insert(Favorite favorite);

    @Update
    void update(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

    @Query("DELETE FROM favorite_table")
    void deleteAllNotes();

    @Query("SELECT * FROM favorite_table ORDER BY rating DESC")
    LiveData<List<Favorite>> getAllNotes();
}
