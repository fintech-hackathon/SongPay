package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.List;

public class FindActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;

    // 노래방 리스트 RecyclerView
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    // 네이버 지도 마커 클릭 시, 뜨는 정보창
    InfoWindow infoWindow = new InfoWindow();

    // TODO : 노래방 리스트 데이터 불러오면 됩니다.(데이터를 어떻게 불러오실지 몰라서 일단 String[]로 남겨둡니다...)
    String[] titleData = {"악쓰는 하마 2호점", "홍대M가라오케점", "스토리M멀티방", "홍대M가라오케점 2호점", "스토리M멀티방 2호점"};
    String[] subData = {"서울특별시 마포구 홍익로3길8", "서울특별시 마포구 와우산로 315", "서울특별시 마포구 양화로 183번길", "서울특별시 마포구 와우산로 315", "서울특별시 마포구 양화로 183번길"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        naver_Map();

        recyclerView = findViewById(R.id.mapRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new MapAdapter(titleData, subData);

        recyclerView.setAdapter(adapter);
        // ListView 클릭 이벤트는 (MapAdapter.java)에 있습니다.


        // 정보창 이름 설정
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(getApplicationContext()) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return (CharSequence) infoWindow.getMarker().getTag();
            }
        });
        // 정보창 클릭 이벤트는 아직입니다........
//        infoWindow.setOnClickListener();
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

    //    위치 찾기 권한을 얻기 위한 함수
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

        // 좌표 배열
        ArrayList<LatLng> markerPoints = new ArrayList<>();
        // TODO : 이 부분에 좌표값 불러오면 됩니다.
        markerPoints.add(new LatLng(37.5670135, 126.9783740));
        markerPoints.add(new LatLng(37.4670145, 126.9383730));
        markerPoints.add(new LatLng(37.7670155, 126.5783720));
        markerPoints.add(new LatLng(37.2670165, 126.3783710));
        markerPoints.add(new LatLng(37.1670175, 126.6283700));

        // Marker Data - 마커 배열
        ArrayList<Marker> markers = new ArrayList<>();


        // Marker Click Event
        // lambda error 시, 참고 : https://fluorite94.tistory.com/85
        Overlay.OnClickListener listener = overlay -> {
            Marker marker = (Marker) overlay;
            //열린 정보창이 없다면, 정보창 열기
            if (marker.getInfoWindow() == null) {
                infoWindow.open(marker);
            }
            // 정보창 닫기
            else {
                infoWindow.close();
            }
            return true;
        };

        // marker 위치 설정, 이벤트 설정 -> Markers 배열에 추가
        for (int i = 0; i < markerPoints.size(); i++) {
            Marker marker = new Marker();
            // 마커 좌표
            marker.setPosition(markerPoints.get(i));
            marker.setOnClickListener(listener);
            marker.setTag(titleData[i]);
            markers.add(marker);
        }

        // markers 배열에 있는 marker 들을 네이버맵에 매핑
        for (Marker m : markers) {
            m.setMap(naverMap);
        }


        // 네이버 지도 클릭시, 인포창 닫음
        naverMap.setOnMapClickListener((coord, point) -> {
            infoWindow.close();
        });

    }
}
