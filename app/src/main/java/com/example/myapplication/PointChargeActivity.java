package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PointChargeActivity extends AppCompatActivity {

    EditText nameEditText,accountNumberEditText,pointChargeEditText;
    Button pointChargeButton,qrScanButton;

    String name,account,chargePoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_charge);

        init();
        click();
    }

    void init(){
        nameEditText = findViewById(R.id.nameEditText);
        accountNumberEditText = findViewById(R.id.accountNumberEditText);
        pointChargeEditText = findViewById(R.id.pointChargeEditText);
        pointChargeButton = findViewById(R.id.pointChargeButton);
        qrScanButton = findViewById(R.id.qrScanButton);
    }

    void click(){
        pointChargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                입금자 성함
                name = nameEditText.getText().toString();
//                계좌 번호
                account = accountNumberEditText.getText().toString();
//                충전 금액
                chargePoint = pointChargeEditText.getText().toString();

                if(!name.isEmpty() && !account.isEmpty() && !chargePoint.isEmpty()) {
//                Toast.makeText(PointChargeActivity.this, "입금자 성함 : " + name + "\n계좌 번호 : " + account + "\n충전 금액 : " + chargePoint, Toast.LENGTH_SHORT).show();
                    Toast.makeText(PointChargeActivity.this, "충전이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PointChargeActivity.this, "값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        qrScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qrScanIntent = new Intent(getApplicationContext(),ScanQrActivity.class);
                startActivity(qrScanIntent);
            }
        });
    }
}