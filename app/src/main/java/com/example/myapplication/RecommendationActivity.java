package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        recyclerView = findViewById(R.id.musicRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // TODO : DB에서 노래정보 불러오면 됩니다.
        int[] image = {R.mipmap.ic_musicimage_round, R.mipmap.ic_musicimage_round};
        String[] title = {"[다비치] 8282", "[조정석] 아로하"};
        String[] singer = {"홍길동", "박찬영"};
        String[] youtube_url = {"FWTfKpZ0VWU", "mxJGDa7ThbE"};
        int[] view = {10, 20};
        int[] recommend = {5, 10};

        adapter = new MusicAdapter(image, title, singer, youtube_url, view, recommend);

        recyclerView.setAdapter(adapter);
    }
}