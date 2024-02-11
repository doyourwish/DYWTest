package com.example.bottomnav.setting.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnav.R;
import com.example.bottomnav.common.UserMailAddress;

public class SendMailForResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail_for_reset_password);

        UserMailAddress userMailAddress = new UserMailAddress(SendMailForResetPasswordActivity.this);

        //メールアドレス表示
        String mailAddress = userMailAddress.getUserMailAddress();
        TextView loginEmailTextView = findViewById(R.id.login_email_text);
        EditText loginEmailEditText = findViewById(R.id.login_email_edit);
        if(mailAddress == null){
            loginEmailTextView.setVisibility(View.GONE);
            loginEmailEditText.setVisibility(View.VISIBLE);
            Log.d("mail_address","mail_address is null because you didn't log in");
        }
        else {
            loginEmailTextView.setVisibility(View.VISIBLE);
            loginEmailEditText.setVisibility(View.GONE);
            loginEmailTextView.setText(mailAddress);
        }

        //送信ボタン
        Button send_button = findViewById(R.id.send_button);
        send_button.setOnClickListener(v -> {
            //パスワード再設定画面に遷移
            //本来はこの画面でパスコードのメール送信をすべき
            //しかし、 パスコードメール送信 > パスワード再設定 のプロセスで共通のインスタンスを使用することや、
            //ネットワーク操作があるクラスは別のActivityにインスタンスを渡すためのSerializeができないため、
            //遷移先の画面でメール送信を行う
            Intent intent = new Intent(getApplication(), ResetPasswordActivity.class);
            if(mailAddress == null){
                intent.putExtra(getString(R.string.user_mail_address_key),loginEmailEditText.getText().toString());
            }
            else {
                intent.putExtra(getString(R.string.user_mail_address_key),mailAddress);
            }
            startActivity(intent);
            //パスワード再設定メール送信画面を閉じる
            finish();
        });

        //戻るボタン
        Button return_button = findViewById(R.id.return_button);
        return_button.setOnClickListener(v -> {
            //遷移元のホーム画面、ログイン画面に遷移(閉じる動作のみ)
            //パスワード再設定メール送信画面を閉じる
            finish();
        });

        //こちら表示
        TextView qa_textView = (TextView) findViewById(R.id.reset_password_concern_click);
        qa_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Q&A画面に遷移
                Intent intent = new Intent(getApplication(), UserAccountQAActivity.class);
                startActivity(intent);
            }
        });
    }
}