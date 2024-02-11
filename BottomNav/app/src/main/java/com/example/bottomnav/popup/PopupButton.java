package com.example.bottomnav.popup;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public abstract class PopupButton{

    protected AlertDialog.Builder alertDialogBuilder;

    PopupButton(AlertDialog.Builder alertDialogBuilder){
        this.alertDialogBuilder = alertDialogBuilder;
    }

    abstract AlertDialog.Builder setButton(DialogInterface.OnClickListener onClickListener, String message);

    abstract AlertDialog.Builder setButton(DialogInterface.OnClickListener onClickListener);
}
