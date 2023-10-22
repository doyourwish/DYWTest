package com.example.bottomnav;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Login_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 既にログインしてる場合、自動ログインして画面を閉じる
        UserMailAddress userMailAddress = new UserMailAddress(Login_Activity.this);
        if(userMailAddress.getUserMailAddress() != null){
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // パスワード忘却テキスト
        TextView forget_text = findViewById(R.id.forget_text);
        forget_text.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), SendMailForResetPasswordActivity.class);
            startActivity(intent);
        });

        // アカウント登録テキスト
        TextView register_text = findViewById(R.id.register_text);
        register_text.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), Register.class);
            startActivity(intent);
        });

        // ログインボタン
        EditText text_email = findViewById(R.id.email);
        EditText text_password = findViewById(R.id.password);
        Button login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(v -> {
            String email = text_email.getText().toString();
            String password = text_password.getText().toString();
            //login
            //パスワード入力を反映させるために、一度サインアウト
            CognitoLogin cognitoLogin = new CognitoLogin(Login_Activity.this);
            cognitoLogin.signOut(email);
            cognitoLogin.login(email,password);
        });
    }
}





