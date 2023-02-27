package com.websarva.wings.android.screentransition;//package your.package.name;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendButton = findViewById(R.id.send_button);
        // lambda式
        sendButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), SubActivity.class);
            startActivity(intent);
        });



public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendButton = findViewById(R.id.skip_button);
        sendButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), SubActivity.class);
            startActivity(intent);
                });
    }
}