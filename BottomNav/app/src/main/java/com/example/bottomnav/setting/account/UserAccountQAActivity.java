package com.example.bottomnav.setting.account;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnav.R;
import com.example.bottomnav.common.MyAsyncTask;

public class UserAccountQAActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_qa_activity);

        //http getテスト
        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute();

        //戻るボタン
        Button return_button = findViewById(R.id.return_button);
        return_button.setOnClickListener(v -> {
            //パスワード再設定メール送信画面を閉じる
            finish();
        });
    }
}
