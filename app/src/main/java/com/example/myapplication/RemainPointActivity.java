package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RemainPointActivity extends AppCompatActivity {

    TextView nameText,remainText;
    Button weekButton,monthButton,threeMonthButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remain_point);

        init();
        click();
    }

    void init(){
        nameText = findViewById(R.id.nameText);
        remainText = findViewById(R.id.remainText);
        weekButton = findViewById(R.id.weekButton);
        monthButton = findViewById(R.id.monthButton);
        threeMonthButton = findViewById(R.id.threeMonthButton);


    }

    void click(){

    }
}