package com.example.bottomnav.editText;

import android.app.Activity;

public abstract class InputRule {
    protected Activity activity;

    public InputRule(Activity activity){
        this.activity = activity;
    }

    abstract String getShowMessage(CharSequence text);
}
