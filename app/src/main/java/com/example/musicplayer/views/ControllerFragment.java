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

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.musicplayer.R;
import com.example.musicplayer.ServicePlayer;
import com.example.musicplayer.presenters.ControllerPresenter;

public class ControllerFragment extends Fragment {

    private boolean mBound = false;
    private ControllerPresenter mPresenter;
    private ServiceConnection mServiceConnection;
    private ServicePlayer mServicePlayer;
    private Intent mIntent;

    private ImageView mButtonPlay;
    private ImageView mButtonLooping;
    private SeekBar mSeekBarTrackProgress;
    private final Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("ControllerFragment", "onCreateView");
        mPresenter = new ControllerPresenter(this);
        mPresenter.onCreate();
        View view = inflater.inflate(R.layout.fragment_controller, null);
        initViews(view);
        initServiceConnection();
        return view;
    }

    private void initServiceConnection(){
        mIntent = new Intent(getActivity(), ServicePlayer.class);
        mServiceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d("ServicePlayer", "MainActivity onServiceConnected");
                mServicePlayer = ((ServicePlayer.MusicBinder) binder).getService();
                mBound = true;
                startUpdater();
            }
            public void onServiceDisconnected(ComponentName name) {
                Log.d("ServicePlayer", "MainActivity onServiceDisconnected");
                mBound = false;
            }
        };
        getActivity().bindService(mIntent, mServiceConnection, 0);
        getActivity().startService(mIntent);
    }

    private void initViews(View view){
        ImageView buttonOpenPlayer = (ImageView) view.findViewById(R.id.buttonOpenPlayer);
        buttonOpenPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //((MainActivity) getActivity()).showPlayerFragment(mServicePlayer.getCurrentSong(), mServicePlayer.getCurrentPlaylist());
                    ((MainActivity) getActivity()).showPlayerFragment();
                    ((MainActivity) getActivity()).hideControllerFragment();
                } catch (Exception c){
                    c.printStackTrace();
                }
            }
        });
        ImageView buttonPreviousSong = (ImageView) view.findViewById(R.id.buttonPreviousSong);
        buttonPreviousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mServicePlayer.playPreviousSong();
                    mPresenter.addSongToHistory(mServicePlayer.getCurrentSong());
                    startUpdater();
                } catch (Exception c){
                    c.printStackTrace();
                }
            }
        });
        ImageView buttonNextSong = (ImageView) view.findViewById(R.id.buttonNextSong);
        buttonNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mServicePlayer.playNextSong();
                    mPresenter.addSongToHistory(mServicePlayer.getCurrentSong());
                    startUpdater();
                } catch (Exception c){
                    c.printStackTrace();
                }
            }
        });
        mButtonPlay = (ImageView) view.findViewById(R.id.buttonPlay);
        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mServicePlayer.isPlaying()) {
                        mServicePlayer.pause();
                        startUpdater();
                    } else {
                        mServicePlayer.start();
                        startUpdater();
                    }
                } catch (Exception c){
                    c.printStackTrace();
                }
            }
        });
        mButtonLooping = (ImageView) view.findViewById(R.id.buttonRepeat);
        mButtonLooping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mServicePlayer.isLooping()) {
                        mServicePlayer.setLooping(false);
                    } else {
                        mServicePlayer.setLooping(true);
                    }
                } catch (Exception c){
                    c.printStackTrace();
                }
            }
        });
        mSeekBarTrackProgress = (SeekBar) view.findViewById(R.id.seekBarTrackProgress);
        mSeekBarTrackProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    mServicePlayer.seekTo(seekBar.getProgress());
                    startUpdater();
                } catch (Exception c){
                    c.printStackTrace();
                }
            }
        });
    }

    public void startUpdater() {
        if(mServicePlayer.getPlayer() != null) {
            updateViews();
            Runnable notification = new Runnable() {
                public void run() {
                    startUpdater();
                }
            };
            mHandler.postDelayed(notification, 500);
        }else{
            Runnable notification = new Runnable() {
                public void run() {
                    startUpdater();
                }
            };
            mHandler.postDelayed(notification, 1000);
        }
    }

    public void updateViews(){
        if(mServicePlayer.isPlaying()){
            Glide
                    .with(this)
                    .load(R.drawable.button_pause_mini)
                    .into(mButtonPlay);
        }else{
            Glide
                    .with(this)
                    .load(R.drawable.button_play_mini)
                    .into(mButtonPlay);
        }
        if(mServicePlayer.isLooping()){
            Glide
                    .with(this)
                    .load(R.drawable.button_replay_on_mini)
                    .into(mButtonLooping);
        }else{
            Glide
                    .with(this)
                    .load(R.drawable.button_replay_off_mini)
                    .into(mButtonLooping);
        }
        mSeekBarTrackProgress.setMax(mServicePlayer.getDuration());
        mSeekBarTrackProgress.setProgress(mServicePlayer.getCurrentPosition());
    }

    public void updateHistory(){
        ((MainActivity) getActivity()).updateHistory();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!mBound) return;
        getActivity().unbindService(mServiceConnection);
        mBound = false;
        Log.d("ControllerFragment", "onStop");
    }
}
