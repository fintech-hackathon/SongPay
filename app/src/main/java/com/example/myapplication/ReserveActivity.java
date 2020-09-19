package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReserveActivity extends AppCompatActivity {

//    노래방이름 , 주소, 가격정보(DB에서?)
    TextView titleTextView, addressTextView,priceInfoTextView,priceTextView;
    Button reserveButton;
    AutoCompleteTextView roomSelectDropdownMenu, songSelectDropdownMenu;
    TimePicker timePicker;
    String room,songbymoney,owner,strTime;

    PopupMenu roomSelectMenu,songSelectMenu;

    void init() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String address = intent.getStringExtra("address");
        room = intent.getStringExtra("room");
        songbymoney = intent.getStringExtra("songbymoney");
        owner = intent.getStringExtra("owner");


        titleTextView = findViewById(R.id.titleTextView);
        addressTextView = findViewById(R.id.addressTextView);
        priceInfoTextView  = findViewById(R.id.priceInfoTextView);

        roomSelectDropdownMenu = findViewById(R.id.roomSelectDropdownMenu);
        songSelectDropdownMenu = findViewById(R.id.songSelectDropdownMenu);
        timePicker = findViewById(R.id.timePicker);

        priceTextView = findViewById(R.id.priceTextView);
        reserveButton = findViewById(R.id.reserveButton);

        roomSelectDropdownMenu.setInputType(InputType.TYPE_NULL);
        songSelectDropdownMenu.setInputType(InputType.TYPE_NULL);

        titleTextView.setText(title);
        addressTextView.setText(address);
        priceInfoTextView.setText(songbymoney+"곡 : 1000원");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        init();

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {


                Date time = new Date();
                SimpleDateFormat sdp = new SimpleDateFormat("yyyy/MM/dd");
                String dd = sdp.format(time);

                strTime = dd+"/"+hourOfDay+"/"+minute;

                Toast.makeText(getApplicationContext(), strTime, Toast.LENGTH_SHORT).show();
            }
        });




        roomSelectDropdownMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomSelectMenu = new PopupMenu(getApplicationContext(), view);

                int roomnum = Integer.parseInt(room);

                for(int i = 0;i < roomnum ; i++){
                    roomSelectMenu.getMenu().add((i+1)+"번방");
                }
                roomSelectMenu.show();

                roomSelectMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        roomSelectDropdownMenu.setText(menuItem.getTitle().toString());
                        return onOptionsItemSelected(menuItem);
                    }
                });
            }
        });

        songSelectDropdownMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songSelectMenu = new PopupMenu(getApplicationContext(), view);

                //x 곡
                for(int i = 1;i <= 4; i++){
                    songSelectMenu.getMenu().add((Integer.parseInt(songbymoney)*i)+"곡");
                }
                songSelectMenu.show();

                songSelectMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        songSelectDropdownMenu.setText(menuItem.getTitle().toString());
                        return onOptionsItemSelected(menuItem);
                    }
                });
            }
        });

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences= getSharedPreferences("User", MODE_PRIVATE);

                String ID = sharedPreferences.getString("Id","default Name");  // 불러올려는 key, default Value
                final String TYPE = "1";

                Log.i("msg","ID : " + ID);
                Log.i("msg","owner : "+owner);
                Log.i("msg","songSelectDropdownMenu : "+songSelectDropdownMenu.getText());
                Log.i("msg","roomSelectDropdownMenu : "+roomSelectDropdownMenu.getText());
                Log.i("msg","room : "+room);
                Log.i("msg","songbymoney : "+songbymoney);
                Log.i("msg","TYPE : "+TYPE);
                Log.i("msg","Date : "+strTime);




                Toast.makeText(getApplicationContext(), "예약이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                finish();
                // dialog로 표시
            }
        });
    }


}
