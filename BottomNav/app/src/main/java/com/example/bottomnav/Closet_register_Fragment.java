package com.example.bottomnav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class Closet_register_Fragment extends FragmentHandler {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet_register, container, false);

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

        // 服装追加ボタン
        ImageButton add_clothing_button = view.findViewById(R.id.button);
        add_clothing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new Image_Fragment());
            }
        });

        return view;
    }
}