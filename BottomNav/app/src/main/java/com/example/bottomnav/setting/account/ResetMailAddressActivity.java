package com.example.bottomnav.setting.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnav.R;
import com.example.bottomnav.cognito.CognitoResetMailAddress;
import com.example.bottomnav.editText.FormPasscode;
import com.example.bottomnav.popup.ActivityFinish;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.KindsButton;
import com.example.bottomnav.popup.Popup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ResetMailAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_mail_address);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //メールアドレス取得
        Intent intent = getIntent();
        String newMailAddress = intent.getStringExtra(getString(R.string.user_new_mail_address_key));

        //パスコード送信
        CognitoResetMailAddress cognitoResetMailAddress = new CognitoResetMailAddress(ResetMailAddressActivity.this);
        cognitoResetMailAddress.sendSetMailAddressCode(newMailAddress);

        //パスコード確認
        TextInputEditText passcodeEditText = findViewById(R.id.passcode_box);
        TextInputLayout passcodeLayout = findViewById(R.id.layout_passcode_box);

        FormPasscode formPasscode = new FormPasscode(this);
        formPasscode.createInputForm(passcodeEditText, passcodeLayout);

        //変更押下
        Button change_button = findViewById(R.id.change_button);
        change_button.setOnClickListener(v -> {
            //パスコード入力確認
            if(formPasscode.getErrorMessage(passcodeEditText) != null){
                Popup popup = new Popup(this,new ButtonInfo());
                popup.createPopup(getString(R.string.passcode_re_input_title), formPasscode.getErrorMessage(passcodeEditText));
                return;
            }

            String passcode = passcodeEditText.getText().toString();

            //メールアドレス再設定
            //メールアドレス再設定画面も閉じられる
            cognitoResetMailAddress.setNewMailAddress(passcode);
        });

        //キャンセル押下
        Button cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(v -> {
            //遷移元のホーム画面に遷移
            //メールアドレス再設定画面を閉じる
            ButtonInfo positiveButtonInfo = new ButtonInfo();
            positiveButtonInfo.popupFunctions.add(new ActivityFinish(ResetMailAddressActivity.this));
            ButtonInfo negativeButtonInfo = new ButtonInfo();
            negativeButtonInfo.kindsButton = KindsButton.negative;
            ArrayList<ButtonInfo> multiButtonInfo = new ArrayList<>();
            multiButtonInfo.add(positiveButtonInfo);
            multiButtonInfo.add(negativeButtonInfo);
            Popup popup = new Popup(ResetMailAddressActivity.this, multiButtonInfo);
            popup.createPopup(getString(R.string.reset_mail_address_title),getString(R.string.reset_mail_address_cancel_message));
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
