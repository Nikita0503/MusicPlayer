package com.example.musicplayer.views;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapters.PlaylistsAdapter;
import com.example.musicplayer.adapters.SongsAdapter;
import com.example.musicplayer.models.data.Playlist;
import com.example.musicplayer.models.data.Song;
import com.example.musicplayer.presenters.MainPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private MainPresenter mPresenter;
    private PlaylistsAdapter mAdapterPlaylists;
    private SongsAdapter mAdapterSongsHistory;
    private RecyclerView mRecyclerViewPlaylists;
    private RecyclerView mRecyclerViewSongsHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.d("MainFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_main, null);
        mPresenter = new MainPresenter(this);
        mPresenter.onCreate();
        initViews(view);
        fetchSongsHistory();
        fetchAllPlaylists();
        return view;
    }

    private void initViews(View view){
        initRecyclerViewPlaylists(view);
        initRecyclerViewRecentlySongs(view);
        ImageView buttonOpenAllSongsScreen = (ImageView) view.findViewById(R.id.buttonOpenAllSongsScreen);
        buttonOpenAllSongsScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showAllSongsFragment();
            }
        });
        Button buttonClearHistory = (Button) view.findViewById(R.id.buttonClearHistory);
        buttonClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.clearHistory();
            }
        });
    }

    private void initRecyclerViewPlaylists(View view){
        mAdapterPlaylists = new PlaylistsAdapter(getActivity(), this);
        mRecyclerViewPlaylists = (RecyclerView) view.findViewById(R.id.recyclerViewPlaylists);
        mRecyclerViewPlaylists.setAdapter(mAdapterPlaylists);
        mRecyclerViewPlaylists.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void initRecyclerViewRecentlySongs(View view){
        mAdapterSongsHistory = new SongsAdapter(getActivity());
        mRecyclerViewSongsHistory = (RecyclerView) view.findViewById(R.id.recyclerViewRecentlySongs);
        mRecyclerViewSongsHistory.setAdapter(mAdapterSongsHistory);
        mRecyclerViewSongsHistory.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void fetchSongsHistory(){
        mPresenter.fetchSongsHistory();
    }

    public void setSongsHistory(ArrayList<Song> songsHistory){
        mAdapterSongsHistory.addSongs(songsHistory);
        mRecyclerViewSongsHistory.scrollToPosition(mAdapterSongsHistory.getItemCount() - 1);
    }

    public void scrollHistoryToEnd(){
        mRecyclerViewSongsHistory.scrollToPosition(mAdapterSongsHistory.getItemCount() - 1);
    }

    public void fetchAllPlaylists(){
        mPresenter.fetchAllPlaylists();
    }

    public void setPlaylists(ArrayList<Playlist> playlists){
        mAdapterPlaylists.addPlaylists(playlists);
    }

    public void addPlaylist(String title){
        mPresenter.addPlaylist(new Playlist(title, "[]"));
    }

    public void deletePlaylist(Playlist playlist){
        mPresenter.deletePlaylist(playlist);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mPresenter.onDestroy();
    }

}
