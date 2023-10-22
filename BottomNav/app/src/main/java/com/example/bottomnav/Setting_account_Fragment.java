package com.example.bottomnav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class Setting_account_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_account, container, false);

        // メールアドレスを表示
        UserMailAddress userMailAddress = new UserMailAddress((MainActivity) getActivity());
        TextView textMailAddress = view.findViewById(R.id.mail_address);
        textMailAddress.setText(userMailAddress.getUserMailAddress());

        // ボタンにクリックイベントを設定する
        ImageButton button = view.findViewById(R.id.return_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 遷移先のFragmentを作成する
                Fragment secondFragment = new SettingFragment();
                // FragmentManagerを使って遷移する
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, secondFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // パスワード再設定ボタン
        ImageButton password_button = view.findViewById(R.id.password_button);
        password_button.setOnClickListener(new View.OnClickListener() {
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
