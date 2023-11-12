package com.example.bottomnav.cognito;

import android.app.Activity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.services.cognitoidentityprovider.model.CodeDeliveryDetailsType;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;
import com.example.bottomnav.common.Popup;
import com.example.bottomnav.start.GenderAgeActivity;

public class CognitoSignUp
{
    final private CognitoConfigure cognitoConfigure = new CognitoConfigure();

    private CognitoUserPool userPool;

    private CognitoUser cognitoUser;

    private Activity activity;

    private Popup popup;

    public CognitoSignUp(Activity activity){
        this.activity = activity;
        // ポップアップ用クラスのインスタンス生成
        popup = new Popup(this.activity);
        // Cognitoユーザープールの作成
        userPool = new CognitoUserPool(activity.getApplicationContext(),
                cognitoConfigure.userPoolId, cognitoConfigure.clientId,
                cognitoConfigure.clientSecret, cognitoConfigure.cognitoRegion);
    }

    public boolean signUpUser(String username,String password) {

        // UserAttributeの設定（必要に応じて追加）
        CognitoUserAttributes userAttributes = new CognitoUserAttributes();
        userAttributes.addAttribute("email", username);
        userAttributes.addAttribute("preferred_username", username); // ユーザー名をメールアドレスに合わせる

        // SignUp処理
        userPool.signUpInBackground(username, password, userAttributes, null, new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser user, SignUpResult signUpResult) {
                // SignUp 成功時の処理
                // 確認コードの送信方法などの情報を取得できます。
                CodeDeliveryDetailsType codeDeliveryDetails = signUpResult.getCodeDeliveryDetails();
                cognitoUser = user;
                // SignUp 成功時のポップアップ表示
                popup.showPopup("Success", "Send Code was Success. Check your email box");
            }

            @Override
            public void onFailure(Exception exception) {
                // SignUp 失敗時の処理
                // エラーメッセージの表示や適切な処理を行うことができます。
                // SignUp 失敗時のポップアップ表示
                popup.showPopupWithActivityFinish("Error", "Send Code failed: " + exception.getMessage());
            }
        });

        return true;
    }

    public boolean confirmSignUp(String confirmationCode) {

        cognitoUser.confirmSignUpInBackground(confirmationCode, false, new GenericHandler() {
            @Override
            public void onSuccess() {
                popup.showPopupWithActivityChange("Success", "Account confirmed. You can now sign in.", GenderAgeActivity.class);
            }

            @Override
            public void onFailure(Exception exception) {
                popup.showPopup("Error", "Confirmation failed: " + exception.getMessage());
            }
        });

        return true;
    }
}
