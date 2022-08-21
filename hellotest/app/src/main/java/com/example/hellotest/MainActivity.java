package com.example.hellotest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private boolean buttonTap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ボタンを設定
        Button button = findViewById(R.id.button);

        // TextView の設定
        textView = findViewById(R.id.text_view);

        // lambda式で簡略化
        button.setOnClickListener( v -> {
            // flagがtrueの時
            if (buttonTap) {
                textView.setText(R.string.hello);
                buttonTap = false;
            }
            // flagがfalseの時
            else {
                textView.setText(R.string.world);
                buttonTap = true;
            }
        });
    }
}