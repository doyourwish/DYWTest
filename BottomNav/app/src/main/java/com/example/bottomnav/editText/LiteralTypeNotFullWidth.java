package com.example.bottomnav.editText;

import android.app.Activity;

import com.example.bottomnav.R;

public class LiteralTypeNotFullWidth extends RuleLength {
    public LiteralTypeNotFullWidth(Activity activity){
        super(activity);
    }

    String getShowMessage(CharSequence text) {
        for (int i = 0; i < text.toString().length(); i++) {
            char c = text.toString().charAt(i);
            if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
                return activity.getString(R.string.literal_not_full_width);
            }
        }
        return null;
    }
}
