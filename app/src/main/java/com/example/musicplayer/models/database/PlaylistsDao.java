package com.example.musicplayer.models.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musicplayer.models.data.Playlist;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface PlaylistsDao {

    @Query("SELECT * FROM playlist")
    Single<List<Playlist>> getAllPlaylists();

    @Insert
    void addPlaylist(Playlist playlist);

    @Delete
    void deletePlaylist(Playlist playlist);

    @Update
    void updatePlaylist(Playlist playlist);
}
