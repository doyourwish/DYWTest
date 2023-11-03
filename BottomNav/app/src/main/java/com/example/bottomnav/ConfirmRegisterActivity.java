package com.example.bottomnav;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_register);

        Popup popup = new Popup(ConfirmRegisterActivity.this);

        //メールアドレス取得
        Intent intent = getIntent();
        String mailAddress = intent.getStringExtra(getString(R.string.register_user_mail_address_key));
        String password = intent.getStringExtra(getString(R.string.register_user_password_key));

        //パスコード送信
        CognitoSignUp cognitoSignUp = new CognitoSignUp(ConfirmRegisterActivity.this);
        cognitoSignUp.signUpUser(mailAddress,password);

        //登録押下
        EditText text_passcode = findViewById(R.id.passcode_box);
        Button register_button = findViewById(R.id.register_register_button);
        register_button.setOnClickListener(v -> {
            String passcode = text_passcode.getText().toString();

            //新規登録確認
            //成功の場合、新規登録確認画面を閉じる
            cognitoSignUp.confirmSignUp(passcode);
        });

        //戻る押下
        Button return_button = findViewById(R.id.register_return_button);
        return_button.setOnClickListener(v -> {
            //遷移元のホーム画面、ログイン画面に遷移
            //パスワード再設定画面を閉じる
            popup.showPopupTwoButtonWithActivityFinish("キャンセル確認","会員登録を中止しますか？");
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