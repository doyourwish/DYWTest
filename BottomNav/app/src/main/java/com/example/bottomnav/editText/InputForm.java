package com.example.bottomnav.editText;

import android.app.Activity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public abstract class InputForm {
    protected Activity activity;

    protected ConfirmInput confirmInput;

    public InputForm(Activity activity){
        this.activity = activity;
        this.confirmInput = new ConfirmInput(activity);
    }

    public abstract boolean createInputForm(TextInputEditText editText, TextInputLayout layout);

    public String getErrorMessage(TextInputEditText editText){
        return confirmInput.getErrorMessage(editText);
    }

}
