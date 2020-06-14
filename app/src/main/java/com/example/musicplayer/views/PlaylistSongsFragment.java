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
import com.example.musicplayer.models.data.Album;
import com.example.musicplayer.models.data.Playlist;

public class PlaylistSongsFragment extends Fragment {

    private Playlist mPlaylist;
    private SongsAdapter mAdapterAllSongs;
    private RecyclerView mRecyclerViewAllSongs;

    public PlaylistSongsFragment(Playlist playlist) {
        mPlaylist = playlist;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("PlaylistSongsFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_playlist_songs_list, null);
        //((MainActivity) getActivity()).showControllerFragment();
        initRecyclerViewAllSongs(view);
        ImageView buttonBack = (ImageView) view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getParentFragment() != null){
                    getParentFragment().getChildFragmentManager().popBackStack();
                }else{
                    getActivity().getSupportFragmentManager().popBackStack();
                }

            }
        });
        TextView textView = (TextView) view.findViewById(R.id.textViewPlaylistName);
        textView.setText(mPlaylist.title);
        return view;
    }

    private void initRecyclerViewAllSongs(View view){
        mAdapterAllSongs = new SongsAdapter(getActivity());
        mAdapterAllSongs.addSongs(mPlaylist.songs);
        mRecyclerViewAllSongs = (RecyclerView) view.findViewById(R.id.recyclerViewAllSongs);
        mRecyclerViewAllSongs.setAdapter(mAdapterAllSongs);
        mRecyclerViewAllSongs.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
