package com.example.bottomnav.common;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.example.bottomnav.R;
import com.example.bottomnav.cognito.CognitoDeleteUser;
import com.example.bottomnav.cognito.CognitoLogin;
import com.example.bottomnav.start.LoginActivity;

//TODO:クラス構造検討
//ポップアップ参考:https://qiita.com/ohwada/items/f95518b6948b271433a3

//TODO:メッセージの管理方法、エラーコード設定検討
public class Popup {

    private Activity activity;

    public Popup(Activity activity){
        this.activity = activity;
    }
    public boolean showPopup(String title, String message) {
        if(activity == null){
            Log.e("Popup.showPopup","activity is null");
            return false;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(activity, R.style.AlertDialogStyle)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create();

        alertDialog.show();
        return true;
    }

    public boolean showPopupWithActivityFinish(String title, String message){
        if(activity == null){
            Log.e("Popup.showPopupWithActivityFinish","activity is null");
            return false;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(activity, R.style.AlertDialogStyle)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finish();
                    }
                })
                .setCancelable(false)
                .create();

        alertDialog.show();
        return true;
    }

    public boolean showPopupTwoButtonWithActivityFinish(String title, String message) {
        if(activity == null){
            Log.e("Popup.showPopupTwoButtonWithActivityFinish","activity is null");
            return false;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(activity, R.style.AlertDialogStyle)
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
                .setCancelable(false)
                .create();

        alertDialog.show();
        return true;
    }

    public boolean showPopupWithActivityChange(String title, String message,Class<?> cls){
        if(activity == null){
            Log.e("Popup.showPopupWithActivityChange","activity is null");
            return false;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(activity, R.style.AlertDialogStyle)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(activity.getApplication(), cls);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                })
                .setCancelable(false)
                .create();

        alertDialog.show();
        return true;
    }

    public boolean showPopupWithActivityLogin(String title, String message){
        if(activity == null){
            Log.e("Popup.showPopupWithActivityLogin","activity is null");
            return false;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(activity, R.style.AlertDialogStyle)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //サインアウト
                        CognitoLogin cognitoLogin = new CognitoLogin(activity);
                        UserMailAddress userMailAddress = new UserMailAddress(activity);
                        cognitoLogin.signOut(userMailAddress.getUserMailAddress());

                        dialog.dismiss();
                        Intent intent = new Intent(activity.getApplication(), LoginActivity.class);
                        //積み重ねたAcvitityを全削除
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                })
                .setCancelable(false)
                .create();

        alertDialog.show();
        return true;
    }

    public boolean showTwoPopupWithDeleteUser(String first_title, String first_message){
        if(activity == null){
            Log.e("Popup.showTwoPopupWithActivityRegister","activity is null");
            return false;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(activity, R.style.AlertDialogStyle)
                .setTitle(first_title)
                .setMessage(first_message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //データ削除
                        //TODO:DBから特定のユーザーデータを削除
                        UserMailAddress userMailAddress = new UserMailAddress(activity);
                        CognitoDeleteUser cognitoDeleteUser = new CognitoDeleteUser(activity);
                        cognitoDeleteUser.deleteUser(userMailAddress.getUserMailAddress());
                        userMailAddress.saveUserMailAddress(null);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create();

        alertDialog.show();
        return true;
    }
}
