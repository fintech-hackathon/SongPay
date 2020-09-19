package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.util.NetworkTask;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class PointChargeActivity extends AppCompatActivity {

    EditText bankDropdownMenu, accountNumberEditText, pointChargeEditText, nameEditText;
    TextInputLayout nameTextInputLayout, accountNumberTextInputLayout;
    Button pointChargeButton, qrScanButton;

    String name, account, chargePoint, bank, uAccount, ID;

    boolean hasAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_charge);

        init();

        if (bank.equals("")) {
            //계좌가 없을시 리다이렉션 하는곳입니다.
        } else { //계좌가 있을 경우, UI Data Set
            bankDropdownMenu.setText(codeToname(bank));
            accountNumberEditText.setText(uAccount);
            nameEditText.setText(name);
        }

        click();
    }

    void init() {
        nameEditText = findViewById(R.id.nameEditText);
        bankDropdownMenu = findViewById(R.id.bankDropdownMenu);
        accountNumberEditText = findViewById(R.id.accountNumberEditText);
        pointChargeEditText = findViewById(R.id.pointChargeEditText);
        pointChargeButton = findViewById(R.id.pointChargeButton);
//        qrScanButton = findViewById(R.id.qrScanButton);
//        qrScanButton.setVisibility(View.INVISIBLE);

        nameTextInputLayout = findViewById(R.id.nameTextInputLayout);
        accountNumberTextInputLayout = findViewById(R.id.accountTextInputLayout);


        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        ID = sharedPreferences.getString("Id", "default Name");  // 불러올려는 key, default Value

        String url = "http://115.85.180.70:3001/user/getinfo";

        JSONObject object = new JSONObject();

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
            bank = obj.get("u_bank").toString();
            uAccount = obj.get("u_account").toString();
            name = obj.get("u_name").toString();


        }
        catch(Exception e){
            Log.i("error",e.getMessage());
        }

    }

    void click(){
        pointChargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                충전 금액
                chargePoint = pointChargeEditText.getText().toString();

                if(!chargePoint.isEmpty()) {

                    String url = "http://115.85.180.70:3001/user/charge";

                    JSONObject object = new JSONObject();

                    try{
                        object.put("CUST_ID",ID);
                        object.put("TRAN_AMT",chargePoint);
                    }
                    catch (Exception e){
                        Log.e("error",e.getMessage());
                    }
                    Log.i("msg","=======================");
                    Log.i("msg",object.toString());
                    Log.i("msg","=======================");
                    NetworkTask networkTask = new NetworkTask(url, object,"POST");
                    String result = null;
                    try{
                        result = networkTask.execute().get();
                        Log.i("msg",result);

                        Fragment frg = null;
                        frg = getSupportFragmentManager().findFragmentById(R.id.page_1);
                        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.detach(frg).attach(frg).commit();
                    }
                    catch(Exception e){
                        Log.i("error",e.getMessage());
                    }
//                Toast.makeText(PointChargeActivity.this, "입금자 성함 : " + name + "\n계좌 번호 : " + account + "\n충전 금액 : " + chargePoint, Toast.LENGTH_SHORT).show();
                    Toast.makeText(PointChargeActivity.this, "충전이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(homeIntent);
                } else {
                    Toast.makeText(PointChargeActivity.this, "값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    String codeToname(String name){
        String result = "";
        if(name.equals("002")){
            result = "산업은행";
        }
        else if(name.equals("003")){
            result = "기업은행";
        }
        else if(name.equals("004")){
            result = "국민은행";
        }
        else if(name.equals("005")){
            result = "하나은행";
        }
        else if(name.equals("011")){
            result = "농협은행";
        }
        else if(name.equals("020")){
            result = "우리은행";
        }
        else if(name.equals("081")){
            result = "하나은행";
        }
        else if(name.equals("088")){
            result = "신한은행";
        }
        return result;
    }
}