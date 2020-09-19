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

import com.example.myapplication.util.NetworkTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

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


        Date time = new Date();
        SimpleDateFormat sdp = new SimpleDateFormat("yyyy/MM/dd/hh/mm");
        strTime = sdp.format(time);




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
            }
        });




        roomSelectDropdownMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomSelectMenu = new PopupMenu(getApplicationContext(), view);

                HashMap<String,int[]> hm = getList(owner);

                int roomnum = Integer.parseInt(room);
                int[] check = hm.get("check");
                int[] songnum = hm.get("songnum");

                Log.i("msg","check[] : " + Arrays.toString(check));
                Log.i("msg","songnum[] : " + Arrays.toString(songnum));


                for(int i = 0;i < roomnum ; i++){
                    String title = (i+1)+"번방 ";
                    if(check[i] == 1){
                        title += "("+songnum[i]+"곡 남음)";
                    }
                    else{
                        title += "(입장가능)";
                    }
                    roomSelectMenu.getMenu().add(title);
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

                String song = songSelectDropdownMenu.getText().toString();
                String room = roomSelectDropdownMenu.getText().toString();
                song = song.substring(0,song.length() - 1);
                room = room.substring(0,room.length() - 9);

                int TRAN_AMT =  ((Integer.parseInt(song)/Integer.parseInt(songbymoney)) * 1000);

                if(getCharge(ID,TRAN_AMT)){
                    String url = "http://115.85.180.70:3001/user/payment";
                    JSONObject object = new JSONObject();
                    try{
                        object.put("OWNER_ID",owner);
                        object.put("CUST_ID",ID);
                        object.put("TRAN_AMT",TRAN_AMT);
                        object.put("ROOM_NUM",room);
                        object.put("TYPE",TYPE);
                        object.put("DATE",strTime);
                    }
                    catch (Exception e){
                        Log.e("error",e.getMessage());
                    }

                    NetworkTask networkTask = new NetworkTask(url, object,"POST");
                    String res = null;
                    try{
                        res = networkTask.execute().get();
                    }
                    catch(Exception e){
                    }
                    if(res.equals("success")){
                        Toast.makeText(getApplicationContext(), "예약이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(homeIntent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "예약을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "포인트가 부족합니다.", Toast.LENGTH_SHORT).show();
                }

                // dialog로 표시
            }
        });
    }

    HashMap<String,int[]> getList(String sr_o_id){
        String url = "http://115.85.180.70:3001/room/getList";
        JSONObject object = new JSONObject();


        try{
            object.put("sr_o_id",sr_o_id);
        }
        catch (Exception e){
            Log.e("error",e.getMessage());
        }

        NetworkTask networkTask = new NetworkTask(url, object,"POST");
        String result = null;

        int roomnum = Integer.parseInt(room);
        int[] check = new int[roomnum];
        int[] songnum = new int[roomnum];

        try{
            result = networkTask.execute().get();

            JSONArray jsonarr = new JSONArray(result);

            for(int i = 0; i < jsonarr.length() ; i++){
                JSONObject obj = (JSONObject)jsonarr.get(i);
                check[Integer.parseInt(obj.get("sr_room").toString())] = 1;
                songnum[Integer.parseInt(obj.get("sr_room").toString())] = Integer.parseInt(obj.get("sr_song").toString());
            }
        }
        catch(Exception e){
            Log.i("error",e.getMessage());
        }
        HashMap<String,int[]> hm = new HashMap<>();
        hm.put("check",check);
        hm.put("songnum",songnum);
        return hm;
    }


    boolean getCharge(String Id,int money){
        String url = "http://115.85.180.70:3001/coocon/checkAccount";
        JSONObject object = new JSONObject();
        boolean flag = false;


        try{
            object.put("u_id",Id);
        }
        catch (Exception e){
            Log.e("error",e.getMessage());
        }

        NetworkTask networkTask = new NetworkTask(url, object,"POST");
        String result = null;
        String BAL_AMT = "";
        try{
            result = networkTask.execute().get();

            JSONObject obj = new JSONObject(result);
            BAL_AMT = obj.get("BAL_AMT").toString();
            String REPY_CD = obj.get("REPY_CD").toString();

        }
        catch(Exception e){
            Log.i("error",e.getMessage());
        }

        int amt = Integer.parseInt(BAL_AMT);

        if(amt < money){
            flag = false;
        }
        else{
            flag = true;
        }

        return flag;
    }


}
