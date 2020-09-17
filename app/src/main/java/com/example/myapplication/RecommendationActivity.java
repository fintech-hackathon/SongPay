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

        // DB에서 사용자 거래내역 불러오면 됩니다.
        int[] image =  {R.mipmap.ic_musicimage_round,R.mipmap.ic_musicimage_round};
        String[] title =  {"[다비치] 8282","[조정석] 아로하"};
        String[] singer = {"홍길동","박찬영"};

        adapter = new MusicAdapter(image, title,singer);

        recyclerView.setAdapter(adapter);
    }
}