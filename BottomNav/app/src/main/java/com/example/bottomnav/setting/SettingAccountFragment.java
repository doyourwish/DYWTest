package com.example.bottomnav.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bottomnav.R;
import com.example.bottomnav.common.FragmentHandler;
import com.example.bottomnav.common.UserMailAddress;
import com.example.bottomnav.home.MainActivity;
import com.example.bottomnav.setting.account.SendMailForResetPasswordActivity;


public class SettingAccountFragment extends FragmentHandler {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_account, container, false);

        // メールアドレスを表示
        UserMailAddress userMailAddress = new UserMailAddress(requireActivity());
        TextView textMailAddress = view.findViewById(R.id.mail_address);
        textMailAddress.setText(userMailAddress.getUserMailAddress());

        // 戻るボタン
        ImageButton return_button = view.findViewById(R.id.return_button);
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new SettingFragment());
            }
        });

        // パスワード再設定ボタン
        ImageButton reset_password_button = view.findViewById(R.id.password_button);
        reset_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main_activity = (MainActivity) getActivity();
                Intent intent = new Intent(main_activity, SendMailForResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }
}
