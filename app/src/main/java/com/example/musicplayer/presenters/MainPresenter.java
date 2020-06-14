package com.example.musicplayer.presenters;

import android.util.Log;

import com.example.musicplayer.BaseContract;
import com.example.musicplayer.models.data.Playlist;
import com.example.musicplayer.models.data.Song;
import com.example.musicplayer.models.database.JSONConverter;
import com.example.musicplayer.models.database.MusicDatabase;
import com.example.musicplayer.views.MainFragment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements BaseContract.Presenter {

    private MusicDatabase mMusicDatabase;
    private CompositeDisposable mDisposables;
    private MainFragment mFragment;

    public MainPresenter(MainFragment fragment){
        mFragment = fragment;
    }

    @Override
    public void onCreate() {
        mDisposables = new CompositeDisposable();
        mMusicDatabase = new MusicDatabase();
    }

    public void fetchSongsHistory(){
        Disposable disposableSongsHistory = mMusicDatabase
                .getHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Song>>() {
                    @Override
                    public void onSuccess(List<Song> value) {
                        mFragment.setSongsHistory(selectionSort(value));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        mDisposables.add(disposableSongsHistory);
    }

    public void clearHistory(){
        DisposableCompletableObserver completableClearHistory = mMusicDatabase
                .clearHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        mFragment.fetchSongsHistory();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        mDisposables.add(completableClearHistory);
    }

    private ArrayList<Song> selectionSort(List<Song> songs){
        Song[] arr = songs.toArray(new Song[songs.size()]);
        for (int i = 0; i < songs.size(); i++) {
            long min = arr[i].date;
            int min_i = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].date < min) {
                    min = arr[j].date;
                    min_i = j;
                }
            }
            if (i != min_i) {
                Song tmp = arr[i];
                arr[i] = arr[min_i];
                arr[min_i] = tmp;
            }
        }

        ArrayList<Song> sortedSongs = new ArrayList<Song>(Arrays.asList(arr));
        return sortedSongs;
    }

    public void fetchAllPlaylists(){
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
                        mFragment.setPlaylists(new ArrayList<Playlist>(playlists));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        mDisposables.add(disposableAllPlaylists);
    }

    public void addPlaylist(Playlist playlist){
        DisposableCompletableObserver completableAddPlaylist = mMusicDatabase
                .addPlaylist(playlist)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        mFragment.fetchAllPlaylists();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        mDisposables.add(completableAddPlaylist);
    }

    public void deletePlaylist(Playlist playlist){
        playlist.songsJSON = JSONConverter.toJSON(playlist.songs);
        DisposableCompletableObserver completableDeletePlaylist = mMusicDatabase
                .deletePlaylist(playlist)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        mFragment.fetchAllPlaylists();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        mDisposables.add(completableDeletePlaylist);
    }

    @Override
    public void onDestroy() {
        mDisposables.clear();
    }
}
