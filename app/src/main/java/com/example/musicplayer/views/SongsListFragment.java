package com.example.musicplayer.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapters.SongsAdapter;
import com.example.musicplayer.models.data.Song;
import com.example.musicplayer.presenters.SongsListPresenter;

import java.util.ArrayList;

public class SongsListFragment extends Fragment {

    private SongsListPresenter mPresenter;
    private SongsAdapter mAdapterAllSongs;
    private RecyclerView mRecyclerViewSongs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("SongsListFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_songs_list, null);
        mPresenter = new SongsListPresenter(this);
        mPresenter.onCreate();
        mPresenter.fetchSongsList();
        initRecyclerViewSongs(view);
        return view;
    }

    private void initRecyclerViewSongs(View view){
        mAdapterAllSongs = new SongsAdapter(getActivity());
        mRecyclerViewSongs = (RecyclerView) view.findViewById(R.id.recyclerViewAllSongs);
        mRecyclerViewSongs.setAdapter(mAdapterAllSongs);
        mRecyclerViewSongs.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void addSongs(ArrayList<Song> songs){
        mAdapterAllSongs.addSongs(songs);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
