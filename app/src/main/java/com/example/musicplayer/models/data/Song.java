package com.example.musicplayer.models.data;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Song {

    @PrimaryKey
    public long id;
    public long date;
    public long duration;
    public String title;
    public String singer;
    public String album;

    public Song(long id, long duration, String title, String singer, String album) {
        this.id = id;
        this.duration = duration;
        this.title = title;
        this.singer = singer;
        this.album = album;
    }
}
