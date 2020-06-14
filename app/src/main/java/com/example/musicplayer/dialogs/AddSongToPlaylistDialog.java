package com.example.musicplayer.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapters.AddSongToPlaylistAdapter;
import com.example.musicplayer.models.data.Playlist;
import com.example.musicplayer.models.data.Song;
import com.example.musicplayer.presenters.AddSongToPlaylistPresenter;

import java.util.ArrayList;

public class AddSongToPlaylistDialog extends Dialog {

    AddSongToPlaylistPresenter mPresenter;
    Song mSong;
    AddSongToPlaylistAdapter mAdapterAddSongToPlaylist;
    RecyclerView mRecyclerViewAllPlaylists;

    public AddSongToPlaylistDialog(@NonNull Context context, Song song) {
        super(context);
        mSong = song;
        Log.d("AddSongToPlaylistDialog", "onCreateView");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_song_to_playlist);
        initRecyclerViewPlaylists();
        mPresenter = new AddSongToPlaylistPresenter(this);
        mPresenter.onCreate();
        mPresenter.fetchAllPlaylists();
    }

    private void initRecyclerViewPlaylists(){
        mAdapterAddSongToPlaylist = new AddSongToPlaylistAdapter(this);
        mRecyclerViewAllPlaylists = (RecyclerView) findViewById(R.id.recyclerViewAllPlaylists);
        mRecyclerViewAllPlaylists.setAdapter(mAdapterAddSongToPlaylist);
        mRecyclerViewAllPlaylists.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void addPlaylists(ArrayList<Playlist> playlists){
        mAdapterAddSongToPlaylist.addPlaylists(playlists);
    }

    public void addSongToPlaylist(Playlist playlist){
        mPresenter.addSongToPlaylist(playlist, mSong);
    }
}
