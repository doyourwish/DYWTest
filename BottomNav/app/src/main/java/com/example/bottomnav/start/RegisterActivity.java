package com.example.bottomnav.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnav.R;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.Popup;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //登録ボタン
        EditText text_mail_address_1 = findViewById(R.id.register_mail_address_box_1);
        EditText text_mail_address_2 = findViewById(R.id.register_mail_address_box_2);
        EditText text_password_1 = findViewById(R.id.register_password_box_1);
        EditText text_password_2 = findViewById(R.id.register_password_box_2);

        Button register_button = findViewById(R.id.register_register_button);
        register_button.setOnClickListener(v -> {
            String mail_address_1 = text_mail_address_1.getText().toString();
            String mail_address_2 = text_mail_address_2.getText().toString();
            String password_1 = text_password_1.getText().toString();
            String password_2 = text_password_2.getText().toString();

            //メールアドレス確認
            if(!mail_address_1.equals(mail_address_2)){
                ButtonInfo buttonInfo = new ButtonInfo();
                Popup popup = new Popup(RegisterActivity.this, buttonInfo);
                popup.createPopup("新規会員登録", "「メールアドレス」と「確認用メールアドレス」が一致しません");
                return;
            }

            //パスワード確認
            if(!password_1.equals(password_2)){
                ButtonInfo buttonInfo = new ButtonInfo();
                Popup popup = new Popup(RegisterActivity.this, buttonInfo);
                popup.createPopup("新規会員登録", "「パスワード」と「確認用パスワード」が一致しません");
                return;
            }

            //会員登録
            //実際に処理を行うのは、ConfirmRegisterActivity

            //新規登録確認画面に遷移
            //本来はこの画面でパスコードのメール送信をすべき
            //しかし、 パスコードメール送信 > パスワード再設定 のプロセスで共通のインスタンスを使用することや、
            //ネットワーク操作があるクラスは別のActivityにインスタンスを渡すためのSerializeができないため、
            //遷移先の画面でメール送信を行う
            Intent intent = new Intent(getApplication(), ConfirmRegisterActivity.class);
            intent.putExtra(getString(R.string.register_user_mail_address_key),mail_address_1);
            intent.putExtra(getString(R.string.register_user_password_key),password_1);
            startActivity(intent);
            //新規登録画面を閉じる
            finish();
        });

        //戻るボタン
        Button return_button = findViewById(R.id.register_return_button);
        return_button.setOnClickListener(v -> {
            //遷移元のアプリ説明画面に遷移
            //新規登録画面を閉じる
            finish();
        });

        //こちら
        TextView qa_textView = findViewById(R.id.register_already_register_click);
        qa_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //引き継ぎ画面(ログイン画面)に遷移
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}