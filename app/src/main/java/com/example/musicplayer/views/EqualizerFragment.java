package com.example.musicplayer.views;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.musicplayer.R;
import com.example.musicplayer.ServicePlayer;
import com.example.musicplayer.models.data.Song;
import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

public class EqualizerFragment extends Fragment {

    private Song mSong;
    private ServicePlayer mServicePlayer;

    private TextView mTextViewEqualizer1;
    private TextView mTextViewEqualizer2;
    private TextView mTextViewEqualizer3;
    private TextView mTextViewEqualizer4;
    private TextView mTextViewEqualizer5;
    private SeekBar mSeekBarEqualizer1;
    private SeekBar mSeekBarEqualizer2;
    private SeekBar mSeekBarEqualizer3;
    private SeekBar mSeekBarEqualizer4;
    private SeekBar mSeekBarEqualizer5;
    private Croller mCrollerBassBoost;
    private Croller mCrollerBassVirtualizer;
    private final Handler mHandler = new Handler();

    public EqualizerFragment(ServicePlayer servicePlayer) {
        mServicePlayer = servicePlayer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("EqualizerFragment", "onCreateView1");
        View view = inflater.inflate(R.layout.fragment_equalizer, null);
        //((MainActivity) getActivity()).hideControllerFragment();
        ((MainActivity) getActivity()).hidePlayerFragment();
        ImageView buttonBack = (ImageView) view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showPlayerFragment();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        initViews(view);
        return view;
    }

    private void initViews(View view){
        mTextViewEqualizer1 = (TextView) view.findViewById(R.id.textViewEqualizer1);
        mTextViewEqualizer2 = (TextView) view.findViewById(R.id.textViewEqualizer2);
        mTextViewEqualizer3 = (TextView) view.findViewById(R.id.textViewEqualizer3);
        mTextViewEqualizer4 = (TextView) view.findViewById(R.id.textViewEqualizer4);
        mTextViewEqualizer5 = (TextView) view.findViewById(R.id.textViewEqualizer5);
        mSeekBarEqualizer1 = (SeekBar) view.findViewById(R.id.seekBarEqualizer1);
        mSeekBarEqualizer1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mServicePlayer.setBandLevel(0, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarEqualizer2 = (SeekBar) view.findViewById(R.id.seekBarEqualizer2);
        mSeekBarEqualizer2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mServicePlayer.setBandLevel(1, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarEqualizer3 = (SeekBar) view.findViewById(R.id.seekBarEqualizer3);
        mSeekBarEqualizer3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mServicePlayer.setBandLevel(2, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarEqualizer4 = (SeekBar) view.findViewById(R.id.seekBarEqualizer4);
        mSeekBarEqualizer4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mServicePlayer.setBandLevel(3, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarEqualizer5 = (SeekBar) view.findViewById(R.id.seekBarEqualizer5);
        mSeekBarEqualizer5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mServicePlayer.setBandLevel(4, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mCrollerBassBoost = (Croller) view.findViewById(R.id.crollerBassBoost);
        mCrollerBassBoost.setProgress(0);
        mCrollerBassBoost.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                mServicePlayer.setStrengthBassBooster(progress);
            }

            @Override
            public void onStartTrackingTouch(Croller croller) {

            }

            @Override
            public void onStopTrackingTouch(Croller croller) {

            }
        });
        mCrollerBassVirtualizer = (Croller) view.findViewById(R.id.crollerVirtualizer);
        mCrollerBassVirtualizer.setProgress(0);
        mCrollerBassVirtualizer.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                mServicePlayer.setStrengthVirtualizer(progress);
            }

            @Override
            public void onStartTrackingTouch(Croller croller) {

            }

            @Override
            public void onStopTrackingTouch(Croller croller) {

            }
        });
        mTextViewEqualizer1.setText(mServicePlayer.getCenterFreq(0));
        mTextViewEqualizer2.setText(mServicePlayer.getCenterFreq(1));
        mTextViewEqualizer3.setText(mServicePlayer.getCenterFreq(2));
        mTextViewEqualizer4.setText(mServicePlayer.getCenterFreq(3));
        mTextViewEqualizer5.setText(mServicePlayer.getCenterFreq(4));
        mSeekBarEqualizer1.setMin(mServicePlayer.getBandLevelRangeMin());
        mSeekBarEqualizer1.setMax(mServicePlayer.getBandLevelRangeMax());
        mSeekBarEqualizer2.setMin(mServicePlayer.getBandLevelRangeMin());
        mSeekBarEqualizer2.setMax(mServicePlayer.getBandLevelRangeMax());
        mSeekBarEqualizer3.setMin(mServicePlayer.getBandLevelRangeMin());
        mSeekBarEqualizer3.setMax(mServicePlayer.getBandLevelRangeMax());
        mSeekBarEqualizer4.setMin(mServicePlayer.getBandLevelRangeMin());
        mSeekBarEqualizer4.setMax(mServicePlayer.getBandLevelRangeMax());
        mSeekBarEqualizer5.setMin(mServicePlayer.getBandLevelRangeMin());
        mSeekBarEqualizer5.setMax(mServicePlayer.getBandLevelRangeMax());
        mSeekBarEqualizer1.setProgress(0);
        mSeekBarEqualizer2.setProgress(0);
        mSeekBarEqualizer3.setProgress(0);
        mSeekBarEqualizer4.setProgress(0);
        mSeekBarEqualizer5.setProgress(0);
        startUpdater();
    }


    public void startUpdater() {
        updateViews();
        Runnable notification = new Runnable() {
            public void run() {
                startUpdater();
            }
        };
        mHandler.postDelayed(notification, 500);
    }

    public void updateViews(){
        if(mSong != null){
            if(mSong.id != mServicePlayer.getCurrentSong().id){
                mSeekBarEqualizer1.setProgress(mServicePlayer.getBandLevel(0));
                mSeekBarEqualizer2.setProgress(mServicePlayer.getBandLevel(1));
                mSeekBarEqualizer3.setProgress(mServicePlayer.getBandLevel(2));
                mSeekBarEqualizer4.setProgress(mServicePlayer.getBandLevel(3));
                mSeekBarEqualizer5.setProgress(mServicePlayer.getBandLevel(4));
                mCrollerBassBoost.setProgress(mServicePlayer.getStrengthBassBooster());
                mCrollerBassVirtualizer.setProgress(mServicePlayer.getStrengthVirtualizer());
                mSong = mServicePlayer.getCurrentSong();
            }
        }else{
            mSong = mServicePlayer.getCurrentSong();
        }
    }
}
