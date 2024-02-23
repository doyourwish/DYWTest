package com.example.bottomnav.editText;

import android.app.Activity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FormPassword extends InputForm{
    public FormPassword(Activity activity){
        super(activity);
    }

    public boolean createInputForm(TextInputEditText editText, TextInputLayout layout){
        confirmInput.addRule(new LengthOver(activity,8), WhenChanged.afterText);
        confirmInput.addRule(new LiteralTypeUpperEnglish(activity), WhenChanged.afterText);
        confirmInput.addRule(new LiteralTypeLowerEnglish(activity), WhenChanged.afterText);
        confirmInput.addRule(new LiteralTypeNumber(activity), WhenChanged.afterText);
        confirmInput.addRule(new LiteralTypeNotFullWidth(activity), WhenChanged.afterText);
        confirmInput.createConfirmInput(editText,layout);
        return true;
    }
}
