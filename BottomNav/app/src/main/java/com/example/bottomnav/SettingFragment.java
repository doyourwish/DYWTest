package com.example.bottomnav;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class SettingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        // ボタンにクリックイベントを設定する
        ImageButton button = view.findViewById(R.id.return_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 遷移先のFragmentを作成する
                Fragment secondFragment = new HomeFragment();
                // FragmentManagerを使って遷移する
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, secondFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ImageButton button2 = view.findViewById(R.id.setting_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 遷移先のFragmentを作成する
                Fragment secondFragment2 = new Setting_account_Fragment();
                // FragmentManagerを使って遷移する
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, secondFragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ImageButton button3= view.findViewById(R.id.user_button);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 遷移先のFragmentを作成する
                Fragment secondFragment2 = new Setting_user_info_Fragment();
                // FragmentManagerを使って遷移する
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, secondFragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ImageButton button4= view.findViewById(R.id.security_button);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 遷移先のFragmentを作成する
                Fragment secondFragment2 = new Setting_security_Fragment();
                // FragmentManagerを使って遷移する
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, secondFragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ImageButton button5= view.findViewById(R.id.connection_button);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 遷移先のFragmentを作成する
                Fragment secondFragment2 = new Setting_external_site_connection_Fragment();
                // FragmentManagerを使って遷移する
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, secondFragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ImageButton button6= view.findViewById(R.id.info_button);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 遷移先のFragmentを作成する
                Fragment secondFragment2 = new Setting_about_app_Fragment();
                // FragmentManagerを使って遷移する
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, secondFragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });




        return view;



    }
}


