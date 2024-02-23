package com.example.bottomnav.start;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnav.R;
import com.example.bottomnav.cognito.CognitoLogin;
import com.example.bottomnav.common.UserMailAddress;
import com.example.bottomnav.editText.ConfirmInput;
import com.example.bottomnav.editText.FormMailAddress;
import com.example.bottomnav.editText.FormPassword;
import com.example.bottomnav.editText.InputForm;
import com.example.bottomnav.editText.LengthOver;
import com.example.bottomnav.editText.LiteralTypeLowerEnglish;
import com.example.bottomnav.editText.LiteralTypeNotFullWidth;
import com.example.bottomnav.editText.LiteralTypeNumber;
import com.example.bottomnav.editText.LiteralTypeUpperEnglish;
import com.example.bottomnav.editText.RuleMailAddress;
import com.example.bottomnav.editText.WhenChanged;
import com.example.bottomnav.home.MainActivity;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.Popup;
import com.example.bottomnav.setting.account.SendMailForResetPasswordActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 既にログインしてる場合、自動ログインして画面を閉じる
        UserMailAddress userMailAddress = new UserMailAddress(LoginActivity.this);
        if(userMailAddress.getUserMailAddress() != null){
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // パスワード忘却テキスト
        TextView forget_text = findViewById(R.id.login_forget_text);
        forget_text.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), SendMailForResetPasswordActivity.class);
            startActivity(intent);
        });

        // アカウント登録テキスト
        TextView register_text = findViewById(R.id.login_register_text);
        register_text.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), RegisterActivity.class);
            startActivity(intent);
        });


        //メールアドレス確認
        TextInputEditText mailAddressEditText = findViewById(R.id.login_mail_box);
        TextInputLayout mailAddressLayout = findViewById(R.id.layout_login_mail_box);

        FormMailAddress formMailAddress = new FormMailAddress(this);
        formMailAddress.createInputForm(mailAddressEditText, mailAddressLayout);

        //パスワード確認
        TextInputEditText passwordEditText = findViewById(R.id.login_password_box);
        TextInputLayout passwordLayout = findViewById(R.id.layout_login_password_box);

        FormPassword formPassword = new FormPassword(this);
        formPassword.createInputForm(passwordEditText, passwordLayout);

        // ログインボタン
        Button login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(v -> {
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
            //login
            //パスワード入力を反映させるために、一度サインアウト
            CognitoLogin cognitoLogin = new CognitoLogin(LoginActivity.this);
            cognitoLogin.signOut(email);
            cognitoLogin.login(email,password);
        });
    }
}
