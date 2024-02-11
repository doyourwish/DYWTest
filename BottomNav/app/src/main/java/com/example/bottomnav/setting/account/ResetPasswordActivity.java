package com.example.bottomnav.setting.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnav.R;
import com.example.bottomnav.cognito.CognitoResetPassword;
import com.example.bottomnav.popup.ActivityFinish;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.KindsButton;
import com.example.bottomnav.popup.Popup;

import java.util.ArrayList;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //メールアドレス取得
        Intent intent = getIntent();
        String mailAddress = intent.getStringExtra(getString(R.string.user_mail_address_key));

        //パスコード送信
        CognitoResetPassword cognitoResetPassword = new CognitoResetPassword(ResetPasswordActivity.this);
        cognitoResetPassword.sendForgotPasswordCode(mailAddress);

        //変更押下
        EditText text_new_password_1 = findViewById(R.id.new_password_1);
        EditText text_new_password_2 = findViewById(R.id.new_password_2);
        EditText text_passcode = findViewById(R.id.passcode_box);
        Button change_button = findViewById(R.id.change_button);
        change_button.setOnClickListener(v -> {
            String new_password_1 = text_new_password_1.getText().toString();
            String new_password_2 = text_new_password_2.getText().toString();
            String passcode = text_passcode.getText().toString();

            //パスワード確認
            if(!new_password_1.equals(new_password_2)){
                ButtonInfo buttonInfo = new ButtonInfo();
                Popup popup = new Popup(ResetPasswordActivity.this, buttonInfo);
                popup.createPopup("パスワード再設定", "「新しいパスワード」と「確認用パスワード」が一致しません");
                return;
            }

            //パスワード再設定
            //パスワード再設定画面も閉じられる
            cognitoResetPassword.setNewPassword(passcode,new_password_1);
        });

        //キャンセル押下
        Button cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(v -> {
            //遷移元のホーム画面、ログイン画面に遷移
            //パスワード再設定画面を閉じる
            ButtonInfo positiveButtonInfo = new ButtonInfo();
            positiveButtonInfo.popupFunctions.add(new ActivityFinish(ResetPasswordActivity.this));
            ButtonInfo negativeButtonInfo = new ButtonInfo();
            negativeButtonInfo.kindsButton = KindsButton.negative;
            ArrayList<ButtonInfo> multiButtonInfo = new ArrayList<>();
            multiButtonInfo.add(positiveButtonInfo);
            multiButtonInfo.add(negativeButtonInfo);
            Popup popup = new Popup(ResetPasswordActivity.this, multiButtonInfo);
            popup.createPopup("キャンセル確認","パスワードの再設定をやめますか？");
        });

        //こちら押下
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