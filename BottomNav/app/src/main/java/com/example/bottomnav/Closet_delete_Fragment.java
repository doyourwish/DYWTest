package com.example.bottomnav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Closet_delete_Fragment extends FragmentHandler {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet_delete, container, false);

        // 表示ボタン
        Button display_button = view.findViewById(R.id.display);
        display_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new ClosetFragment());
            }
        });

        // 登録ボタン
        Button register_button = view.findViewById(R.id.register);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new Closet_register_Fragment());
            }
        });

        // 削除ボタン
        Button delete_button = view.findViewById(R.id.delete);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new Closet_delete_Fragment());
            }
        });
        return view;
    }
}