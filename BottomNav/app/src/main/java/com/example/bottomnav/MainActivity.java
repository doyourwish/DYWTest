package com.example.bottomnav;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;



public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment=new HomeFragment();
    ClosetFragment closetFragment=new ClosetFragment();
    CalendarFragment calendarFragment=new CalendarFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView =findViewById(R.id.nav);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // BackStackを設定
            fragmentTransaction.addToBackStack(null);

            // counterをパラメータとして設定
            int count = 0;
            fragmentTransaction.replace(R.id.container,HomeFragment.newInstance(count));

            fragmentTransaction.commit();
        }


        HomeFragment Homefragment = new HomeFragment();
        FragmentTransaction swtransaction = getSupportFragmentManager().beginTransaction();
        swtransaction.add(R.id.container, homeFragment);
        swtransaction.commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;

                    case R.id.closet:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, closetFragment).commit();
                        return true;

                    case R.id.calendar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, calendarFragment).commit();
                        return true;

                }
                return false;
            }

        });

        // Set the default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                new HomeFragment()).commit();
    }
}

