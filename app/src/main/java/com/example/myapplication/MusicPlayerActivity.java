package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.util.NetworkTask;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class MusicPlayerActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;

    TextView hashTagTextView, titleTextView, singerTextView;
    ImageButton youtubePlayButton;
    LottieAnimationView thumsUpLottieView;

    YouTubePlayer.OnInitializedListener listener; // 이벤트처리, 리스너가 초기화가 됐을 때,

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;


    String title;
    String singer;
    String youtube_url;
    int likes;
    int views;

    ArrayList<String> title_array;
    ArrayList<String> singer_array;
    ArrayList<String> youtube_url_array;
    ArrayList<Integer> views_array;
    ArrayList<Integer> likes_array;


    void init() {
        hashTagTextView = findViewById(R.id.hashTagTextView);
        titleTextView = findViewById(R.id.titleTextView);
        singerTextView = findViewById(R.id.singerTextView);
        youtubePlayButton = findViewById(R.id.youtubePlayButton);
        thumsUpLottieView = findViewById(R.id.thumsUpLottieView);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeView);

        Intent intent = getIntent();



        title = intent.getStringExtra("title");
        singer = intent.getStringExtra("singer");
        youtube_url = intent.getStringExtra("link");

        titleTextView.setText(title);
        singerTextView.setText(singer);


        // TODO : DB에서 노래정보 불러오면 됩니다.
        int[] image = {R.mipmap.ic_cover1_foreground, R.mipmap.ic_cover2_foreground, R.mipmap.ic_cover3_foreground,R.mipmap.ic_cover4_foreground, R.mipmap.ic_cover5_foreground};

//
//        titleTextView.setText(title);
//        singerTextView.setText(singer);

        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(youtube_url); // 영상 아이디, 여기서 영상을 띄움

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

                uplikes(youtube_url);


                Toast.makeText(MusicPlayerActivity.this, "GOOD!", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = findViewById(R.id.musicListView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        adapter = new MusicAdapter(image);

        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        init();
    }

    void uplikes(String link){

        String url = "http://115.85.180.70:3001/record/uplikes";
        JSONObject object = new JSONObject();

        try{
            object.put("r_url",link);
        }
        catch (Exception e){
            Log.e("error",e.getMessage());
        }

        NetworkTask networkTask = new NetworkTask(url, object,"POST");
        networkTask.execute();
    }


}