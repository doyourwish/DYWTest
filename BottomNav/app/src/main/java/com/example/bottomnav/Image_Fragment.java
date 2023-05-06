package com.example.bottomnav;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Image_Fragment extends Fragment {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
// 親クラスの同名メソッドの呼び出し。
        super.onActivityResult(requestCode, resultCode, data);
// カメラアプリとの連携からの戻りでかつ撮影成功の場合。
        if(requestCode == 200 && resultCode == RESULT_OK) {
// 撮影された画像のビットマップデータを取得。
            Bitmap bitmap = data.getParcelableExtra("data");
            ImageView ivCamera = findViewById(R.id.camera);
// 撮影された画像をImageViewに設定。
            ivCamera.setImageBitmap(bitmap);
        } }

    public void onCameraImageClick(View view) {
// Intentオブジェクトを生成。
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 200);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageButton button = view.findViewById(R.id.return_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 遷移先のFragmentを作成する
                Fragment secondFragment = new Closet_register_Fragment();
                // FragmentManagerを使って遷移する
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, secondFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;


    }
}