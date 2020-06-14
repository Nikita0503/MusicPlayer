package com.example.musicplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.dialogs.AddSongToPlaylistDialog;
import com.example.musicplayer.models.data.Playlist;
import com.example.musicplayer.views.PlayerFragment;

import java.util.ArrayList;

public class AddSongToPlaylistAdapter extends RecyclerView.Adapter<AddSongToPlaylistAdapter.ViewHolder> {

    private ArrayList<Playlist> mPlaylists;
    private AddSongToPlaylistDialog mDialog;

    public AddSongToPlaylistAdapter(AddSongToPlaylistDialog dialog)
    {
        mDialog = dialog;
        mPlaylists = new ArrayList<Playlist>();
    }

    public void addPlaylists(ArrayList<Playlist> playlists){
        mPlaylists.clear();
        mPlaylists.addAll(playlists);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_playlist_title, parent, false);
        return new AddSongToPlaylistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewInfo.setText(mPlaylists.get(position).title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.addSongToPlaylist(mPlaylists.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaylists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewInfo = (TextView) itemView.findViewById(R.id.info_text);
        }
    }
}
