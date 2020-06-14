package com.example.musicplayer.views;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapters.SingersAdapter;
import com.example.musicplayer.models.data.Singer;
import com.example.musicplayer.presenters.AllSingersPresenter;

import java.util.ArrayList;

public class AllSingersFragment extends Fragment {

    private AllSingersPresenter mPresenter;
    private SingersAdapter mAdapterAllSingers;
    private RecyclerView mRecyclerViewAllSingers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("AllSingersFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_all_singers, null);
        mPresenter = new AllSingersPresenter(this);
        mPresenter.onCreate();
        mPresenter.fetchSingersList();
        initRecyclerViewSingers(view);
        return view;
    }

    private void initRecyclerViewSingers(View view) {
        mAdapterAllSingers = new SingersAdapter(getParentFragment());
        mRecyclerViewAllSingers = (RecyclerView) view.findViewById(R.id.recyclerViewAllSingers);
        mRecyclerViewAllSingers.setAdapter(mAdapterAllSingers);
        mRecyclerViewAllSingers.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void addSingers(ArrayList<Singer> singers){
        mAdapterAllSingers.addSingers(singers);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
