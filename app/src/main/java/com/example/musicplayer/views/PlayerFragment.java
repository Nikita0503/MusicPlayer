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

import com.bumptech.glide.Glide;
import com.example.musicplayer.R;
import com.example.musicplayer.ServicePlayer;
import com.example.musicplayer.dialogs.AddSongToPlaylistDialog;
import com.example.musicplayer.models.data.Song;
import com.example.musicplayer.models.database.JSONConverter;
import com.example.musicplayer.presenters.PlayerPresenter;

import java.util.ArrayList;

public class PlayerFragment extends Fragment {

    private Song mSong;
    private PlayerPresenter mPresenter;
    private ArrayList<Song> mPlaylist;
    private boolean mBound = false;
    private ServiceConnection mServiceConnection;
    private ServicePlayer mServicePlayer;
    private Intent mIntent;

    private TextView mTextViewSongName;
    private TextView mTextViewSinger;
    private TextView mTextViewCurrentProgress;
    private TextView mTextViewFullProgress;
    private ImageView mButtonPlay;
    private ImageView mButtonLooping;
    private SeekBar mSeekBarTrackProgress;
    private final Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("PlayerFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_player, null);
        mPresenter = new PlayerPresenter(this);
        mPresenter.onCreate();
        initViews(view);
        initServiceConnection();
        return view;
    }

    public void setSong(Song song){
        mSong = song;
    }

    public void setPlaylist(ArrayList<Song> playlist){
        mPlaylist = playlist;
    }

    public void playSong(){
        if(mServicePlayer.getCurrentSong() != null){
            if(mSong.id != mServicePlayer.getCurrentSong().id) {
                mServicePlayer.setPlaylist(mPlaylist);
                mServicePlayer.playSong(mSong);
                mPresenter.addSongToHistory(mSong);
            }
        }else{
            mServicePlayer.setPlaylist(mPlaylist);
            mServicePlayer.playSong(mSong);
            mPresenter.addSongToHistory(mSong);
        }

        //mServicePlayer.setPlaylist(mPlaylist);
        //mServicePlayer.playSong(mSong);
        //mPresenter.addSongToHistory(mSong);
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
        mTextViewSongName = (TextView) view.findViewById(R.id.textViewSongName);
        mTextViewSinger = (TextView) view.findViewById(R.id.textViewSinger);
        ImageView buttonBack = (ImageView) view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().getSupportFragmentManager().popBackStack();
                ((MainActivity) getActivity()).hidePlayerFragment();
                ((MainActivity) getActivity()).showControllerFragment();
            }
        });
        ImageView buttonEqualizer = (ImageView) view.findViewById(R.id.buttonEqualizer);
        buttonEqualizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showEqualizerFragment(mServicePlayer);
            }
        });
        ImageView buttonAddToPlaylist = (ImageView) view.findViewById(R.id.buttonAddToPlaylist);
        buttonAddToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSongToPlaylistDialog dialog = new AddSongToPlaylistDialog(getContext(), mSong);
                dialog.show();
            }
        });
        ImageView buttonPreviousSong = (ImageView) view.findViewById(R.id.buttonPreviousSong);
        buttonPreviousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServicePlayer.playPreviousSong();
                mSong = mServicePlayer.getCurrentSong();
                mPresenter.addSongToHistory(mSong);
                startUpdater();
            }
        });
        ImageView buttonNextSong = (ImageView) view.findViewById(R.id.buttonNextSong);
        buttonNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServicePlayer.playNextSong();
                mSong = mServicePlayer.getCurrentSong();
                mPresenter.addSongToHistory(mSong);
                startUpdater();
            }
        });
        mButtonPlay = (ImageView) view.findViewById(R.id.buttonPlay);
        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mServicePlayer.isPlaying()){
                    mServicePlayer.pause();
                    startUpdater();
                }else{
                    mServicePlayer.start();
                    startUpdater();
                }
            }
        });
        mButtonLooping = (ImageView) view.findViewById(R.id.buttonRepeat);
        mButtonLooping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mServicePlayer.isLooping()){
                    mServicePlayer.setLooping(false);
                }else {
                    mServicePlayer.setLooping(true);
                }
            }
        });
        mTextViewCurrentProgress = (TextView) view.findViewById(R.id.textViewCurrentProgress);
        mTextViewFullProgress = (TextView) view.findViewById(R.id.textViewFullProgress);
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
                mServicePlayer.seekTo(seekBar.getProgress());
                startUpdater();
            }
        });
    }

    public void startUpdater() {
        /*updateViews();
        //if (mServicePlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    Log.d("asd", "qwe");
                    startUpdater();
                }
            };
        mHandler.postDelayed(notification,500);*/

        if(mServicePlayer.getPlayer() != null) {
            updateViews();
            Runnable notification = new Runnable() {
                public void run() {
                    startUpdater();
                }
            };
            mHandler.postDelayed(notification, 100);
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
                    .load(R.drawable.button_pause_player)
                    .into(mButtonPlay);
        }else{
            Glide
                    .with(this)
                    .load(R.drawable.button_play_player)
                    .into(mButtonPlay);
        }
        if(mServicePlayer.isLooping()){
            Glide
                    .with(this)
                    .load(R.drawable.button_replay_on)
                    .into(mButtonLooping);
        }else{
            Glide
                    .with(this)
                    .load(R.drawable.button_replay_off)
                    .into(mButtonLooping);
        }
        mSeekBarTrackProgress.setMax(mServicePlayer.getDuration());
        mSeekBarTrackProgress.setProgress(mServicePlayer.getCurrentPosition());
        mTextViewCurrentProgress.setText(String.valueOf(getDuration(mServicePlayer.getCurrentPosition())));
        mTextViewFullProgress.setText(String.valueOf(getDuration(mServicePlayer.getDuration())));
        mTextViewSongName.setText(mServicePlayer.getCurrentSong().title);
        mTextViewSinger.setText(mServicePlayer.getCurrentSong().singer);
    }

    public String getDuration(long duration) {
        duration = duration/1000;
        long hour = duration/3600;
        long min = duration/60 % 60;
        long sec = duration/ 1 % 60;
        if(hour<1){
            if(min<10){
                return String.format("%2d:%02d", min, sec);
            }else{
                return String.format("%02d:%02d", min, sec);
            }
        }else{
            if(hour<10){
                return String.format("%2d:%02d:%02d", hour, min, sec);
            }else{
                return String.format("%02d:%02d:%02d", hour, min, sec);
            }

        }
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
        Log.d("PlayerFragment", "onStop");
    }
}
