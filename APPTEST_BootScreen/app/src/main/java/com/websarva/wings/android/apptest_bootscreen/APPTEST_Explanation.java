package com.websarva.wings.android.apptest_bootscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class APPTEST_Explanation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apptest_explanation);

        Button button = findViewById(R.id.bt_explanation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(APPTEST_Explanation.this, APPTEST_Register_Maill.class);
                startActivity(intent);
            }
        });


    }
}