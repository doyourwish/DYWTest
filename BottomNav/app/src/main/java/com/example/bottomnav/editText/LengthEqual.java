package com.example.bottomnav.editText;

import android.app.Activity;

import com.example.bottomnav.R;

public class LengthEqual extends RuleLength {

    private final int limitLength;

    public LengthEqual(Activity activity, int limitLength){
        super(activity);
        this.limitLength = limitLength;
    }
    String getShowMessage(CharSequence text) {
        if(text.toString().length() == limitLength){
            return null;
        }
        return String.valueOf(limitLength) + activity.getString(R.string.length_over);
    }
}
