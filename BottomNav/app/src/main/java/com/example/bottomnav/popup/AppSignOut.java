package com.example.bottomnav.popup;

import android.app.Activity;

import com.example.bottomnav.cognito.CognitoLogin;
import com.example.bottomnav.common.UserMailAddress;

public class AppSignOut extends PopupCognito {

    public AppSignOut(Activity activity) {
        super(activity);
    }

    boolean executeFunction(){
        CognitoLogin cognitoLogin = new CognitoLogin(activity);
        UserMailAddress userMailAddress = new UserMailAddress(activity);
        cognitoLogin.signOut(userMailAddress.getUserMailAddress());
        return true;
    }
}
