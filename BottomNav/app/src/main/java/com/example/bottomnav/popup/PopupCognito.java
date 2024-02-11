package com.example.bottomnav.popup;

import android.app.Activity;

public abstract class PopupCognito extends PopupFunction{

    PopupCognito(Activity activity){
        super(activity);
    }

    abstract boolean executeFunction();
}
