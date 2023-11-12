package com.example.bottomnav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Image_Fragment extends FragmentHandler {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        // アルバムから服装取得ボタン
        Button album_button = view.findViewById(R.id.album);
        album_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new Closet_register_Fragment());
            }
        });

        // カメラから服装取得ボタン
        Button camera_button = view.findViewById(R.id.camera);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new Closet_register_Fragment());
            }
        });

        return view;
    }

}