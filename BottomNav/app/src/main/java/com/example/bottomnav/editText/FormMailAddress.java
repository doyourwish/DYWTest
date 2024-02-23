package com.example.bottomnav.editText;

import android.app.Activity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FormMailAddress extends InputForm{
    public FormMailAddress(Activity activity){
        super(activity);
    }

    public boolean createInputForm(TextInputEditText editText, TextInputLayout layout){
        confirmInput.addRule(new RuleMailAddress(activity), WhenChanged.afterText);
        confirmInput.createConfirmInput(editText,layout);
        return true;
    }
}
