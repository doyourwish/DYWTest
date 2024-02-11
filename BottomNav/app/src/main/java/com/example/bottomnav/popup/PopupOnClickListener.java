package com.example.bottomnav.popup;

import android.content.DialogInterface;

import java.util.ArrayList;

public class PopupOnClickListener {

    public DialogInterface.OnClickListener createOnClickListener(ArrayList<PopupFunction> popupFunctions){
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                for(PopupFunction popupFunction: popupFunctions) {
                    popupFunction.executeFunction();
                }
            }
        };
        return onClickListener;
    }
}
