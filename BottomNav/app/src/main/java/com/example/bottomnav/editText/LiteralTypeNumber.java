package com.example.bottomnav.editText;

import android.app.Activity;

import com.example.bottomnav.R;

public class LiteralTypeNumber extends RuleLiteralType {
    public LiteralTypeNumber(Activity activity){
        super(activity);
    }

    String getShowMessage(CharSequence text) {
        if(text.toString().matches(".*[0-9].*")){
            return null;
        }
        return activity.getString(R.string.literal_number);
    }
}
