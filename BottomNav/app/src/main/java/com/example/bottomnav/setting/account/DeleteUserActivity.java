package com.example.bottomnav.setting.account;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnav.R;
import com.example.bottomnav.common.Popup;
import com.example.bottomnav.common.UserMailAddress;

public class DeleteUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        Popup popup = new Popup(DeleteUserActivity.this);
        UserMailAddress userMailAddress = new UserMailAddress(DeleteUserActivity.this);

        //メールアドレス表示
        String mailAddress = userMailAddress.getUserMailAddress();
        if(mailAddress == null){
            Log.d("mail_address","mail_address is null because you didn't log in");
            popup.showPopupWithActivityFinish("mail_address","mail_address is null because you didn't log in");
        }
        else {
            TextView loginEmailTextView = findViewById(R.id.login_email_text);
            loginEmailTextView.setText(mailAddress);
        }

        //退会確認チェックボックス
        CheckBox checkBox = findViewById(R.id.checkbox_delete_user_agree);

        //退会ボタン
        Button delete_user_button = findViewById(R.id.delete_user_button);
        delete_user_button.setOnClickListener(v -> {
            if(checkBox.isChecked()){
                popup.showTwoPopupWithDeleteUser("退会確認","本当に退会しますか？");
            }
            else{
                popup.showPopup("退会","退会に同意する場合は、チェックボックスにチェックを入れてください");
            }
        });

        //キャンセルボタン
        Button cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(v -> {
            //退会画面を閉じる
            finish();
        });
    }
}