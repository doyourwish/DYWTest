package com.example.bottomnav;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Login_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button Button = findViewById(R.id.forget_button);
        // lambda式
        Button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), Login_Forget.class);
            startActivity(intent);
        });

        Button Button2 = findViewById(R.id.register_button);
        // lambda式
        Button2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), Register.class);
            startActivity(intent);
        });

        Button Button3 = findViewById(R.id.login);
        // lambda式
        Button3.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
        });

        final CheckBox checkbox = (CheckBox)findViewById(R.id.checkbox);
        checkbox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkbox.isChecked() == true) {
                }
                else {
                }
            }
        });
    }
}





