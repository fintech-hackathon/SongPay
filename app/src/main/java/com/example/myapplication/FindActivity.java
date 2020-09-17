package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.List;

public class FindActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        naver_Map();


        recyclerView = findViewById(R.id.mapRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // 노래방 리스트 데이터 입니다.
        String[] titleData =  {"악쓰는 하마 2호점","홍대M가라오케점","스토리M멀티방"};
        String[] subData =  {"서울특별시 마포구 홍익로3길8","서울특별시 마포구 와우산로 315","서울특별시 마포구 양화로 183번길"};

        adapter = new MapAdapter(titleData, subData);

        recyclerView.setAdapter(adapter);
    }

    void naver_Map(){
        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            NaverMapOptions options = new NaverMapOptions().zoomControlEnabled(false);
            mapFragment = MapFragment.newInstance(options);
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

    }

//    위치 권한을 얻기 위한 함수
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);

        // 네이버 지도 ui 객체 접근
        UiSettings uiSettings = naverMap.getUiSettings();
        // 현재 위치 버튼 활성화
        uiSettings.setLocationButtonEnabled(true);

        // Marker Data - 마커 배열
        List<Marker> markers = new ArrayList<>();
        Marker marker1 = new Marker();
        Marker marker2 = new Marker();
        Marker marker3 = new Marker();

        marker1.setPosition(new LatLng(37.567,126.9));
        marker2.setPosition(new LatLng(37.566,126.8));
        marker3.setPosition(new LatLng(37.565,126.7));

        marker1.setMap(naverMap);
        marker2.setMap(naverMap);
        marker3.setMap(naverMap);

        for (int i = 0; i < 1; ++i) {
            // 각 마커 데이터를 입력하면 됩니다.
            Marker marker = new Marker();
            // 마커 좌표
            marker.setPosition(new LatLng(37.5670135, 126.9783740));
            markers.add(marker);
        }

        // markers 배열에 있는 marker들을 네이버맵에 매핑
        for (Marker marker : markers) {
            marker.setMap(naverMap);
        }
    }
}
