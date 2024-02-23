package com.example.bottomnav.cognito;

import android.app.Activity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.services.cognitoidentityprovider.model.CodeDeliveryDetailsType;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;
import com.example.bottomnav.popup.ActivityChange;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.Popup;
import com.example.bottomnav.start.GenderAgeActivity;
import com.example.bottomnav.start.RegisterActivity;

public class CognitoSignUp extends CognitoManager {
    public CognitoSignUp(Activity activity){
        super(activity);
    }

    public boolean signUpUser(String username,String password) {

        // UserAttributeの設定（必要に応じて追加）
        CognitoUserAttributes userAttributes = new CognitoUserAttributes();
        userAttributes.addAttribute("email", username);

        // SignUp処理
        cognitoUserPool.signUpInBackground(username, password, userAttributes, null, new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser user, SignUpResult signUpResult) {
                // SignUp 成功時の処理
                // 確認コードの送信方法などの情報を取得できます。
                CodeDeliveryDetailsType codeDeliveryDetails = signUpResult.getCodeDeliveryDetails();
                cognitoUser = user;
                // SignUp 成功時のポップアップ表示
                ButtonInfo buttonInfo = new ButtonInfo();
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup("Success", "Send Code was Success. Check your email box");
            }

            @Override
            public void onFailure(Exception exception) {
                // SignUp 失敗時の処理
                // エラーメッセージの表示や適切な処理を行うことができます。
                // SignUp 失敗時のポップアップ表示
                ButtonInfo buttonInfo = new ButtonInfo();
                buttonInfo.popupFunctions.add(new ActivityChange(activity, RegisterActivity.class));
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup("Error", "Send Code failed: " + exception.getMessage());
            }
        });

        return true;
    }

    public boolean confirmSignUp(String confirmationCode) {

        cognitoUser.confirmSignUpInBackground(confirmationCode, false, new GenericHandler() {
            @Override
            public void onSuccess() {
                ButtonInfo buttonInfo = new ButtonInfo();
                buttonInfo.popupFunctions.add(new ActivityChange(activity, GenderAgeActivity.class));
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup("Success", "Account confirmed. You can now sign in.");
            }

            @Override
            public void onFailure(Exception exception) {
                ButtonInfo buttonInfo = new ButtonInfo();
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup("Error", "Confirmation failed: " + exception.getMessage());
            }
        });

        return true;
    }
}
