package com.example.bottomnav.editText;

import android.app.Activity;

public abstract class RuleLength extends InputRule{
    public RuleLength(Activity activity){
        super(activity);
    }

    abstract String getShowMessage(CharSequence text);
}
