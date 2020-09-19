package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.util.NetworkTask;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText idEditText,passEditText;
    Button loginButton;

    // 알림창
    AlertDialog accountDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        click();
    }


    void init(){
        idEditText = findViewById(R.id.idTextInput);
        passEditText = findViewById(R.id.passwordInputText);
        loginButton = findViewById(R.id.loginButton);


        idEditText.setText("bonobono1");
        passEditText.setText("123412341");

        // alert 창 builder 설정
        Context context;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("로그인 정보가 없습니다.").setMessage("입력하신 정보로 회원가입을 진행하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String ID = idEditText.getText().toString();
                String Password = passEditText.getText().toString();


                String url = "http://115.85.180.70:3001/user/join";

                JSONObject object = new JSONObject();

                try{
                    object.put("u_id",ID);
                    object.put("u_pw",Password);
                }
                catch (Exception e){
                    Log.e("error",e.getMessage());
                }

                // AsyncTask를 통해 HttpURLConnection 수행.
                NetworkTask networkTask = new NetworkTask(url, object,"POST");
                String result = null;
                try{
                    result = networkTask.execute().get();
                }
                catch(Exception e){
                    Log.i("error",e.getMessage());
                }

                if(result.equals("success")){
                    Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);
                    homeIntent.putExtra("ID",ID);
                    homeIntent.putExtra("PASSWORD",Password);

                    LoginSuccess(ID, Password);
                    startActivity(homeIntent);
                }
                else{
                    //로그인 실패 메세지 보여주기
                }


            }
        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 취소
            }
        });
        accountDialog = builder.create();

    }

    void click(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);
                if(passEditText.getText().length() > 8) {
                    String ID = idEditText.getText().toString();
                    String Password = passEditText.getText().toString();
                    homeIntent.putExtra("ID",ID);
                    homeIntent.putExtra("PASSWORD",Password);

                    String url = "http://115.85.180.70:3001/user/login";

                    JSONObject object = new JSONObject();

                    try{
                        object.put("u_id",ID);
                        object.put("u_pw",Password);
                    }
                    catch (Exception e){
                        Log.e("error",e.getMessage());
                    }

                    // AsyncTask를 통해 HttpURLConnection 수행.
                    NetworkTask networkTask = new NetworkTask(url, object,"POST");
                    String result = null;
                    try{
                        result = networkTask.execute().get();
                    } catch (Exception e) {
                        Log.i("error", e.getMessage());
                    }

                    if (result.equals("success")) {
                        // 로그인 성공
                        LoginSuccess(ID, Password);
                        startActivity(homeIntent);
                    } else {
                        Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        accountDialog.show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Required password over 8 characters",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 로그인이 성공하면, 사용자의 [ID와 password]를 다른 Activity에서도 쓸 수 있게 저장해둡니다.
    void LoginSuccess(String Id, String Password){
        SharedPreferences sharedPreferences= getSharedPreferences("User", MODE_PRIVATE);    // test 이름의 기본모드 설정
        SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
        editor.putString("Id",Id);
        editor.putString("Password",Password);
        editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.
    }
}