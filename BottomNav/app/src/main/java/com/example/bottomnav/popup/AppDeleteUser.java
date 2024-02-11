package com.example.bottomnav.popup;

import android.app.Activity;

import com.example.bottomnav.cognito.CognitoDeleteUser;
import com.example.bottomnav.cognito.CognitoLogin;
import com.example.bottomnav.common.UserMailAddress;

public class AppDeleteUser extends PopupCognito {

    public AppDeleteUser(Activity activity) {
        super(activity);
    }

    boolean executeFunction(){
        //データ削除
        //TODO:DBから特定のユーザーデータを削除
        UserMailAddress userMailAddress = new UserMailAddress(activity);
        CognitoDeleteUser cognitoDeleteUser = new CognitoDeleteUser(activity);
        cognitoDeleteUser.deleteUser(userMailAddress.getUserMailAddress());
        userMailAddress.saveUserMailAddress(null);
        return true;
    }
}
