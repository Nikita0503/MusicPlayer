package com.example.musicplayer.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class TabAllSongsAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTitles;

    public TabAllSongsAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        mFragments = new ArrayList<Fragment>();
        mTitles = new ArrayList<String>();
    }

    public void addFragment(Fragment fragment, String title){
        mFragments.add(fragment);
        mTitles.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
