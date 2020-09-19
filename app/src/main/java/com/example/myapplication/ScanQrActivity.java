package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.util.NetworkTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

public class ScanQrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        new IntentIntegrator(this).initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                // todo
            } else {


                String url = "http://115.85.180.70:3001//user/payment";


                SharedPreferences sharedPreferences= getSharedPreferences("User", MODE_PRIVATE);
                String ID = sharedPreferences.getString("Id","default Name");  // 불러올려는 key, default Value
                String TRAN_AMT = "";
                String OWNER_ID = "";
                String ROOM_NUM = "";
                try{
                    JSONObject obj = new JSONObject(result.getContents());

                    OWNER_ID = obj.getString("OWNER_ID");
                    TRAN_AMT = obj.getString("TRAN_AMT");
                    ROOM_NUM = obj.getString("ROOM_NUM");

                }
                catch (Exception e){

                }

                JSONObject object = new JSONObject();
                try{
                    object.put("OWNER_ID",OWNER_ID);
                    object.put("CUST_ID",ID);
                    object.put("TRAN_AMT",TRAN_AMT);
                    object.put("ROOM_NUM",ROOM_NUM);
                    object.put("TYPE","0");
                    object.put("DATE","");
                }
                catch (Exception e){
                    Log.e("error",e.getMessage());
                }

                NetworkTask networkTask = new NetworkTask(url, object,"POST");

                String res = null;
                try{
                    res = networkTask.execute().get();
                    Log.i("msg",res);
                }
                catch(Exception e){
                    Log.i("error",e.getMessage());
                }
//                Toast.makeText(PointChargeActivity.this, "입금자 성함 : " + name + "\n계좌 번호 : " + account + "\n충전 금액 : " + chargePoint, Toast.LENGTH_SHORT).show();
                Toast.makeText(ScanQrActivity.this, "충전이 완료되었습니다!", Toast.LENGTH_SHORT).show();

                Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(homeIntent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}