package com.example.bottomnav.popup;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.example.bottomnav.R;

import java.util.ArrayList;

public class Popup {
    private final Activity activity;
    private ArrayList<ButtonInfo> multiButtonInfo = new ArrayList<>();


    public Popup(Activity activity, ButtonInfo buttonInfo){
        this.activity = activity;
        this.multiButtonInfo.add(buttonInfo);
    }

    public Popup(Activity activity, ArrayList<ButtonInfo> multiButtonInfo){
        this.activity = activity;
        this.multiButtonInfo = multiButtonInfo;
    }

    public boolean createPopup(String title,String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.AlertDialogStyle);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);

        for(ButtonInfo buttonInfo: multiButtonInfo) {
            PopupOnClickListener popupOnClickListener = new PopupOnClickListener();
            DialogInterface.OnClickListener onClickListener = popupOnClickListener.createOnClickListener(buttonInfo.popupFunctions);

            PopupButton popupButton;
            if(buttonInfo.kindsButton == KindsButton.positive){
                popupButton = new PositiveButton(alertDialogBuilder);
            }
            else if (buttonInfo.kindsButton == KindsButton.negative) {
                popupButton = new NegativeButton(alertDialogBuilder);
            }
            else if (buttonInfo.kindsButton == KindsButton.Neutral) {
                popupButton = new NeutralButton(alertDialogBuilder);
            }
            else {
                Log.e("Error","[Popup.createPopup]KindsButton is illegal.");
                return false;
            }

            if(buttonInfo.message != null) {
                popupButton.setButton(onClickListener, buttonInfo.message);
            }
            else{
                popupButton.setButton(onClickListener);
            }
        }

        alertDialogBuilder.create();
        alertDialogBuilder.show();
        return true;
    }
}
