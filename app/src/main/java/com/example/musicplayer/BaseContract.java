package com.example.musicplayer;

public interface BaseContract {
    
    public interface View {

    }

    public interface Presenter {
        public void onCreate();
        public void onDestroy();
    }
}
