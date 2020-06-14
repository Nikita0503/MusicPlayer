package com.example.musicplayer.models.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.musicplayer.models.data.Song;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM song")
    Single<List<Song>> getHistory();

    @Insert
    void addSongToHistory(Song song);

    @Delete
    void deleteSongFromHistory(Song song);

    @Transaction
    default void addSongToHistoryInTransaction(Song song){
        deleteSongFromHistory(song);
        addSongToHistory(song);
    }

    @Query("DELETE FROM song")
    void clearHistory();
}
