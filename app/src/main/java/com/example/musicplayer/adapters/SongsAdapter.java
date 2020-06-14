package com.example.musicplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.models.data.Song;
import com.example.musicplayer.views.MainActivity;

import java.util.ArrayList;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {

    private ArrayList<Song> mSongs;
    private MainActivity mActivity;

    public SongsAdapter(FragmentActivity activity) {
        mActivity = (MainActivity) activity;
        mSongs = new ArrayList<Song>();
    }

    public void addSongs(ArrayList<Song> songs){
        mSongs.clear();
        mSongs.addAll(songs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_songs, parent, false);
        return new SongsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsAdapter.ViewHolder holder, final int position) {
        holder.textViewInfo.setText(mSongs.get(position).title);
        holder.textViewSinger.setText(mSongs.get(position).singer);
        holder.textViewDuration.setText(getDuration(mSongs.get(position).duration));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.showPlayerFragment(mSongs.get(position), mSongs);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewInfo;
        TextView textViewSinger;
        TextView textViewDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewInfo = (TextView) itemView.findViewById(R.id.info_text);
            textViewSinger = (TextView) itemView.findViewById(R.id.textViewSinger);
            textViewDuration = (TextView) itemView.findViewById(R.id.textViewDuration);
        }
    }
}
