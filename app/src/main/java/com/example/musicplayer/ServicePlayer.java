package com.example.musicplayer;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Virtualizer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;


import com.example.musicplayer.models.data.Song;

import java.util.ArrayList;

public class ServicePlayer extends Service {

    private boolean mIsLooping;
    private Song mCurrentSong;
    private ArrayList<Song> mPlaylist;
    private MusicBinder binder = new MusicBinder();
    private MediaPlayer mMediaPlayer;
    private Equalizer mEqualizer;
    private BassBoost mBassBoost;
    private Virtualizer mVirtualizer;

    public void onCreate() {
        super.onCreate();
        Log.d("ServicePlayer", "ServicePlayer onCreate");
    }

    public void setPlaylist(ArrayList<Song> playlist){
        mPlaylist = playlist;
    }

    public void playSong(Song song){
        mCurrentSong = song;
        releaseMediaPlayer();
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playNextSong();
                }
            });
            mMediaPlayer.setLooping(mIsLooping);
            mMediaPlayer.setDataSource(getApplicationContext(), ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song.id));
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepare();
            mMediaPlayer.start();

            mEqualizer = new Equalizer(1, mMediaPlayer.getAudioSessionId());
            mEqualizer.setEnabled(true);
            mEqualizer.setBandLevel((short) 0, (short) 0);
            mEqualizer.setBandLevel((short) 1, (short) 0);
            mEqualizer.setBandLevel((short) 2, (short) 0);
            mEqualizer.setBandLevel((short) 3, (short) 0);
            mEqualizer.setBandLevel((short) 4, (short) 0);

            mBassBoost = new BassBoost(2, mMediaPlayer.getAudioSessionId());

            mVirtualizer = new Virtualizer(3, mMediaPlayer.getAudioSessionId());
            mVirtualizer.setEnabled(true);
            mVirtualizer.setStrength((short) 0);
        } catch (Exception c){
            c.printStackTrace();
        }
    }

    public void playPreviousSong(){
        int currentSongIndex = getCurrentSongIndex();
        currentSongIndex--;
        if(currentSongIndex < 0){
            currentSongIndex = mPlaylist.size() - 1;
        }
        playSong(mPlaylist.get(currentSongIndex));
    }

    public void playNextSong(){
        int currentSongIndex = getCurrentSongIndex();
        currentSongIndex++;
        if(currentSongIndex == mPlaylist.size()){
            currentSongIndex = 0;
        }
        playSong(mPlaylist.get(currentSongIndex));
    }

    public int getCurrentSongIndex(){
        int currentSongIndex = 0;
        for(int i = 0; i < mPlaylist.size(); i++){
            if(mPlaylist.get(i).id == mCurrentSong.id){
                currentSongIndex = i;
                break;
            }
        }
        return currentSongIndex;
    }

    public void pause(){
        mMediaPlayer.pause();
    }

    public void start(){
        mMediaPlayer.start();
    }

    public void seekTo(int progress){
        mMediaPlayer.seekTo(progress);
    }

    public void setLooping(boolean isLooping){
        mIsLooping = isLooping;
        mMediaPlayer.setLooping(isLooping);
    }

    public int getCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }

    public boolean isLooping(){
        return mIsLooping;
    }

    public int getDuration(){
        return mMediaPlayer.getDuration();
    }

    public Song getCurrentSong(){
        return mCurrentSong;
    }

    public ArrayList<Song> getCurrentPlaylist(){
        return mPlaylist;
    }

    public MediaPlayer getPlayer(){
        return mMediaPlayer;
    }

    public boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }

    public int getBandLevelRange(int number){
        return mEqualizer.getBandLevelRange()[number];
    }

    public void setBandLevel(int number, int level){
        mEqualizer.setBandLevel((short) number, (short) level);
    }

    public int getBandLevel(int number){
        return mEqualizer.getBandLevel((short) number);
    }

    public int getStrengthBassBooster(){
        return mBassBoost.getRoundedStrength();
    }

    public void setStrengthBassBooster(int strength){
        if(strength > 1){
            mBassBoost.setEnabled(true);
            mBassBoost.setStrength((short) strength);
        }else{
            mBassBoost.setEnabled(false);
        }

    }

    public int getStrengthVirtualizer(){
        return mVirtualizer.getRoundedStrength();
    }

    public void setStrengthVirtualizer(int strength){
        mVirtualizer.setStrength((short) strength);
    }

    public String getCenterFreq(int number){
        return String.valueOf(mEqualizer.getCenterFreq((short) number) / 1000) + " Hz";
    }

    public int getBandLevelRangeMin(){
        return mEqualizer.getBandLevelRange()[0];
    }

    public int getBandLevelRangeMax(){
        return mEqualizer.getBandLevelRange()[1];
    }

    public void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mEqualizer != null){
            try {
                mEqualizer.release();
                mEqualizer = null;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        if (mBassBoost != null){
            try {
                mBassBoost.release();
                mBassBoost = null;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        if (mVirtualizer != null){
            try {
                mVirtualizer.release();
                mVirtualizer = null;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ServicePlayer", "ServicePlayer onBind");
        return binder;
    }

    public class MusicBinder extends Binder {
        public ServicePlayer getService() {
            return ServicePlayer.this;
        }
    }
}
