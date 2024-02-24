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
import com.example.bottomnav.editText.FormPasscode;
import com.example.bottomnav.editText.FormPassword;
import com.example.bottomnav.popup.ActivityFinish;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.KindsButton;
import com.example.bottomnav.popup.Popup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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

        //パスコード確認
        TextInputEditText passcodeEditText = findViewById(R.id.passcode_box);
        TextInputLayout passcodeLayout = findViewById(R.id.layout_passcode_box);

        FormPasscode formPasscode = new FormPasscode(this);
        formPasscode.createInputForm(passcodeEditText, passcodeLayout);

        //パスワード確認
        TextInputEditText passwordEditText = findViewById(R.id.reset_password_box);
        TextInputLayout passwordLayout = findViewById(R.id.layout_reset_password_box);

        FormPassword formPassword = new FormPassword(this);
        formPassword.createInputForm(passwordEditText, passwordLayout);

        //変更押下
        Button change_button = findViewById(R.id.change_button);
        change_button.setOnClickListener(v -> {
            //パスコード入力確認
            if(formPasscode.getErrorMessage(passcodeEditText) != null){
                Popup popup = new Popup(this,new ButtonInfo());
                popup.createPopup(getString(R.string.passcode_re_input_title), formPasscode.getErrorMessage(passcodeEditText));
                return;
            }

            //パスワード入力確認
            if(formPassword.getErrorMessage(passwordEditText) != null){
                Popup popup = new Popup(this,new ButtonInfo());
                popup.createPopup(getString(R.string.password_re_input_title), formPassword.getErrorMessage(passwordEditText));
                return;
            }

            String passcode = passcodeEditText.getText().toString();
            String new_password = passwordEditText.getText().toString();

            //パスワード再設定
            //パスワード再設定画面も閉じられる
            cognitoResetPassword.setNewPassword(passcode,new_password);
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
            popup.createPopup(getString(R.string.reset_password_title),getString(R.string.reset_password_cancel_message));
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