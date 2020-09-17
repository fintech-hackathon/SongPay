package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReserveActivity extends AppCompatActivity {

//    노래방이름 , 주소, 가격정보(DB에서?)
    TextView titleTextView, addressTextView,priceInfoTextView,priceTextView;
    Button reserveButton;
    AutoCompleteTextView roomSelectDropdownMenu, songSelectDropdownMenu;
    TimePicker timePicker;

    PopupMenu roomSelectMenu,songSelectMenu;

    void init() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String address = intent.getStringExtra("address");

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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        init();

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String strTime = hourOfDay + " : " + minute;
                Toast.makeText(getApplicationContext(), strTime, Toast.LENGTH_SHORT).show();
            }
        });


        String[] RoomsArray = new String[]{"Item 1", "Item 2", "Item 3", "Item 4"};

        roomSelectDropdownMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomSelectMenu = new PopupMenu(getApplicationContext(), view);

                roomSelectMenu.getMenu().add("1번방");
                roomSelectMenu.getMenu().add("2번방");
                roomSelectMenu.getMenu().add("3번방");
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

                songSelectMenu.getMenu().add("3곡");
                songSelectMenu.getMenu().add("5곡");
                songSelectMenu.getMenu().add("10곡");
                songSelectMenu.getMenu().add("30곡");
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
                Toast.makeText(getApplicationContext(), "예약이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                finish();
                // dialog로 표시
            }
        });
    }


}
