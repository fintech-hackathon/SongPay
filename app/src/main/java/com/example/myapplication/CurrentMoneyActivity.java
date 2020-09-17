package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CurrentMoneyActivity extends AppCompatActivity {

    TextView nameText,remainText;
    Button weekButton,monthButton,threeMonthButton;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    String Id,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_money);

        // 저장한 [ID, Password]를 불러옵니다.
        SharedPreferences sharedPreferences= getSharedPreferences("User", MODE_PRIVATE);
        Id = sharedPreferences.getString("Id","default Name");  // 불러올려는 key, default Value
        Password = sharedPreferences.getString("Password","default Password");  // 불러올려는 key, default Value

        init();
        click();
    }

    void init(){
        nameText = findViewById(R.id.nameText);
        remainText = findViewById(R.id.remainText);
        weekButton = findViewById(R.id.weekButton);
        monthButton = findViewById(R.id.monthButton);
        threeMonthButton = findViewById(R.id.threeMonthButton);

        nameText.setText(Id);

        recyclerView = findViewById(R.id.historyListView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // DB에서 사용자 거래내역 불러오면 됩니다.
        String[] date =  {"2020.09.10","2020.09.05","2020.09.01"};
        String[] remain =  {"4000","3000","1000"};
        String[] sub = {"-3000(사용)","+2000(충전)","-1000(사용"};

        adapter = new HistoryAdapter(date, remain,sub);

        recyclerView.setAdapter(adapter);
    }

    void click(){

    }
}