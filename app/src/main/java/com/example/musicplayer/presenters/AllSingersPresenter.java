package com.example.musicplayer.presenters;

import com.example.musicplayer.BaseContract;
import com.example.musicplayer.models.MusicProvider;
import com.example.musicplayer.models.data.Singer;
import com.example.musicplayer.models.data.Song;
import com.example.musicplayer.views.AllSingersFragment;

import java.util.ArrayList;
import java.util.HashSet;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AllSingersPresenter implements BaseContract.Presenter {

    private MusicProvider mMusicProvider;
    private CompositeDisposable mDisposables;
    private AllSingersFragment mFragment;

    public AllSingersPresenter(AllSingersFragment fragment){
        mFragment = fragment;
    }

    @Override
    public void onCreate() {
        mDisposables = new CompositeDisposable();
        mMusicProvider = new MusicProvider(mFragment.getContext());
    }

    public void fetchSingersList(){
        Disposable disposableSingersList = mMusicProvider.getSongList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<Song>>() {
                    @Override
                    public void onSuccess(ArrayList<Song> songs) {
                        HashSet<String> singersSet = new HashSet<String>();
                        for(int i = 0; i < songs.size(); i++){
                            Song song = songs.get(i);
                            singersSet.add(song.singer);
                        }
                        ArrayList<String> singerNames = new ArrayList<String>(singersSet);
                        ArrayList<Singer> singers = new ArrayList<Singer>();
                        for(int i = 0; i < singerNames.size(); i++){
                            ArrayList<Song> singerSongs = new ArrayList<Song>();
                            for(int j = 0; j < songs.size(); j++){
                                if(singerNames.get(i).equals(songs.get(j).singer)){
                                    singerSongs.add(songs.get(j));
                                }
                            }
                            singers.add(new Singer(singerNames.get(i), singerSongs));
                        }
                        mFragment.addSingers(singers);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        mDisposables.add(disposableSingersList);
    }

    @Override
    public void onDestroy() {
        mDisposables.clear();
    }
}
