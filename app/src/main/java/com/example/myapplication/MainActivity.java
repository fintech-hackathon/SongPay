package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.mainFragments.Page1;
import com.example.myapplication.mainFragments.Page2;
import com.example.myapplication.mainFragments.Page3;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;

    Page1 page1Fragment = new Page1();
    Page2 page2Fragment = new Page2();
    Page3 page3Fragment = new Page3();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    void init(){
        bottomNavigation = findViewById(R.id.bottom_navigation);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,page1Fragment).commitAllowingStateLoss();

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentTransaction = fragmentManager.beginTransaction();

                switch(item.getItemId()){
                    case R.id.page_1 : fragmentTransaction.replace(R.id.frameLayout,page1Fragment).commitAllowingStateLoss(); return true;
                    case R.id.page_2 : fragmentTransaction.replace(R.id.frameLayout,page2Fragment).commitAllowingStateLoss(); return true;
                    case R.id.page_3 : fragmentTransaction.replace(R.id.frameLayout,page3Fragment).commitAllowingStateLoss(); return true;
                }
                return false;
            }

        });
    }

}