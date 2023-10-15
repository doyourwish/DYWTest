package com.example.bottomnav;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

public class UserMailAddress {
    SharedPreferences dataStore;
    String preferencesFile = "DataStore";
    private String keyMailAddress = "mail_address";

    UserMailAddress(Activity activity){
        dataStore = activity.getSharedPreferences(preferencesFile, MODE_PRIVATE);
    }

    public boolean saveUserMailAddress(String mail_address){
        SharedPreferences.Editor editor = dataStore.edit();
        editor.putString(keyMailAddress, mail_address);
        editor.commit();
        return true;
    }

    public String getUserMailAddress(){
        return dataStore.getString(keyMailAddress, null);
    }
}
