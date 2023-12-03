package com.example.bottomnav.setting.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnav.R;
import com.example.bottomnav.cognito.CognitoResetMailAddress;
import com.example.bottomnav.common.Popup;

public class ResetMailAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_mail_address);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Popup popup = new Popup(ResetMailAddressActivity.this);

        //メールアドレス取得
        Intent intent = getIntent();
        String newMailAddress = intent.getStringExtra(getString(R.string.user_new_mail_address_key));

        //パスコード送信
        CognitoResetMailAddress cognitoResetMailAddress = new CognitoResetMailAddress();
        cognitoResetMailAddress.sendSetMailAddressCode(newMailAddress,ResetMailAddressActivity.this);

        //変更押下
        EditText text_passcode = findViewById(R.id.passcode_box);
        Button change_button = findViewById(R.id.change_button);
        change_button.setOnClickListener(v -> {
            String passcode = text_passcode.getText().toString();

            //メールアドレス再設定
            //メールアドレス再設定画面も閉じられる
            cognitoResetMailAddress.setNewMailAddress(passcode);
        });

        //キャンセル押下
        Button cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(v -> {
            //遷移元のホーム画面に遷移
            //メールアドレス再設定画面を閉じる
            popup.showPopupTwoButtonWithActivityFinish("キャンセル確認","メールアドレスの再設定をやめますか？");
        });

        //こちら押下
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
