package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AwardActivity extends AppCompatActivity {


    Button recordButton, recommendationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_award);

        init();
        click();
    }

    void init(){
        recordButton = findViewById(R.id.recordButton);
        recommendationButton = findViewById(R.id.recommendationButton);
    }

    void click(){
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recordIntent = new Intent(getApplicationContext(),RecordActivity.class);
                startActivity(recordIntent);
            }
        });

        recommendationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommendationIntent = new Intent(getApplicationContext(),RecommendationActivity.class);
                startActivity(recommendationIntent);
            }
        });
    }
}