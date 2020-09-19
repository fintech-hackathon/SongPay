package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HorizontalMusicAdapter extends RecyclerView.Adapter<HorizontalMusicAdapter.MusicHolder> {
    private int[] image;
    private ArrayList<String> title, singer, youtube_url;
    private ArrayList<Integer> views, likes;

    MusicHolder musicHolder;

    public HorizontalMusicAdapter(int[] image, ArrayList<String> title, ArrayList<String> singer, ArrayList<String> youtube_url,ArrayList<Integer> views,  ArrayList<Integer>  likes) {
        this.image = image;
        this.title = title;
        this.singer = singer;
        this.youtube_url = youtube_url;
        this.views = views;
        this.likes = likes;
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
        holder.songTitleTextView.setText(this.title.get(position));
        holder.singerTextView.setText(this.singer.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent playerIntent = new Intent(context, MusicPlayerActivity.class);
                playerIntent.putStringArrayListExtra("title", title);
                playerIntent.putStringArrayListExtra("singer", singer);
                playerIntent.putStringArrayListExtra("link", youtube_url);
                playerIntent.putIntegerArrayListExtra("likes", likes);
                playerIntent.putIntegerArrayListExtra("views", views);
                playerIntent.putExtra("position", position);
                context.startActivity(playerIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.size();
    }
}


