package com.example.bottomnav.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.bottomnav.R;
import com.example.bottomnav.common.FragmentHandler;
import com.example.bottomnav.setting.SettingFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HomeFragment extends FragmentHandler {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 広告表示
        AdView adView = view.findViewById(R.id.advertisement);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // 設定ボタン
        ImageButton setting_button = view.findViewById(R.id.setting_button);
        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new SettingFragment());
            }
        });

        // 通知ボタン
        ImageButton notification_button= view.findViewById(R.id.notification_button);
        notification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new NotificationFragment());
            }
        });

        return view;
    }
}



