package com.example.musicplayer.models.data;

import java.util.ArrayList;

public class Album {

    public String title;
    public ArrayList<Song> songs;

    public Album(String title, ArrayList<Song> songs) {
        this.title = title;
        this.songs = songs;
    }

}
