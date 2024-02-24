package com.example.bottomnav.cognito;

import android.app.Activity;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.services.cognitoidentityprovider.model.CodeDeliveryDetailsType;
import com.amazonaws.services.cognitoidentityprovider.model.CodeMismatchException;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;
import com.amazonaws.services.cognitoidentityprovider.model.UsernameExistsException;
import com.example.bottomnav.R;
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
                popup.createPopup(activity.getString(R.string.register_title), activity.getString(R.string.send_passcode_success));
            }

            @Override
            public void onFailure(Exception exception) {
                Log.e("[CognitoSignUp]signUpUser", "[onFailure]kinds of error: " + exception.getClass().getSimpleName());
                Log.e("[CognitoSignUp]signUpUser", "[onFailure]error message: " + exception.getMessage());
                // SignUp 失敗時の処理
                // エラーメッセージの表示や適切な処理を行うことができます。
                // SignUp 失敗時のポップアップ表示
                ButtonInfo buttonInfo = new ButtonInfo();
                buttonInfo.popupFunctions.add(new ActivityChange(activity, RegisterActivity.class));
                Popup popup = new Popup(activity, buttonInfo);
                if (exception instanceof UsernameExistsException) {
                    popup.createPopup(activity.getString(R.string.register_title), activity.getString(R.string.already_register_mail_address));
                }
                else{
                    popup.createPopup(activity.getString(R.string.register_title), activity.getString(R.string.cognito_internal_error));
                }
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
                popup.createPopup(activity.getString(R.string.register_title), activity.getString(R.string.register_complete));
            }

            @Override
            public void onFailure(Exception exception) {
                Log.e("[CognitoSignUp]confirmSignUp", "[onFailure]kinds of error: " + exception.getClass().getSimpleName());
                Log.e("[CognitoSignUp]confirmSignUp", "[onFailure]error message: " + exception.getMessage());
                ButtonInfo buttonInfo = new ButtonInfo();
                Popup popup = new Popup(activity, buttonInfo);
                if (exception instanceof CodeMismatchException) {
                    popup.createPopup(activity.getString(R.string.register_title), activity.getString(R.string.passcode_illegal));
                }
                else{
                    popup.createPopup(activity.getString(R.string.register_title), activity.getString(R.string.cognito_internal_error));
                }
            }
        });

        return true;
    }
}
