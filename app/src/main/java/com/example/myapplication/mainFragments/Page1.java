package com.example.myapplication.mainFragments;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AwardActivity;
import com.example.myapplication.HorizontalMusicAdapter;
import com.example.myapplication.MusicAdapter;
import com.example.myapplication.PointChargeActivity;
import com.example.myapplication.CurrentMoneyActivity;
import com.example.myapplication.R;
import com.example.myapplication.FindActivity;
import com.example.myapplication.util.NetworkTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


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
    TextView remainTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_page1, container, false);

        remainView = v.findViewById(R.id.remainPointView);
        chargeView = v.findViewById(R.id.pointChargeView);
        reserveView = v.findViewById(R.id.reserveKaraokeView);
        awardView = v.findViewById(R.id.awardView);
        remainTextView = v.findViewById(R.id.remainTextView);



        String url = "http://115.85.180.70:3001/coocon/checkAccount";
        JSONObject object = new JSONObject();


        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("User", MODE_PRIVATE);
        String ID = sharedPreferences.getString("Id","default Name");  // 불러올려는 key, default Value



        try{
            object.put("u_id",ID);
        }
        catch (Exception e){
            Log.e("error",e.getMessage());
        }

        NetworkTask networkTask = new NetworkTask(url, object,"POST");
        String result = null;

        try{
            result = networkTask.execute().get();

            JSONObject obj = new JSONObject(result);
            String BAL_AMT = obj.get("BAL_AMT").toString();
            String REPY_CD = obj.get("REPY_CD").toString();

            if(REPY_CD.equals("0000")){
                remainTextView.setText(BAL_AMT);
            }
            else{
                remainTextView.setText("error");
            }


        }
        catch(Exception e){
            Log.i("error",e.getMessage());
        }

        viewClick();
        // 최근 노래 목록
        recentMusicList(v);
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

    void recentMusicList(View v) {
        recyclerView = v.findViewById(R.id.recentMusicListView);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        String result;
        String url = "http://115.85.180.70:3001/record/getList";
        JSONObject object = new JSONObject();

        // TODO : DB에서 노래정보 불러오면 됩니다.
        int[] image = {R.mipmap.ic_cover1_foreground, R.mipmap.ic_cover2_foreground, R.mipmap.ic_cover3_foreground,R.mipmap.ic_cover4_foreground, R.mipmap.ic_cover5_foreground};
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> singer = new ArrayList<>();
        ArrayList<String> youtube_url = new ArrayList<>();
        ArrayList<Integer> views = new ArrayList<>();
        ArrayList<Integer> likes = new ArrayList<>();

        try {
            NetworkTask parser = new NetworkTask(url, object, "POST");
            result = parser.execute().get();
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject temp = (JSONObject) array.get(i);
                title.add(temp.get("r_title").toString());
                singer.add(temp.get("r_name").toString());
                youtube_url.add(temp.get("r_url").toString());
                views.add(Integer.parseInt(temp.get("r_views").toString()));
                likes.add(Integer.parseInt(temp.get("r_likes").toString()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        adapter = new HorizontalMusicAdapter(image, title, singer, youtube_url, views, likes);
        recyclerView.setAdapter(adapter);
    }
}