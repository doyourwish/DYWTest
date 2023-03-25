package com.websarva.wings.android.apptest_bootscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class APPTEST_Register_Maill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apptest_register_maill);

        Button button = findViewById(R.id.bt_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(APPTEST_Register_Maill.this, APPTEST_Explanation.class);
                startActivity(intent);
                // 前の画面を終了する
                finish();
            }
        });

        Button button2 = findViewById(R.id.bt_register);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(APPTEST_Register_Maill.this, APPTEST_Gender_Age.class);
                startActivity(intent);
                // 前の画面を終了する
                finish();
            }
        });

        TextView textView = (TextView) findViewById(R.id.tv_skip);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(APPTEST_Register_Maill.this, APPTEST_Gender_Age.class);
                startActivity(intent);
                // 前の画面を終了する
                finish();
            }
        });

    }
}