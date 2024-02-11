package com.example.bottomnav.popup;

import android.app.Activity;
import android.content.Intent;

public class ActivityChange extends PopupActivity {

    private boolean isAllActivityFinish = false;

    private final Class<?> changeClass;

    public ActivityChange(Activity activity, Class<?> changeClass) {
        super(activity);
        this.changeClass = changeClass;
    }

    public ActivityChange(Activity activity, Class<?> changeClass, boolean isAllActivityFinish) {
        super(activity);
        this.changeClass = changeClass;
        this.isAllActivityFinish = isAllActivityFinish;
    }

    boolean executeFunction(){
        Intent intent = new Intent(activity.getApplication(), changeClass);
        if(isAllActivityFinish){
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        activity.startActivity(intent);
        activity.finish();
        return true;
    }
}
