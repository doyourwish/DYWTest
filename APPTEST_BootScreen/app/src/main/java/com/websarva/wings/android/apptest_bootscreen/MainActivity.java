package com.websarva.wings.android.apptest_bootscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountDownTimer count = new CountDownTimer(5000, 1000) { // 5000ミリ秒、1000ミリ秒間隔で実行
            public void onTick(long millisUntilFinished) {
                // 何かしらの処理
            }
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, APPTEST_Explanation.class);
                startActivity(intent);
                finish();
            }
        }.start();

    }
}