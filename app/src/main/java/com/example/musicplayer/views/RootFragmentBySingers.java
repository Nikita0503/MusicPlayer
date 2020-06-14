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
import com.example.musicplayer.models.data.Singer;

public class RootFragmentBySingers extends Fragment {

    private FragmentTransaction mFragmentTransaction;
    private FrameLayout mFrameLayoutRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("RootFragmentBySingers", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_root, null);
        mFrameLayoutRoot = (FrameLayout) view.findViewById(R.id.rootFragmentBy);
        showAllSingersFragment();
        return view;
    }

    public void showAllSingersFragment(){
        mFragmentTransaction = getChildFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.rootFragmentBy, new AllSingersFragment());
        mFragmentTransaction.commit();
    }

    public void showSingerSongsFragment(Singer singer){
        mFragmentTransaction = getChildFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.rootFragmentBy, new SingerSongsFragment(singer));
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }
}
