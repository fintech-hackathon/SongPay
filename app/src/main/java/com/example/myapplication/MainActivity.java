package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.mainFragments.Page1;
import com.example.myapplication.mainFragments.Page2;
import com.example.myapplication.mainFragments.Page3;
import com.example.myapplication.mainFragments.QrCodeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;

    Page1 page1Fragment = new Page1();
    Page2 page2Fragment = new Page2();
    Page3 page3Fragment = new Page3();
    QrCodeFragment qrCodeFragment = new QrCodeFragment();
    String Id,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();



        Intent intent = getIntent();

//        ID, Password 정보로 사용자 정보 읽어오면 될 것 같습니당!
        Id  = intent.getStringExtra("ID");
        Password  = intent.getStringExtra("PASSWORD");
//        Toast.makeText(getApplicationContext(), Id+" : " + Password, Toast.LENGTH_SHORT).show();
    }

    void init(){

        bottomNavigation = findViewById(R.id.bottom_navigation);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,page1Fragment).commitAllowingStateLoss();

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentTransaction = fragmentManager.beginTransaction();

                switch(item.getItemId()) {
                    case R.id.page_1:
                        fragmentTransaction.replace(R.id.frameLayout, page1Fragment).commitAllowingStateLoss();
                        return true;
                    case R.id.page_2:
                        fragmentTransaction.replace(R.id.frameLayout, page2Fragment).commitAllowingStateLoss();
                        return true;
                    case R.id.qrPage:
                        fragmentTransaction.replace(R.id.frameLayout, qrCodeFragment).commitAllowingStateLoss();
                        return true;
                    case R.id.page_3:
                        fragmentTransaction.replace(R.id.frameLayout, page3Fragment).commitAllowingStateLoss();
                        return true;
                }
                return false;
            }

        });
    }

}