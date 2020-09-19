package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {
    private int[] image;
    private ArrayList<String> title, singer, youtube_url;
    private ArrayList<Integer> views, likes;

    MusicHolder musicHolder;

    public MusicAdapter(int[] image, ArrayList<String> title, ArrayList<String> singer, ArrayList<String> youtube_url, ArrayList<Integer> views,  ArrayList<Integer>  likes) {
        this.image = image;
        this.title = title;
        this.singer = singer;
        this.youtube_url = youtube_url;
        this.views = views;
        this.likes = likes;



    }


    public static class MusicHolder extends RecyclerView.ViewHolder {
        public ImageView musicImageView;
        public TextView songTitleTextView, singerTextView, viewTextView, recommendTextView;

        public MusicHolder(View view) {
            super(view);
            this.musicImageView = view.findViewById(R.id.musicImageView);
            this.songTitleTextView = view.findViewById(R.id.songTitleTextView);
            this.singerTextView = view.findViewById(R.id.singerTextView);
            this.viewTextView = view.findViewById(R.id.viewTextview);
            this.recommendTextView = view.findViewById(R.id.recommendTextView);
        }
    }

    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item, parent, false);

        musicHolder = new MusicHolder(holderView);
        return musicHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
        holder.musicImageView.setImageResource(image[position]);
        holder.songTitleTextView.setText(title.get(position));
        holder.singerTextView.setText(singer.get(position));
        holder.recommendTextView.setText(String.valueOf(likes.get(position)));
        holder.viewTextView.setText(String.valueOf(views.get(position)));

        // 리스트뷰 아이템 클릭시, 음악 재생 화면(MusicPlayerActivity.java)로 전환.
        // TODO : 해당 아이템 youtube 읽어올 데이터 정의해서, playerIntent 랑 같이 전환하면 되겠습니다.
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO : 여기에 조회수 증가하는 로직 짜면 되겠습니다.
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


