package com.example.bottomnav.editText;

import android.app.Activity;

import com.example.bottomnav.R;

public class LiteralTypeOnlyNumber extends RuleLiteralType {
    public LiteralTypeOnlyNumber(Activity activity){
        super(activity);
    }

    String getShowMessage(CharSequence text) {
        if(text.toString().matches("\\d+")){
            return null;
        }
        return activity.getString(R.string.literal_only_number);
    }
}
