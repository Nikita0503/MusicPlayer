package com.example.musicplayer.models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.musicplayer.models.data.Song;

import java.util.ArrayList;
import java.util.Collections;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class MusicProvider {
    private Context mContext;

    public MusicProvider(Context context){
        mContext = context;
    }

    public Single<ArrayList<Song>> getSongList = Single.create(new SingleOnSubscribe<ArrayList<Song>>() {
        @Override
        public void subscribe(SingleEmitter<ArrayList<Song>> e) throws Exception {
            ContentResolver musicResolver = mContext.getContentResolver();
            Cursor musicCursor = musicResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            ArrayList<Song> songs = new ArrayList<>();
            if(musicCursor!=null && musicCursor.moveToFirst()){
                int titleColumn = musicCursor.getColumnIndex
                        (MediaStore.Audio.Media.TITLE);
                int idColumn = musicCursor.getColumnIndex
                        (MediaStore.Audio.Media._ID);
                int artistColumn = musicCursor.getColumnIndex
                        (MediaStore.Audio.Media.ARTIST);
                int durationColumn = musicCursor.getColumnIndex
                        (MediaStore.Audio.Media.DURATION);
                int albumColumn = musicCursor.getColumnIndex
                        (MediaStore.Audio.Media.ALBUM);
                do {
                    long id = musicCursor.getLong(idColumn);
                    long duration = musicCursor.getLong(durationColumn);
                    String title = musicCursor.getString(titleColumn);
                    String artist = musicCursor.getString(artistColumn);
                    String album = musicCursor.getString(albumColumn);
                    //Uri uri =  ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                    songs.add(new Song(id, duration, title, artist, album));
                    //Log.d("MusicApplication", title + "  |  " + artist + "  |  " + album + "  |  " + duration);
                }
                while (musicCursor.moveToNext());
            }
            Collections.reverse(songs);
            e.onSuccess(songs);
        }
    });
}
