package com.example.bottomnav.editText;

import android.app.Activity;

import com.example.bottomnav.R;

public class RuleMailAddress extends InputRule{
    public RuleMailAddress(Activity activity){
        super(activity);
    }
    String getShowMessage(CharSequence text){
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()){
            return null;
        }
        return activity.getString(R.string.literal_mail_address);
    };
}
