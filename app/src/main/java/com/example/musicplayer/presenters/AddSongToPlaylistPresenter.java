package com.example.musicplayer.presenters;

import com.example.musicplayer.BaseContract;
import com.example.musicplayer.dialogs.AddSongToPlaylistDialog;
import com.example.musicplayer.models.MusicProvider;
import com.example.musicplayer.models.data.Playlist;
import com.example.musicplayer.models.data.Song;
import com.example.musicplayer.models.database.JSONConverter;
import com.example.musicplayer.models.database.MusicDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AddSongToPlaylistPresenter implements BaseContract.Presenter {

    private MusicDatabase mMusicDatabase;
    private CompositeDisposable mDisposables;
    private AddSongToPlaylistDialog mDialog;

    public AddSongToPlaylistPresenter(AddSongToPlaylistDialog dialog) {
        mDialog = dialog;
    }

    @Override
    public void onCreate() {
        mDisposables = new CompositeDisposable();
        mMusicDatabase = new MusicDatabase();
    }

    public void fetchAllPlaylists() {
        Disposable disposableAllPlaylists = mMusicDatabase
                .getAllPlaylists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Playlist>>() {
                    @Override
                    public void onSuccess(List<Playlist> playlists) {
                        for(int i = 0; i < playlists.size(); i++){
                            ArrayList<Song> songs = JSONConverter.fromJSON(playlists.get(i).songsJSON);
                            playlists.get(i).songs = songs;
                        }
                        mDialog.addPlaylists(new ArrayList<Playlist>(playlists));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        mDisposables.add(disposableAllPlaylists);
    }

    public void addSongToPlaylist(Playlist playlist, Song song){
        playlist.songs.add(song);
        playlist.songsJSON = JSONConverter.toJSON(playlist.songs);
        DisposableCompletableObserver completableAddPlaylist = mMusicDatabase
                .updatePlaylist(playlist)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        onDestroy();
                        mDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        mDisposables.add(completableAddPlaylist);
    }

    @Override
    public void onDestroy() {
        mDisposables.clear();
    }
}
