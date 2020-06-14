package com.example.musicplayer.models.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.musicplayer.models.data.Playlist;
import com.example.musicplayer.models.data.Song;

@Database(entities = {Song.class, Playlist.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HistoryDao historyDao();
    public abstract PlaylistsDao playlistDao();
}
