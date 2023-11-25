package com.example.bottomnav.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bottomnav.R;
import com.example.bottomnav.common.FragmentHandler;
import com.example.bottomnav.home.HomeFragment;


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
                changeFragment(new SettingAccountFragment());
            }
        });

        //アカウントテキスト
        TextView account_textView = view.findViewById(R.id.account_text);
        account_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new SettingAccountFragment());
            }
        });

        // ユーザー情報設定ボタン
        ImageButton user_button= view.findViewById(R.id.user_button);
        user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new SettingUserInfoFragment());
            }
        });

        // ユーザー情報設定テキスト
        TextView user_info_textView = view.findViewById(R.id.user_info_text);
        user_info_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new SettingUserInfoFragment());
            }
        });

        // セキュリティ設定ボタン
        ImageButton security_button = view.findViewById(R.id.security_button);
        security_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new SettingSecurityFragment());
            }
        });

        // セキュリティ設定テキスト
        TextView security_textView = view.findViewById(R.id.security_text);
        security_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new SettingSecurityFragment());
            }
        });

        // 外部サイト連携ボタン
        ImageButton connection_button = view.findViewById(R.id.connection_button);
        connection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new SettingExternalSiteConnectionFragment());
            }
        });

        // 外部サイト連携テキスト
        TextView connection_textView = view.findViewById(R.id.connection_text);
        connection_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new SettingExternalSiteConnectionFragment());
            }
        });

        // アプリ説明ボタン
        ImageButton info_button = view.findViewById(R.id.info_button);
        info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new SettingAboutAppFragment());
            }
        });

        // アプリ説明テキスト
        TextView info_textView = view.findViewById(R.id.info_text);
        info_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new SettingAboutAppFragment());
            }
        });
        return view;
    }
}


