package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalMusicAdapter extends RecyclerView.Adapter<HorizontalMusicAdapter.MusicHolder> {
    private int[] image;
    private String[] title,singer;

    MusicHolder musicHolder;

    public HorizontalMusicAdapter(int[] image, String[] title,String[] singer){
        this.image = image;
        this.title = title;
        this.singer = singer;
    }


    public static class MusicHolder extends RecyclerView.ViewHolder{
        public ImageView musicImageView;
        public TextView songTitleTextView,singerTextView;

        public MusicHolder(View view){
            super(view);
            this.musicImageView = view.findViewById(R.id.musicImageView);
            this.songTitleTextView = view.findViewById(R.id.songTitleTextView);
            this.singerTextView = view.findViewById(R.id.singerTextView);
        }
    }

    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_music_item, parent, false);

        musicHolder = new MusicHolder(holderView);
        return musicHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
        holder.musicImageView.setImageResource(this.image[position]);
        holder.songTitleTextView.setText(this.title[position]);
        holder.singerTextView.setText(this.singer[position]);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}


