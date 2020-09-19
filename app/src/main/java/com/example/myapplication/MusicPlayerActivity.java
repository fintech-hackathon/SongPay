package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MusicPlayerActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;

    TextView hashTagTextView, titleTextView, singerTextView;
    ImageButton youtubePlayButton;
    LottieAnimationView thumsUpLottieView;

    YouTubePlayer.OnInitializedListener listener; // 이벤트처리, 리스너가 초기화가 됐을 때,

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    String youtube_link;

    void init() {
        hashTagTextView = findViewById(R.id.hashTagTextView);
        titleTextView = findViewById(R.id.titleTextView);
        singerTextView = findViewById(R.id.singerTextView);
        youtubePlayButton = findViewById(R.id.youtubePlayButton);
        thumsUpLottieView = findViewById(R.id.thumsUpLottieView);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeView);


        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(youtube_link); // 영상 아이디, 여기서 영상을 띄움

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        // 이 버튼을 눌러야지만 유튜브 API를 통해 불러와서 재생이 가능합니다.
        youtubePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youTubePlayerView.initialize("AIzaSyCiKLJK44vLg0nMpFQ8fnjXFch7wVvMme8", listener); // api키 입력 후 리스너에 연결
            }
        });

        thumsUpLottieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : 좋아요 클릭 이벤트 처리 하시면 됩니다.
                Toast.makeText(MusicPlayerActivity.this, "GOOD!", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = findViewById(R.id.musicListView);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        Intent intent = getIntent();
        youtube_link = intent.getStringExtra("link");

        init();
    }
}