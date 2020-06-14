package com.example.musicplayer.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.musicplayer.R;
import com.example.musicplayer.ServicePlayer;
import com.example.musicplayer.models.data.Album;
import com.example.musicplayer.models.data.Playlist;
import com.example.musicplayer.models.data.Song;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction mFragmentTransaction;
    private FrameLayout mFrameLayoutController;
    private FrameLayout mFrameLayoutPlayer;
    private MainFragment mMainFragment;
    private PlayerFragment mPlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFrameLayoutController = (FrameLayout) findViewById(R.id.controllerSongFragment);
        mFrameLayoutPlayer = (FrameLayout) findViewById(R.id.playerFragment);
        requestPermissions();
    }

    public void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else {
            showMainFragment();
            mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragmentTransaction.replace(R.id.controllerSongFragment, new ControllerFragment());
            //mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
            mPlayerFragment = new PlayerFragment();
            mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragmentTransaction.replace(R.id.playerFragment, mPlayerFragment);
            //mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
        }
    }

    public void hideControllerFragment(){
        mFrameLayoutController.setVisibility(View.GONE);
    }

    public void showControllerFragment(){
        mFrameLayoutController.setVisibility(View.VISIBLE);
        mMainFragment.scrollHistoryToEnd();
    }

    public void hidePlayerFragment(){
        mFrameLayoutPlayer.setVisibility(View.GONE);
    }

    public void showPlayerFragment(){
        mFrameLayoutPlayer.setVisibility(View.VISIBLE);
    }

    public void showMainFragment(){
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mMainFragment = new MainFragment();
        mFragmentTransaction.replace(R.id.rootFragment, mMainFragment);
        mFragmentTransaction.commit();
    }


    public void showAllSongsFragment(){
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.rootFragment, new AllSongsFragment());
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    public void updateHistory(){
        mMainFragment.fetchSongsHistory();
    }

    public void showAlbumSongsFragment(Album album){
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.rootFragment, new AlbumSongsFragment(album));
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    public void showPlaylistSongsFragment(Playlist playlist){
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.rootFragment, new PlaylistSongsFragment(playlist));
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    public void showPlayerFragment(Song song, ArrayList<Song> playlist){
        mPlayerFragment.setPlaylist(playlist);
        mPlayerFragment.setSong(song);
        mPlayerFragment.playSong();
        hideControllerFragment();
        showPlayerFragment();
    }

    public void showEqualizerFragment(ServicePlayer servicePlayer){
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.rootFragment, new EqualizerFragment(servicePlayer));
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    @Override
    public void onBackPressed()
    {
        if(mFrameLayoutPlayer.getVisibility() == View.GONE && mFrameLayoutController.getVisibility() == View.GONE){
            showPlayerFragment();
            super.onBackPressed();
        }
        else if(mFrameLayoutPlayer.getVisibility() == View.GONE){
            super.onBackPressed();
        }
        else if(mFrameLayoutPlayer.getVisibility() == View.VISIBLE){
            hidePlayerFragment();
            showControllerFragment();
        }


        //super.onBackPressed();
    }
}
