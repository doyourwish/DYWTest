package com.example.bottomnav.popup;

import android.app.Activity;

public abstract class PopupActivity extends PopupFunction{
    PopupActivity(Activity activity){
        super(activity);
    }

    abstract boolean executeFunction();
}
