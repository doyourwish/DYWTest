package com.example.bottomnav.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnav.R;
import com.example.bottomnav.cognito.CognitoSignUp;
import com.example.bottomnav.editText.FormPasscode;
import com.example.bottomnav.popup.ActivityFinish;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.KindsButton;
import com.example.bottomnav.popup.Popup;
import com.example.bottomnav.setting.account.UserAccountQAActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ConfirmRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_register);

        //メールアドレス取得
        Intent intent = getIntent();
        String mailAddress = intent.getStringExtra(getString(R.string.register_user_mail_address_key));
        String password = intent.getStringExtra(getString(R.string.register_user_password_key));

        //パスコード送信
        CognitoSignUp cognitoSignUp = new CognitoSignUp(ConfirmRegisterActivity.this);
        cognitoSignUp.signUpUser(mailAddress,password);

        //パスコード確認
        TextInputEditText passcodeEditText = findViewById(R.id.passcode_box);
        TextInputLayout passcodeLayout = findViewById(R.id.layout_passcode_box);

        FormPasscode formPasscode = new FormPasscode(this);
        formPasscode.createInputForm(passcodeEditText, passcodeLayout);

        //登録押下
        Button register_button = findViewById(R.id.register_register_button);
        register_button.setOnClickListener(v -> {
            //パスコード入力確認
            if(formPasscode.getErrorMessage(passcodeEditText) != null){
                Popup popup = new Popup(this,new ButtonInfo());
                popup.createPopup(getString(R.string.passcode_re_input_title), formPasscode.getErrorMessage(passcodeEditText));
                return;
            }

            String passcode = passcodeEditText.getText().toString();

            //新規登録確認
            //成功の場合、新規登録確認画面を閉じる
            cognitoSignUp.confirmSignUp(passcode);
        });

        //戻る押下
        Button return_button = findViewById(R.id.register_return_button);
        return_button.setOnClickListener(v -> {
            //遷移元のホーム画面、ログイン画面に遷移
            //パスワード再設定画面を閉じる
            ButtonInfo positiveButtonInfo = new ButtonInfo();
            positiveButtonInfo.popupFunctions.add(new ActivityFinish(ConfirmRegisterActivity.this));
            ButtonInfo negativeButtonInfo = new ButtonInfo();
            negativeButtonInfo.kindsButton = KindsButton.negative;
            ArrayList<ButtonInfo> multiButtonInfo = new ArrayList<>();
            multiButtonInfo.add(positiveButtonInfo);
            multiButtonInfo.add(negativeButtonInfo);
            Popup popup = new Popup(ConfirmRegisterActivity.this, multiButtonInfo);
            popup.createPopup(getString(R.string.register_confirm_cancel_title),getString(R.string.register_confirm_cancel_message));
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