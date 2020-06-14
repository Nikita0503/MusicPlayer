package com.example.musicplayer.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.musicplayer.R;
import com.example.musicplayer.adapters.TabAllSongsAdapter;
import com.google.android.material.tabs.TabLayout;

public class AllSongsFragment extends Fragment {

    private TabLayout mTabLayoutAllSongs;
    private ViewPager mViewPagerAllSongs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.d("AllSongsFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_all_songs, null);
        //((MainActivity) getActivity()).showControllerFragment();
        initTabLayoutAllSongs(view);
        ImageView buttonBack = (ImageView) view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }

    private void initTabLayoutAllSongs(View view){
        mTabLayoutAllSongs = (TabLayout) view.findViewById(R.id.tabLayoutAllSongs);
        mViewPagerAllSongs = (ViewPager) view.findViewById(R.id.viewPagerAllSongs);
        mViewPagerAllSongs.setOffscreenPageLimit(2);
        TabAllSongsAdapter adapter = new TabAllSongsAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new SongsListFragment(), "Songs");
        adapter.addFragment(new RootFragmentBySingers(), "Singers");
        adapter.addFragment(new RootFragmentByAlbums(), "Albums");
        mViewPagerAllSongs.setAdapter(adapter);
        mTabLayoutAllSongs.setupWithViewPager(mViewPagerAllSongs);
    }
}
