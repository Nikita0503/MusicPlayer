package com.example.musicplayer.presenters;

import com.example.musicplayer.BaseContract;
import com.example.musicplayer.models.data.Album;
import com.example.musicplayer.models.MusicProvider;
import com.example.musicplayer.models.data.Song;
import com.example.musicplayer.views.AllAlbumsFragment;

import java.util.ArrayList;
import java.util.HashSet;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AllAlbumsPresenter implements BaseContract.Presenter {

    private MusicProvider mMusicProvider;
    private CompositeDisposable mDisposables;
    private AllAlbumsFragment mFragment;

    public AllAlbumsPresenter(AllAlbumsFragment fragment){
        mFragment = fragment;
    }

    @Override
    public void onCreate() {
        mDisposables = new CompositeDisposable();
        mMusicProvider = new MusicProvider(mFragment.getContext());
    }

    public void fetchAlbumsList(){
        Disposable disposableAlbumsList = mMusicProvider
                .getSongList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<Song>>() {
                    @Override
                    public void onSuccess(ArrayList<Song> songs) {
                        HashSet<String> albumsSet = new HashSet<String>();
                        for(int i = 0; i < songs.size(); i++){
                            Song song = songs.get(i);
                            albumsSet.add(song.album);
                        }
                        ArrayList<String> albumNames = new ArrayList<String>(albumsSet);
                        ArrayList<Album> albums = new ArrayList<Album>();
                        for(int i = 0; i < albumNames.size(); i++){
                            ArrayList<Song> albumSongs = new ArrayList<Song>();
                            for(int j = 0; j < songs.size(); j++){
                                if(albumNames.get(i).equals(songs.get(j).album)){
                                    albumSongs.add(songs.get(j));
                                }
                            }
                            albums.add(new Album(albumNames.get(i), albumSongs));
                        }
                        mFragment.addAlbums(albums);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        mDisposables.add(disposableAlbumsList);
    }

    @Override
    public void onDestroy() {
        mDisposables.clear();
    }
}
