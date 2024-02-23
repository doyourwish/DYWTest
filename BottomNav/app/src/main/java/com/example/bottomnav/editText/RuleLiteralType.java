package com.example.bottomnav.editText;

import android.app.Activity;

public abstract class RuleLiteralType extends InputRule{
    RuleLiteralType(Activity activity){
        super(activity);
    }
    abstract String getShowMessage(CharSequence text);
}
