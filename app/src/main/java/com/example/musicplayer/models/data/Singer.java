package com.example.musicplayer.models.data;

import java.util.ArrayList;

public class Singer {

    public String name;
    public ArrayList<Song> songs;

    public Singer(String name, ArrayList<Song> songs) {
        this.name = name;
        this.songs = songs;
    }
}
