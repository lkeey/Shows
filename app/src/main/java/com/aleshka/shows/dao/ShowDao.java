package com.aleshka.shows.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.aleshka.shows.models.Show;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ShowDao {

    @Query("SELECT * FROM shows")
    Flowable<List<Show>> getWatchList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchList(Show show);

    @Delete
    void removeFromWatchList(Show show);

}
