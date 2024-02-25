package com.example.bottomnav.start;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnav.R;
import com.example.bottomnav.editText.ConfirmInput;
import com.example.bottomnav.editText.FormMailAddress;
import com.example.bottomnav.editText.FormPassword;
import com.example.bottomnav.editText.LengthOver;
import com.example.bottomnav.editText.LiteralTypeLowerEnglish;
import com.example.bottomnav.editText.LiteralTypeNotFullWidth;
import com.example.bottomnav.editText.LiteralTypeNumber;
import com.example.bottomnav.editText.LiteralTypeUpperEnglish;
import com.example.bottomnav.editText.RuleMailAddress;
import com.example.bottomnav.editText.WhenChanged;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.Popup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //メールアドレス確認
        TextInputEditText mailAddressEditText = findViewById(R.id.register_mail_address_box);
        TextInputLayout mailAddressLayout = findViewById(R.id.layout_register_mail_address_box);

        FormMailAddress formMailAddress = new FormMailAddress(this);
        formMailAddress.createInputForm(mailAddressEditText, mailAddressLayout);

        //パスワード確認
        TextInputEditText passwordEditText = findViewById(R.id.register_password_box);
        TextInputLayout passwordLayout = findViewById(R.id.layout_register_password_box);

        FormPassword formPassword = new FormPassword(this);
        formPassword.createInputForm(passwordEditText, passwordLayout);

        //登録ボタン
        Button register_button = findViewById(R.id.register_register_button);
        register_button.setOnClickListener(v -> {
            //メールアドレス入力確認
            if(formMailAddress.getErrorMessage(mailAddressEditText) != null){
                Popup popup = new Popup(this,new ButtonInfo());
                popup.createPopup(getString(R.string.mail_address_re_input_title), formMailAddress.getErrorMessage(mailAddressEditText));
                return;
            }

            //パスワード入力確認
            if(formPassword.getErrorMessage(passwordEditText) != null){
                Popup popup = new Popup(this,new ButtonInfo());
                popup.createPopup(getString(R.string.password_re_input_title), formPassword.getErrorMessage(passwordEditText));
                return;
            }

            String email = mailAddressEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            //会員登録
            //実際に処理を行うのは、ConfirmRegisterActivity

            //新規登録確認画面に遷移
            //本来はこの画面でパスコードのメール送信をすべき
            //しかし、 パスコードメール送信 > パスワード再設定 のプロセスで共通のインスタンスを使用することや、
            //ネットワーク操作があるクラスは別のActivityにインスタンスを渡すためのSerializeができないため、
            //遷移先の画面でメール送信を行う
            Intent intent = new Intent(getApplication(), ConfirmRegisterActivity.class);
            intent.putExtra(getString(R.string.register_user_mail_address_key),email);
            intent.putExtra(getString(R.string.register_user_password_key),password);
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