package com.example.bottomnav.cognito;

import android.app.Activity;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler;
import com.amazonaws.services.cognitoidentityprovider.model.CodeMismatchException;
import com.example.bottomnav.R;
import com.example.bottomnav.popup.ActivityFinish;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.Popup;

import java.util.Objects;

public class CognitoResetPassword extends CognitoManager{
    private ForgotPasswordContinuation forgotPasswordContinuation;

    public CognitoResetPassword(Activity activity) {
        super(activity);
    }

    public boolean sendForgotPasswordCode(String username) {

        CognitoUser cognitoUser = cognitoUserPool.getUser(username);

        cognitoUser.forgotPasswordInBackground(new ForgotPasswordHandler() {
            @Override
            public void onSuccess() {
                // パスワード再設定に成功した場合の処理
                ButtonInfo buttonInfo = new ButtonInfo();
                buttonInfo.popupFunctions.add(new ActivityFinish(activity));
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup(activity.getString(R.string.reset_password_title), activity.getString(R.string.reset_password_success));
            }

            @Override
            public void getResetCode(ForgotPasswordContinuation continuation) {
                // パスワード再設定コードをメール送信
                // このコードをユーザーに提示して、新しいパスワードを設定させる
                // ユーザープールに登録されてないアドレスでもこの関数は呼ばれてしまうが、
                // 運用上ログインせずにパスワード再設定することはないと考え、例外処理はしない
                forgotPasswordContinuation = continuation;
                ButtonInfo buttonInfo = new ButtonInfo();
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup(activity.getString(R.string.reset_password_title), activity.getString(R.string.send_passcode_success));
            }

            @Override
            public void onFailure(Exception exception) {
                // パスワード再設定に失敗した場合の処理
                ButtonInfo buttonInfo = new ButtonInfo();
                Popup popup = new Popup(activity, buttonInfo);
                if (exception instanceof CodeMismatchException) {
                    popup.createPopup(activity.getString(R.string.reset_password_title), activity.getString(R.string.passcode_illegal));
                }
                else{
                    popup.createPopup(activity.getString(R.string.reset_password_title), activity.getString(R.string.cognito_internal_error));
                }
            }
        });

        return true;
    }

    public boolean setNewPassword(String passcode,String newPassword) {

        if(Objects.isNull(forgotPasswordContinuation)){
            ButtonInfo buttonInfo = new ButtonInfo();
            Popup popup = new Popup(activity, buttonInfo);
            popup.createPopup("[CognitoResetPassword]setNewPassword", "No Send code: Please send code");
            return false;
        }
        if (passcode.isEmpty()) {
            ButtonInfo buttonInfo = new ButtonInfo();
            Popup popup = new Popup(activity, buttonInfo);
            popup.createPopup("[CognitoResetPassword]setNewPassword", "Passcode is empty");
            return false;
        }
        if (newPassword.isEmpty()) {
            ButtonInfo buttonInfo = new ButtonInfo();
            Popup popup = new Popup(activity, buttonInfo);
            popup.createPopup("[CognitoResetPassword]setNewPassword", "New Password is empty");
            return false;
        }

        try {
            // 新しいパスワードを設定
            forgotPasswordContinuation.setPassword(newPassword);
            forgotPasswordContinuation.setVerificationCode(passcode);
            // この処理でForgotPasswordHandlerのonSuccessやonFailureに移る
            forgotPasswordContinuation.continueTask();
        } catch(Exception e){
            Log.e("[CognitoResetPassword]setNewPassword", "kinds of error: " + e.getClass().getSimpleName());
            Log.e("[CognitoResetPassword]setNewPassword", "error message: " + e.getMessage());
            ButtonInfo buttonInfo = new ButtonInfo();
            Popup popup = new Popup(activity, buttonInfo);
            popup.createPopup(activity.getString(R.string.reset_password_title), activity.getString(R.string.cognito_internal_error));
        }

        return true;
    }
}
