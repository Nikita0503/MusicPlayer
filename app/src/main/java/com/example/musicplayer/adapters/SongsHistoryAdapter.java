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
import java.util.Collection;
import java.util.Collections;

public class SongsHistoryAdapter extends RecyclerView.Adapter<SongsHistoryAdapter.ViewHolder> {

    private ArrayList<Song> mSongs;
    private MainActivity mActivity;

    public SongsHistoryAdapter(FragmentActivity activity) {
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
        return new SongsHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsHistoryAdapter.ViewHolder holder, final int position) {
        holder.textViewInfo.setText(mSongs.get(/*mSongs.size() - position - 1*/ position).title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList songs = new ArrayList(mSongs);
                //Collections.reverse(songs);
                mActivity.showPlayerFragment(mSongs.get(/*mSongs.size() - position - 1*/ position), mSongs);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewInfo = (TextView) itemView.findViewById(R.id.info_text);
        }
    }
}
