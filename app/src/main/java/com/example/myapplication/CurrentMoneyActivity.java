package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.util.DateUtils;
import com.example.myapplication.util.NetworkTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CurrentMoneyActivity extends AppCompatActivity {

    TextView nameText,remainText;
    Button weekButton,monthButton,threeMonthButton;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    String Id,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_money);

        // 저장한 [ID, Password]를 불러옵니다.
        SharedPreferences sharedPreferences= getSharedPreferences("User", MODE_PRIVATE);
        Id = sharedPreferences.getString("Id","default Name");  // 불러올려는 key, default Value
        Password = sharedPreferences.getString("Password","default Password");  // 불러올려는 key, default Value

        init();
        click();
    }

    void init(){
        nameText = findViewById(R.id.nameText);
        remainText = findViewById(R.id.remainText);
        weekButton = findViewById(R.id.weekButton);
        monthButton = findViewById(R.id.monthButton);
        threeMonthButton = findViewById(R.id.threeMonthButton);

        nameText.setText(Id);

        recyclerView = findViewById(R.id.historyListView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try{
            String charge = getCharge(Id);
            remainText.setText(charge);
        }

        catch (Exception e){

        }
        HashMap<String, ArrayList<String>> hm = new HashMap<>();
        try{
            hm = getChargeList(Id,0);
        }
        catch (Exception e){

        }

        adapter = new HistoryAdapter(hm.get("date"),hm.get("sub"));

        recyclerView.setAdapter(adapter);
    }

    void click(){
        weekButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                HashMap<String, ArrayList<String>> hm = new HashMap<>();
                try{
                    hm = getChargeList(Id,0);
                }
                catch (Exception e){

                }

                adapter = new HistoryAdapter(hm.get("date"),hm.get("sub"));

                recyclerView.setAdapter(adapter);
            }
        });

        monthButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                HashMap<String, ArrayList<String>> hm = new HashMap<>();
                try{
                    hm = getChargeList(Id,1);
                }
                catch (Exception e){

                }

                adapter = new HistoryAdapter(hm.get("date"),hm.get("sub"));

                recyclerView.setAdapter(adapter);
            }
        });

        threeMonthButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                HashMap<String, ArrayList<String>> hm = new HashMap<>();
                try{
                    hm = getChargeList(Id,2);
                }
                catch (Exception e){

                }

                adapter = new HistoryAdapter(hm.get("date"),hm.get("sub"));

                recyclerView.setAdapter(adapter);
            }
        });
    }

    String getCharge(String Id){
        String url = "http://115.85.180.70:3001/coocon/checkAccount";
        JSONObject object = new JSONObject();

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

        return BAL_AMT;
    }

    /*
    term
    0 : week
    1 : moneth
    2 : threemonth
     */
    HashMap<String, ArrayList<String>> getChargeList(String Id,int term){




        String url = "http://115.85.180.70:3001/transinfo/getList";
        JSONObject object = new JSONObject();

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
        }
        catch(Exception e){
            Log.i("error",e.getMessage());
        }




        HashMap<String, ArrayList<String>> hm = new HashMap<>();
        ArrayList<String> dateAr = new ArrayList<>();
        ArrayList<String> subAr = new ArrayList<>();


        try{
            JSONArray array = new JSONArray(result);

            Date today_date = new Date();
            Date yesterday_date = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            if(term == 0){
                cal.add(Calendar.DATE,-7);
            }
            else if(term == 1){
                cal.add(Calendar.MONTH,-1);
            }
            else if(term == 2){
                cal.add(Calendar.MONTH,-3);
            }





            SimpleDateFormat sdp = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat resultsdp = new SimpleDateFormat("yyyy.MM.dd");



            yesterday_date = sdp.parse(sdp.format(cal.getTime()));

            Log.i("msg","today : " + today_date);
            Log.i("msg","yesterday : "+yesterday_date.toString());


            for (int i = 0; i< array.length(); i++){
                JSONObject obj = (JSONObject)array.get(i);
                String date = obj.get("t_date").toString();
                String[] dateArr = date.split("/");
                date = dateArr[0]+"-"+dateArr[1]+"-"+dateArr[2];
                Date arrDate = sdp.parse(date);

                if(arrDate.after(yesterday_date)){

                    Log.i("msg","arrDate : "+arrDate);

                    Log.i("msg",arrDate.toString());
                    dateAr.add(resultsdp.format(arrDate));
                    if(obj.get("t_type").toString().equals("0")){//충전
                        subAr.add("충전/+ " + obj.get("t_money").toString());
                    }
                    else{//사용
                        subAr.add("사용/- " + obj.get("t_money").toString());
                    }
                }
            }


        }
        catch (Exception e){

        }
        hm.put("date",dateAr);
        hm.put("sub",subAr);
        return hm;
    }
}