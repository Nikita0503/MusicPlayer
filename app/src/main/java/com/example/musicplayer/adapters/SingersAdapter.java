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
import com.example.musicplayer.models.data.Singer;
import com.example.musicplayer.views.RootFragmentBySingers;

import java.util.ArrayList;

public class SingersAdapter extends RecyclerView.Adapter<SingersAdapter.ViewHolder> {

    private ArrayList<Singer> mSingers;
    private RootFragmentBySingers mFragment;

    public SingersAdapter(Fragment fragment){
        mFragment = (RootFragmentBySingers) fragment;
        mSingers = new ArrayList<Singer>();
    }

    public void addSingers(ArrayList<Singer> singers){
        mSingers.addAll(singers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_singer, parent, false);
        return new SingersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textViewInfo.setText(mSingers.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment.showSingerSongsFragment(mSingers.get(position));
                for(int i = 0; i < mSingers.get(position).songs.size(); i++){
                    Log.d("SingersSongs", mSingers.get(position).songs.get(i).title);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSingers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewInfo = (TextView) itemView.findViewById(R.id.info_text);
        }
    }
}
