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
import com.example.musicplayer.adapters.AlbumsAdapter;
import com.example.musicplayer.models.data.Album;
import com.example.musicplayer.presenters.AllAlbumsPresenter;

import java.util.ArrayList;

public class AllAlbumsFragment extends Fragment {

    private AllAlbumsPresenter mPresenter;
    private AlbumsAdapter mAdapterAllAlbums;
    private RecyclerView mRecyclerViewAlbums;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("AllAlbumsFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_all_albums, null);
        mPresenter = new AllAlbumsPresenter(this);
        mPresenter.onCreate();
        mPresenter.fetchAlbumsList();
        initRecyclerViewAlbums(view);
        return view;
    }

    private void initRecyclerViewAlbums(View view) {
        mAdapterAllAlbums = new AlbumsAdapter(getParentFragment());
        mRecyclerViewAlbums = (RecyclerView) view.findViewById(R.id.recyclerViewAllAlbums);
        mRecyclerViewAlbums.setAdapter(mAdapterAllAlbums);
        mRecyclerViewAlbums.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void addAlbums(ArrayList<Album> albums){
        mAdapterAllAlbums.addAlbums(albums);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
