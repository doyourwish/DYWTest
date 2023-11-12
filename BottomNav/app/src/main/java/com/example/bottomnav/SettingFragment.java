package com.example.bottomnav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class SettingFragment extends FragmentHandler {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // 戻るボタン
        ImageButton return_button = view.findViewById(R.id.return_button);
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new HomeFragment());
            }
        });

        // アカウント設定ボタン
        ImageButton account_setting_button = view.findViewById(R.id.setting_button);
        account_setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new Setting_account_Fragment());
            }
        });

        // ユーザー情報設定ボタン
        ImageButton user_button= view.findViewById(R.id.user_button);
        user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new Setting_user_info_Fragment());
            }
        });

        // セキュリティ設定ボタン
        ImageButton security_button = view.findViewById(R.id.security_button);
        security_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new Setting_security_Fragment());
            }
        });

        // 外部サイト連携ボタン
        ImageButton connection_button = view.findViewById(R.id.connection_button);
        connection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new Setting_external_site_connection_Fragment());
            }
        });

        // アプリ説明ボタン
        ImageButton info_button = view.findViewById(R.id.info_button);
        info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new Setting_about_app_Fragment());
            }
        });

        return view;
    }
}


