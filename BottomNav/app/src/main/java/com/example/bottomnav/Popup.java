package com.example.bottomnav;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

//TODO:クラス構造検討
//ポップアップ参考:https://qiita.com/ohwada/items/f95518b6948b271433a3

//TODO:メッセージの管理方法、エラーコード設定検討
public class Popup {

    private Activity activity;

    Popup(Activity activity){
        this.activity = activity;
    }
    public boolean showPopup(String title, String message) {
        if(activity == null){
            Log.e("Popup.showPopup","activity is null");
            return false;
        }
        android.app.AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        alertDialog.show();
        return true;
    }

    public boolean showPopupWithActivityFinish(String title, String message){
        if(activity == null){
            Log.e("Popup.showPopupWithActivityFinish","activity is null");
            return false;
        }
        android.app.AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finish();
                    }
                })
                .create();

        alertDialog.show();
        return true;
    }

    public boolean showPopupTwoButtonWithActivityFinish(String title, String message) {
        if(activity == null){
            Log.e("Popup.showPopup","activity is null");
            return false;
        }
        android.app.AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        alertDialog.show();
        return true;
    }
}
