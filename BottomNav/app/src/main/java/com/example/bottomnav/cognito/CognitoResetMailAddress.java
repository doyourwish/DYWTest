package com.example.bottomnav.cognito;

import android.app.Activity;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.UpdateAttributesHandler;
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
                // パスコード送信が成功した場合の処理
                ButtonInfo buttonInfo = new ButtonInfo();
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup("Success", "Send Code was Success. Check your email box");
                Log.d("sendSetMailAddressCode","send code : " + newMailAddress);
            }

            @Override
            public void onFailure(Exception exception) {
                // 失敗時の処理
                ButtonInfo buttonInfo = new ButtonInfo();
                buttonInfo.popupFunctions.add(new ActivityFinish(activity));
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup("Error", "Send for setting NewMailAddress was failed: " + exception.getMessage());
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
                popup.createPopup("Success", "Set NewMailAddress was success");
            }

            @Override
            public void onFailure(Exception exception) {
                // 失敗時の処理
                ButtonInfo buttonInfo = new ButtonInfo();
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup("Error", "Set NewMailAddress was failed: " + exception.getMessage());
            }
        });

        return true;
    }
}
