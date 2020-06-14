package com.example.musicplayer.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicplayer.R;
import com.example.musicplayer.models.data.Album;

public class RootFragmentByAlbums extends Fragment {

    private FragmentTransaction mFragmentTransaction;
    private FrameLayout mFrameLayoutRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("RootFragmentByAlbums", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_root, null);
        mFrameLayoutRoot = (FrameLayout) view.findViewById(R.id.rootFragmentBy);
        showAllAlbumsFragment();
        return view;
    }

    public void showAllAlbumsFragment(){
        mFragmentTransaction = getChildFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.rootFragmentBy, new AllAlbumsFragment());
        mFragmentTransaction.commit();
    }

    public void showAlbumSongsFragment(Album album){
        mFragmentTransaction = getChildFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.rootFragmentBy, new AlbumSongsFragment(album));
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

}
