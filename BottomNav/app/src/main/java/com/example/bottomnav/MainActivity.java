package com.example.bottomnav;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    private final FragmentHandler fragmentHandler = new FragmentHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // アクティビティ生成が初回の場合は、ホーム画面に遷移
        if (savedInstanceState == null) {
            fragmentHandler.setFragmentManager(getSupportFragmentManager());
            fragmentHandler.changeFragment(R.id.home);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        // 画面最下部のボタン表示
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return fragmentHandler.changeFragment(item.getItemId());
            }

        });
    }

    @Override
    public void onBackPressed() {
    }
}

