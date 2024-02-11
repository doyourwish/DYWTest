package com.example.bottomnav.popup;

import android.app.Activity;

public abstract class PopupFunction {

    protected Activity activity;

    PopupFunction(Activity activity){
        this.activity = activity;
    }

    abstract boolean executeFunction();
}
