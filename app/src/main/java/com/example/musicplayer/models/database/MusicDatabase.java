package com.example.musicplayer.models.database;

import android.util.Log;

import com.example.musicplayer.models.data.Playlist;
import com.example.musicplayer.models.data.Song;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class MusicDatabase {

    private HistoryDao mHistoryDao;
    private PlaylistsDao mPlaylistDao;

    public MusicDatabase(){
        AppDatabase db = App.getInstance().getDatabase();
        mHistoryDao = db.historyDao();
        mPlaylistDao = db.playlistDao();
    }

    public Single<List<Song>> getHistory(){
        return mHistoryDao.getHistory();
    }

    public Completable addSongToHistory(Song song){
        Date date = new Date();
        song.date = date.getTime();
        return Completable.fromAction(() -> {
            mHistoryDao.addSongToHistoryInTransaction(song);
        });
    }

    public Completable clearHistory(){
        return Completable.fromAction(() -> mHistoryDao.clearHistory());
    }

    public Single<List<Playlist>> getAllPlaylists(){
        return mPlaylistDao.getAllPlaylists();
    }

    public Completable addPlaylist(Playlist playlist){
        return Completable.fromAction(() -> mPlaylistDao.addPlaylist(playlist));
    }

    public Completable deletePlaylist(Playlist playlist){
        return Completable.fromAction(() -> mPlaylistDao.deletePlaylist(playlist));
    }

    public Completable updatePlaylist(Playlist playlist){
        return Completable.fromAction(() -> mPlaylistDao.updatePlaylist(playlist));
    }
}
