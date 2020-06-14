package com.example.musicplayer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.models.data.Album;
import com.example.musicplayer.views.RootFragmentByAlbums;

import java.util.ArrayList;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {

    private ArrayList<Album> mAlbums;
    private RootFragmentByAlbums mFragment;

    public AlbumsAdapter(Fragment fragment){
        mFragment = (RootFragmentByAlbums) fragment;
        mAlbums = new ArrayList<Album>();
    }

    public void addAlbums(ArrayList<Album> albums){
        mAlbums.addAll(albums);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_album, parent, false);
        return new AlbumsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textViewInfo.setText(mAlbums.get(position).title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment.showAlbumSongsFragment(mAlbums.get(position));
                for(int i = 0; i < mAlbums.get(position).songs.size(); i++){
                    Log.d("AlbumSongs", mAlbums.get(position).songs.get(i).title);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewInfo = (TextView) itemView.findViewById(R.id.info_text);
        }
    }
}
