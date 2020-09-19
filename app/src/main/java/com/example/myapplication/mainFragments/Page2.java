package com.example.myapplication.mainFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.util.NetworkTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Page2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Page2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Page2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Page2.
     */
    // TODO: Rename and change types and number of parameters
    public static Page2 newInstance(String param1, String param2) {
        Page2 fragment = new Page2();
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

    //      이름              노래방 이름          방 번호            예약 곡 수
    TextView nameTextView, karaokeNameTextView, numberTextView, songTextView;
    Button confirmButton;

    String Id;
    Boolean isReserve = false;

    void init(View v) {
        nameTextView = v.findViewById(R.id.nameTextView);
        karaokeNameTextView = v.findViewById(R.id.karaokeNameTextView);
        numberTextView = v.findViewById(R.id.numberTextView);
        songTextView = v.findViewById(R.id.songTextView);
        confirmButton = v.findViewById(R.id.confirmButton);

        // 저장한 [ID, Password]를 불러옵니다.
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        Id = sharedPreferences.getString("Id", "default Name");  // 불러올려는 key, default Value
        // 노래방 예약 내역을 DB 에 저장해서 불러 오면 될 것 같습니다!(isReserve)
        nameTextView.setText(Id+" 님");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_page2, container, false);


        init(v);

        HashMap<String,String> hm =  getList(Id);
        isReserve = Boolean.parseBoolean(hm.get("isReserve"));

        if (isReserve) {
            String result = hm.get("result");
            try{
                JSONObject obj = new JSONObject(result);


                numberTextView.setText(obj.get("sr_room") +"번 방");
                songTextView.setText(obj.get("sr_song") + "곡");

                String name = getSingingRoomName(obj.get("sr_o_id").toString());
                karaokeNameTextView.setText(name);
            }
            catch (Exception e){

            }

            // 노래방 이용 시작!
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    roomdelete(Id);
                    Toast.makeText(getContext(), "쏭페이를 이용해주셔서 감사합니다.", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(getContext(), MainActivity.class);
                    startActivity(homeIntent);
                }
            });
        } else {
            karaokeNameTextView.setText("예약 내역이 없습니다...");
            numberTextView.setText("");
            songTextView.setText("");
            confirmButton.setEnabled(false);
        }


        return v;
    }

    HashMap<String,String> getList(String sr_u_id){
        HashMap<String,String> hm = new HashMap<>();
        String url = "http://115.85.180.70:3001/room/getListByUser";
        JSONObject object = new JSONObject();

        try{
            object.put("sr_u_id",sr_u_id);
        }
        catch (Exception e){
            Log.e("error",e.getMessage());
        }

        NetworkTask networkTask = new NetworkTask(url, object,"POST");
        String result = null;

        try{
            result = networkTask.execute().get();
            if(result.isEmpty()){
                hm.put("isReserve","false");
            }
            else{
                hm.put("isReserve","true");
                hm.put("result",result);
            }
        }
        catch(Exception e){
            Log.i("error",e.getMessage());
        }

        return hm;
    }

    String getSingingRoomName(String o_id){

        String url = "http://115.85.180.70:3001/owner/getOwner";
        JSONObject object = new JSONObject();

        try{
            object.put("o_id",o_id);
        }
        catch (Exception e){
            Log.e("error",e.getMessage());
        }

        NetworkTask networkTask = new NetworkTask(url, object,"POST");
        String result = null;

        String name = "";
        try{
            result = networkTask.execute().get();
            JSONObject obj = new JSONObject(result);
            name = obj.get("o_singingroomname").toString();
        }
        catch(Exception e){
        }
        return name;
    }

    void roomdelete(String sr_u_id){

        String url = "http://115.85.180.70:3001/room/roomdelete";
        JSONObject object = new JSONObject();

        try{
            object.put("sr_u_id",sr_u_id);
        }
        catch (Exception e){
            Log.e("error",e.getMessage());
        }

        NetworkTask networkTask = new NetworkTask(url, object,"POST");
        networkTask.execute();
    }
}