package com.example.musicplayer.models.database;

import android.util.Log;

import com.example.musicplayer.models.data.Playlist;
import com.example.musicplayer.models.data.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public abstract class JSONConverter {
    public static ArrayList<Song> fromJSON(String jsonString){
        ArrayList<Song> songs = new ArrayList<Song>();
        try {
            JSONArray jsonSongs = new JSONArray(jsonString);
            for(int i = 0; i < jsonSongs.length(); i++){
                JSONObject jsonSong = jsonSongs.getJSONObject(i);
                songs.add(new Song(
                        jsonSong.getLong("id"),
                        jsonSong.getLong("duration"),
                        jsonSong.getString("title"),
                        jsonSong.getString("singer"),
                        jsonSong.getString("album")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public static String toJSON(ArrayList<Song> songs){
        JSONArray jsonSongs = new JSONArray();
        for(int i = 0; i < songs.size(); i++){
            JSONObject jsonSong = new JSONObject();
            try {
                jsonSong.put("id", songs.get(i).id);
                jsonSong.put("duration", songs.get(i).duration);
                jsonSong.put("title", songs.get(i).title);
                jsonSong.put("singer", songs.get(i).singer);
                jsonSong.put("album", songs.get(i).album);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonSongs.put(jsonSong);
        }
        return jsonSongs.toString();
    }

}
