package com.example.bottomnav.setting.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import com.example.bottomnav.R;
import com.example.bottomnav.common.Popup;
import com.example.bottomnav.common.UserMailAddress;

public class SendMailForResetMailAddressActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail_for_reset_mail_address);
    }

    @Override
    protected void onStart(){
        super.onStart();

        Popup popup = new Popup(SendMailForResetMailAddressActivity.this);
        UserMailAddress userMailAddress = new UserMailAddress(SendMailForResetMailAddressActivity.this);
        String oldMailAddress = userMailAddress.getUserMailAddress();

        //メールアドレス表示
        TextView loginEmailTextView = findViewById(R.id.login_email_text);
        if(oldMailAddress == null){
            loginEmailTextView.setVisibility(View.GONE);
            Log.d("mail_address","mail_address is null because you didn't log in");
        }
        else {
            loginEmailTextView.setVisibility(View.VISIBLE);
            loginEmailTextView.setText(oldMailAddress);
        }

        //送信ボタン
        Button send_button = findViewById(R.id.send_button);
        send_button.setOnClickListener(v -> {
            //メールアドレス再設定画面に遷移
            //他のcognitoの処理に合わせて、遷移先の画面でメール送信
            if(oldMailAddress == null){
                popup.showPopup("Error","再ログインをしてください");
            }
            else {
                EditText newMailAddressText = findViewById(R.id.new_email_edit);
                String newMailAddress = newMailAddressText.getText().toString();
                Intent intent = new Intent(getApplication(), ResetMailAddressActivity.class);
                intent.putExtra(getString(R.string.user_new_mail_address_key),newMailAddress);
                startActivity(intent);
                //メールアドレス再設定メール送信画面を閉じる
                finish();
            }
        });

        //戻るボタン
        Button return_button = findViewById(R.id.return_button);
        return_button.setOnClickListener(v -> {
            //遷移元のホーム画面に遷移(閉じる動作のみ)
            //メールアドレス再設定メール送信画面を閉じる
            finish();
        });

        //こちら表示
        TextView qa_textView = (TextView) findViewById(R.id.reset_mail_address_concern_click);
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
