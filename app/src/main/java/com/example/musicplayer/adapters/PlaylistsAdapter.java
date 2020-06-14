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
import com.example.musicplayer.dialogs.NewPlaylistDialog;
import com.example.musicplayer.models.data.Album;
import com.example.musicplayer.models.data.Playlist;
import com.example.musicplayer.models.data.Song;
import com.example.musicplayer.views.MainActivity;
import com.example.musicplayer.views.MainFragment;

import java.util.ArrayList;

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.ViewHolder> {

    private ArrayList<Playlist> mPlaylists;
    private MainFragment mFragment;
    private MainActivity mActivity;

    public PlaylistsAdapter(FragmentActivity activity, MainFragment fragment) {
        mActivity = (MainActivity) activity;
        mFragment = fragment;
        mPlaylists = new ArrayList<Playlist>();
        mPlaylists.add(new Playlist("1", ""));
    }

    public void addPlaylists(ArrayList<Playlist> playlists){
        mPlaylists.clear();
        mPlaylists = new ArrayList<Playlist>();
        mPlaylists.add(new Playlist("2", ""));
        mPlaylists.addAll(playlists);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_playlists, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if(position == 0){
            holder.textViewInfo.setText("Add new");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewPlaylistDialog dialog = new NewPlaylistDialog(mFragment.getContext(), mFragment);
                    dialog.show();
                }
            });
        }else {
            holder.textViewInfo.setText(mPlaylists.get(position).title);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.showPlaylistSongsFragment(mPlaylists.get(position));
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mFragment.deletePlaylist(mPlaylists.get(position));
                    return false;
                }
            });
        }
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
