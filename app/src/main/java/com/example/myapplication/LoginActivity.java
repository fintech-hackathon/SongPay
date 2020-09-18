package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.util.NetworkTask;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText idEditText,passEditText;
    Button loginButton;

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

                    String url = "http://172.30.1.33:3001/user/login";

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
                        // 로그인 성공
                        LoginSuccess(ID, Password);
                        startActivity(homeIntent);
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