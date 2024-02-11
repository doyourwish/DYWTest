package com.example.bottomnav.popup;

import android.app.Activity;

public class ActivityFinish extends PopupActivity {

    public ActivityFinish(Activity activity) {
        super(activity);
    }

    boolean executeFunction(){
        activity.finish();
        return true;
    }
}
