package com.example.musicplayer.presenters;

import android.util.Log;

import com.example.musicplayer.BaseContract;
import com.example.musicplayer.models.MusicProvider;
import com.example.musicplayer.models.data.Song;
import com.example.musicplayer.views.SongsListFragment;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SongsListPresenter implements BaseContract.Presenter {
    private MusicProvider mMusicProvider;
    private CompositeDisposable mDisposables;
    private SongsListFragment mFragment;

    public SongsListPresenter(SongsListFragment fragment){
        mFragment = fragment;
    }

    @Override
    public void onCreate() {
        mDisposables = new CompositeDisposable();
        mMusicProvider = new MusicProvider(mFragment.getContext());
    }

    public void fetchSongsList(){
        Disposable disposableSongsList = mMusicProvider
                .getSongList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<Song>>() {
                    @Override
                    public void onSuccess(ArrayList<Song> songs) {
                        for(int i = 0; i < songs.size(); i++){
                            Song song = songs.get(i);
                            Log.d("AllSongsList", song.title + "  |  " + song.singer + "  |  " + song.album + "  |  " + song.duration);
                        }
                        mFragment.addSongs(songs);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        mDisposables.add(disposableSongsList);
    }

    @Override
    public void onDestroy() {
        mDisposables.clear();
    }
}
