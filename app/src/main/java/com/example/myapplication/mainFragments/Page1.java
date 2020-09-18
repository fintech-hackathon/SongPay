package com.example.myapplication.mainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AwardActivity;
import com.example.myapplication.HorizontalMusicAdapter;
import com.example.myapplication.MusicAdapter;
import com.example.myapplication.PointChargeActivity;
import com.example.myapplication.CurrentMoneyActivity;
import com.example.myapplication.R;
import com.example.myapplication.FindActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Page1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Page1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Page1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Page1.
     */
    // TODO: Rename and change types and number of parameters
    public static Page1 newInstance(String param1, String param2) {
        Page1 fragment = new Page1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    View remainView,chargeView,reserveView,awardView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_page1, container, false);

        remainView = v.findViewById(R.id.remainPointView);
        chargeView = v.findViewById(R.id.pointChargeView);
        reserveView = v.findViewById(R.id.reserveKaraokeView);
        awardView = v.findViewById(R.id.awardView);

        viewClick();
        // 최근 노래 목록
        recentMusicListView(v);
        return v;
    }

    void viewClick(){
        remainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CurrentMoneyActivity.class);
                startActivity(intent);
            }
        });
        chargeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PointChargeActivity.class);
                startActivity(intent);
            }
        });
        reserveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FindActivity.class);
                startActivity(intent);
            }
        });
        awardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AwardActivity.class);
                startActivity(intent);
            }
        });
    }
    void recentMusicListView(View v){
        recyclerView = v.findViewById(R.id.recentMusicListView);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);

        // 업로드된 영상 데이터랑 똑같이 맞추면 되겠습니다.
        int[] image =  {R.mipmap.ic_musicimage_round,R.mipmap.ic_musicimage_round};
        String[] title =  {"[다비치] 8282","[조정석] 아로하"};
        String[] singer = {"홍길동","박찬영"};

        adapter = new HorizontalMusicAdapter(image, title,singer);

        recyclerView.setAdapter(adapter);
    }
}
