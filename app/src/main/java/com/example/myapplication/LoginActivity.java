package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

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
                    startActivity(homeIntent);
                }else{
                    Toast.makeText(getApplicationContext(),"Required password over 8 characters",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}