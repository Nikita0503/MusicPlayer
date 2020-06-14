package com.example.musicplayer.models.data;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Playlist {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String title;
    public String songsJSON;
    @Ignore
    public ArrayList<Song> songs;

    public Playlist(String title, String songsJSON) {
        this.title = title;
        this.songsJSON = songsJSON;
    }
}
