package com.example.bottomnav.editText;

import android.app.Activity;

import com.example.bottomnav.R;

public class LiteralTypeUpperEnglish extends RuleLiteralType {
    public LiteralTypeUpperEnglish(Activity activity){
        super(activity);
    }

    String getShowMessage(CharSequence text) {
        if(text.toString().matches(".*[A-Z].*")){
            return null;
        }
        return activity.getString(R.string.literal_upper_english);
    }
}
