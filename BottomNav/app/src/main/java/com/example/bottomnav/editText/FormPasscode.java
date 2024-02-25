package com.example.bottomnav.editText;

import android.app.Activity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FormPasscode extends InputForm{
    public FormPasscode(Activity activity){
        super(activity);
    }

    public boolean createInputForm(TextInputEditText editText, TextInputLayout layout){
        confirmInput.addRule(new LengthEqual(activity,6), WhenChanged.afterText);
        confirmInput.addRule(new LiteralTypeOnlyNumber(activity), WhenChanged.afterText);
        confirmInput.createConfirmInput(editText,layout);
        return true;
    }
}
