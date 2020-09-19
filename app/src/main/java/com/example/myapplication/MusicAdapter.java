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

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {
    private int[] image;
    private String[] title, singer, youtube_url;

    MusicHolder musicHolder;

    public MusicAdapter(int[] image, String[] title, String[] singer, String[] youtube_url) {
        this.image = image;
        this.title = title;
        this.singer = singer;
        this.youtube_url = youtube_url;
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
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item, parent, false);

        musicHolder = new MusicHolder(holderView);
        return musicHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
        holder.musicImageView.setImageResource(this.image[position]);
        holder.songTitleTextView.setText(this.title[position]);
        holder.singerTextView.setText(this.singer[position]);

        // 리스트뷰 아이템 클릭시, 음악 재생 화면(MusicPlayerActivity.java)로 전환.
        // TODO : 해당 아이템 youtube 읽어올 데이터 정의해서, playerIntent 랑 같이 전환하면 되겠습니다.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent playerIntent = new Intent(context, MusicPlayerActivity.class);
                playerIntent.putExtra("link", youtube_url[position]);

                context.startActivity(playerIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}


