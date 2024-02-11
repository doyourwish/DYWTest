package com.example.bottomnav.popup;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.bottomnav.R;

public class PositiveButton extends PopupButton{

    public PositiveButton(AlertDialog.Builder alertDialogBuilder){
        super(alertDialogBuilder);
    }

    AlertDialog.Builder setButton(DialogInterface.OnClickListener onClickListener, String message){
        alertDialogBuilder.setPositiveButton(message,onClickListener);
        return alertDialogBuilder;
    };

    AlertDialog.Builder setButton(DialogInterface.OnClickListener onClickListener){
        return setButton(onClickListener,alertDialogBuilder.getContext().getString(R.string.popup_button_positive));
    };
}
