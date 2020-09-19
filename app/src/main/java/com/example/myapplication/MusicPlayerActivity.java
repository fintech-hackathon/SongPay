package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class MusicPlayerActivity extends AppCompatActivity {

    TextView hashTagTextView, titleTextView, singerTextView;
    LottieAnimationView lottieAnimationView;

    void init() {
        hashTagTextView = findViewById(R.id.hashTagTextView);
        titleTextView = findViewById(R.id.titleTextView);
        singerTextView = findViewById(R.id.singerTextView);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);


    }

    void click() {
        // 투표 애니메이션 클릭 시, 애니메이션 재생
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        Intent intent = getIntent();
        String link = intent.getStringExtra("link");

        // 링크 전달되기 위해 임시로 만든겁니다. TODO : 삭제해주세요.
        TextView linkTextView = findViewById(R.id.linkTextView);
        linkTextView.setText(link);


        init();
        click();
    }
}