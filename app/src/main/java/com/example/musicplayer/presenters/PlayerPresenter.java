package com.example.musicplayer.presenters;

import com.example.musicplayer.BaseContract;
import com.example.musicplayer.models.data.Song;
import com.example.musicplayer.models.database.MusicDatabase;
import com.example.musicplayer.views.PlayerFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class PlayerPresenter implements BaseContract.Presenter {

    private MusicDatabase mMusicDatabase;
    private CompositeDisposable mDisposables;
    private PlayerFragment mFragment;

    public PlayerPresenter(PlayerFragment fragment){
        mFragment = fragment;
    }

    @Override
    public void onCreate() {
        mDisposables = new CompositeDisposable();
        mMusicDatabase = new MusicDatabase();
    }

    public void addSongToHistory(Song song){
        DisposableCompletableObserver completable = mMusicDatabase
                .addSongToHistory(song)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        mFragment.updateHistory();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        mDisposables.add(completable);
    }

    @Override
    public void onDestroy() {
        mDisposables.clear();
    }
}
