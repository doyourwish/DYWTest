package com.example.bottomnav.cognito;

import android.app.Activity;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.UpdateAttributesHandler;
import com.amazonaws.services.cognitoidentityprovider.model.AliasExistsException;
import com.amazonaws.services.cognitoidentityprovider.model.CodeMismatchException;
import com.amazonaws.services.cognitoidentityprovider.model.NotAuthorizedException;
import com.example.bottomnav.R;
import com.example.bottomnav.common.UserMailAddress;
import com.example.bottomnav.popup.ActivityChange;
import com.example.bottomnav.popup.ActivityFinish;
import com.example.bottomnav.popup.AppSignOut;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.Popup;
import com.example.bottomnav.start.LoginActivity;

import java.util.List;

public class CognitoResetMailAddress extends CognitoManager{

    public CognitoResetMailAddress(Activity activity) {
        super(activity);
    }

    public boolean sendSetMailAddressCode(String newMailAddress) {

        //ログイン中のアカウントを特定
        UserMailAddress userMailAddress = new UserMailAddress(activity);
        String oldUsername = userMailAddress.getUserMailAddress();
        cognitoUser = cognitoUserPool.getUser(oldUsername);

        // メールアドレス再設定のためのパスコードを送信
        CognitoUserAttributes cognitoUserAttributes = new CognitoUserAttributes();
        cognitoUserAttributes.addAttribute("email", newMailAddress);
        cognitoUser.updateAttributesInBackground(cognitoUserAttributes, new UpdateAttributesHandler() {
            @Override
            public void onSuccess(List<CognitoUserCodeDeliveryDetails> attributesVerificationList) {
                Log.d("[CognitoResetMailAddress]sendSetMailAddressCode","[onSuccess] : " + newMailAddress);
                // パスコード送信が成功した場合の処理
                ButtonInfo buttonInfo = new ButtonInfo();
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup(activity.getString(R.string.reset_mail_address_title), activity.getString(R.string.send_passcode_success));
            }

            @Override
            public void onFailure(Exception exception) {
                Log.e("[CognitoResetMailAddress]sendSetMailAddressCode", "[onFailure]kinds of error: " + exception.getClass().getSimpleName());
                Log.e("[CognitoResetMailAddress]sendSetMailAddressCode", "[onFailure]error message: " + exception.getMessage());
                // 失敗時の処理
                ButtonInfo buttonInfo = new ButtonInfo();
                if (exception instanceof NotAuthorizedException) {
                    //ログイン画面に遷移
                    buttonInfo.popupFunctions.add(new AppSignOut(activity));
                    buttonInfo.popupFunctions.add(new ActivityChange(activity, LoginActivity.class, true));
                    Popup popup = new Popup(activity, buttonInfo);
                    popup.createPopup(activity.getString(R.string.re_login_title),activity.getString(R.string.re_login_message));
                } else {
                    buttonInfo.popupFunctions.add(new ActivityFinish(activity));
                    Popup popup = new Popup(activity, buttonInfo);
                    popup.createPopup(activity.getString(R.string.reset_mail_address_title), activity.getString(R.string.cognito_internal_error));
                }
            }
        });

        return true;
    }

    public boolean setNewMailAddress(String passcode) {

        // パスコード認証後、メールアドレス再設定
        cognitoUser.verifyAttributeInBackground("email", passcode, new GenericHandler() {
            @Override
            public void onSuccess() {
                // メールアドレス再設定が成功した場合の処理
                // ログイン情報更新のため、暫定的にログイン画面に遷移
                // TODO:自動ログイン実装
                // TODO:DB登録
                UserMailAddress userMailAddress = new UserMailAddress(activity);
                userMailAddress.saveUserMailAddress(null);
                ButtonInfo buttonInfo = new ButtonInfo();
                buttonInfo.popupFunctions.add(new AppSignOut(activity));
                buttonInfo.popupFunctions.add(new ActivityChange(activity,LoginActivity.class,true));
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup(activity.getString(R.string.reset_mail_address_title), activity.getString(R.string.reset_mail_address_success));
            }

            @Override
            public void onFailure(Exception exception) {
                Log.e("[CognitoResetMailAddress]setNewMailAddress", "[onFailure]kinds of error: " + exception.getClass().getSimpleName());
                Log.e("[CognitoResetMailAddress]setNewMailAddress", "[onFailure]error message: " + exception.getMessage());
                // 失敗時の処理
                ButtonInfo buttonInfo = new ButtonInfo();
                Popup popup = new Popup(activity, buttonInfo);
                if (exception instanceof CodeMismatchException) {
                    popup.createPopup(activity.getString(R.string.reset_mail_address_title), activity.getString(R.string.passcode_illegal));
                } else if (exception instanceof AliasExistsException) {
                    popup.createPopup(activity.getString(R.string.reset_mail_address_title), activity.getString(R.string.already_register_mail_address));
                } else{
                    popup.createPopup(activity.getString(R.string.reset_mail_address_title), activity.getString(R.string.cognito_internal_error));
                }
            }
        });

        return true;
    }
}
