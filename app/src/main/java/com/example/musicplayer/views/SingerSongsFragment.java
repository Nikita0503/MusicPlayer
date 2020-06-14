package com.example.musicplayer.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapters.SongsAdapter;
import com.example.musicplayer.models.data.Singer;

public class SingerSongsFragment extends Fragment {

    private Singer mSinger;
    private SongsAdapter mAdapterAllSongs;
    private RecyclerView mRecyclerViewSongs;

    public SingerSongsFragment(Singer singer){
        mSinger = singer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("SingerSongsFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_singer_songs_list, null);
        ((TextView) view.findViewById(R.id.textViewSinger)).setText(mSinger.name);
        ImageView buttonBack = (ImageView) view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragment().getChildFragmentManager().popBackStack();
            }
        });
        initRecyclerViewSongs(view);
        return view;
    }

    private void initRecyclerViewSongs(View view){
        mAdapterAllSongs = new SongsAdapter(getActivity());
        mRecyclerViewSongs = (RecyclerView) view.findViewById(R.id.recyclerViewAllSongs);
        mRecyclerViewSongs.setAdapter(mAdapterAllSongs);
        mRecyclerViewSongs.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapterAllSongs.addSongs(mSinger.songs);
    }

}
