package com.example.bottomnav.cognito;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.UpdateAttributesHandler;
import com.example.bottomnav.common.Popup;
import com.example.bottomnav.common.UserMailAddress;
import com.example.bottomnav.start.LoginActivity;

import java.util.List;

public class CognitoResetMailAddress
{
    final private CognitoConfigure cognitoConfigure = new CognitoConfigure();

    private CognitoUser cognitoUser;

    private UserMailAddress userMailAddress;

    private String newMailAddress;

    private Popup popup;

    public boolean sendSetMailAddressCode(String newMailAddress, Activity activity) {

        // 各種初期化
        popup = new Popup(activity);
        CognitoUserPool userPool = new CognitoUserPool(activity.getApplicationContext(),
                cognitoConfigure.userPoolId, cognitoConfigure.clientId,
                cognitoConfigure.clientSecret, cognitoConfigure.cognitoRegion);
        this.newMailAddress = newMailAddress;

        //ログイン中のアカウントを特定
        userMailAddress = new UserMailAddress(activity);
        String oldUsername = userMailAddress.getUserMailAddress();
        cognitoUser = userPool.getUser(oldUsername);

        // メールアドレス再設定のためのパスコードを送信
        CognitoUserAttributes cognitoUserAttributes = new CognitoUserAttributes();
        cognitoUserAttributes.addAttribute("email", this.newMailAddress);
        cognitoUser.updateAttributesInBackground(cognitoUserAttributes, new UpdateAttributesHandler() {
            @Override
            public void onSuccess(List<CognitoUserCodeDeliveryDetails> attributesVerificationList) {
                // パスコード送信が成功した場合の処理
                popup.showPopup("Success", "Send Code was Success. Check your email box");
                Log.d("sendSetMailAddressCode","send code : " + CognitoResetMailAddress.this.newMailAddress);
            }

            @Override
            public void onFailure(Exception exception) {
                // 失敗時の処理
                popup.showPopupWithActivityFinish("Error", "Set NewMailAddress was failed: " + exception.getMessage());
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
                userMailAddress.saveUserMailAddress(null);
                popup.showPopupWithActivityLogin("Success", "Set NewMailAddress was success");
            }

            @Override
            public void onFailure(Exception exception) {
                // 失敗時の処理
                popup.showPopup("Error", "Set NewMailAddress was failed: " + exception.getMessage());
            }
        });

        return true;
    }
}
