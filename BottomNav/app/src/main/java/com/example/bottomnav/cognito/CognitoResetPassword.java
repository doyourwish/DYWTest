package com.example.bottomnav.cognito;

import android.app.Activity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler;
import com.example.bottomnav.popup.ActivityFinish;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.Popup;

import java.util.Objects;

public class CognitoResetPassword
{
    private ForgotPasswordContinuation forgotPasswordContinuation;

    final private CognitoConfigure cognitoConfigure = new CognitoConfigure();

    //現状sendForgotPasswordCodeしかuserPool変数は使われてないが、
    //拡張性を考えてCognitoクラスを作成し、
    //その子クラスとしてCognitoResetPasswordクラスを生成する想定でコードを記述した
    //エラーコードも仕込む予定
    private CognitoUserPool userPool;

    private Activity activity;

    //TODO:メッセージの管理方法、エラーコード設定検討
    public boolean sendForgotPasswordCode(String username, Activity activity) {

        this.activity = activity;

        // ユーザープールの初期化
        userPool = new CognitoUserPool(activity.getApplicationContext(),
                cognitoConfigure.userPoolId, cognitoConfigure.clientId,
                cognitoConfigure.clientSecret, cognitoConfigure.cognitoRegion);

        CognitoUser cognitoUser = userPool.getUser(username);

        cognitoUser.forgotPasswordInBackground(new ForgotPasswordHandler() {
            @Override
            public void onSuccess() {
                // パスワード再設定に成功した場合の処理
                ButtonInfo buttonInfo = new ButtonInfo();
                buttonInfo.popupFunctions.add(new ActivityFinish(activity));
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup("Success", "Set NewPassword was success");
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
                popup.createPopup("Success", "Send Code was Success. Check your email box");
            }

            @Override
            public void onFailure(Exception exception) {
                // パスワード再設定に失敗した場合の処理
                ButtonInfo buttonInfo = new ButtonInfo();
                buttonInfo.popupFunctions.add(new ActivityFinish(activity));
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup("Error", "Set NewPassword was failed: " + exception.getMessage());
            }
        });

        return true;
    }

    public boolean setNewPassword(String passcode,String newPassword) {

        if(Objects.isNull(forgotPasswordContinuation)){
            ButtonInfo buttonInfo = new ButtonInfo();
            Popup popup = new Popup(activity, buttonInfo);
            popup.createPopup("forgotPasswordContinuation is null", "No Send code: Please send code");
        }
        if (passcode.isEmpty()) {
            ButtonInfo buttonInfo = new ButtonInfo();
            Popup popup = new Popup(activity, buttonInfo);
            popup.createPopup("Empty", "Passcode is empty");
            return false;
        }
        if (newPassword.isEmpty()) {
            ButtonInfo buttonInfo = new ButtonInfo();
            Popup popup = new Popup(activity, buttonInfo);
            popup.createPopup("Empty", "New Password is empty");
            return false;
        }

        try {
            // 新しいパスワードを設定
            forgotPasswordContinuation.setPassword(newPassword);
            forgotPasswordContinuation.setVerificationCode(passcode);
            // この処理でForgotPasswordHandlerのonSuccessやonFailureに移る
            forgotPasswordContinuation.continueTask();
        } catch(Exception e){
            ButtonInfo buttonInfo = new ButtonInfo();
            Popup popup = new Popup(activity, buttonInfo);
            popup.createPopup("Error", "No Send code: " + e.getMessage());
        }

        return true;
    }
}
