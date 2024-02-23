package com.example.bottomnav.editText;

import android.app.Activity;

import com.example.bottomnav.R;

public class LiteralTypeLowerEnglish extends RuleLength {
    public LiteralTypeLowerEnglish(Activity activity){
        super(activity);
    }

    String getShowMessage(CharSequence text) {
        if(text.toString().matches(".*[a-z].*")){
            return null;
        }
        return activity.getString(R.string.literal_lower_english);
    }
}
