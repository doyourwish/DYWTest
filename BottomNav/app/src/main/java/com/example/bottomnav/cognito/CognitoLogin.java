package com.example.bottomnav.cognito;

import android.app.Activity;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.example.bottomnav.common.UserMailAddress;
import com.example.bottomnav.home.MainActivity;
import com.example.bottomnav.popup.ActivityChange;
import com.example.bottomnav.popup.AppSignOut;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.Popup;
import com.example.bottomnav.start.GenderAgeActivity;
import com.example.bottomnav.start.LoginActivity;

public class CognitoLogin extends CognitoManager{

    public CognitoLogin(Activity activity){
        super(activity);
    }

    public boolean login(String username,String password) {

        // CognitoUserオブジェクトを作成
        CognitoUser user = cognitoUserPool.getUser(username);

        // ユーザープールにログインリクエストを送信
        user.getSessionInBackground(new AuthenticationHandler() {
            @Override
            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                // 次回ログインを省略するために、ログイン情報を一部保存
                UserMailAddress userMailAddress = new UserMailAddress(activity);
                userMailAddress.saveUserMailAddress(username);

                // ログイン成功時の処理
                String accessToken = userSession.getAccessToken().getJWTToken();
                String idToken = userSession.getIdToken().getJWTToken();
                String refreshToken = userSession.getRefreshToken().getToken();

                // ログイン後の処理をここに記述
                Log.d("login", "ログイン成功");
                Log.d("login", "アクセストークン: " + accessToken);
                Log.d("login", "IDトークン: " + idToken);
                Log.d("login", "リフレッシュトークン: " + refreshToken);

                // ログイン成功時のポップアップ表示
                // 初期登録完了前後で画面遷移を分岐
                // TODO:DB取得実装
                boolean isRegisterUserInfo = false; //search user email from DB
                if(isRegisterUserInfo){
                    ButtonInfo buttonInfo = new ButtonInfo();
                    buttonInfo.popupFunctions.add(new ActivityChange(activity, MainActivity.class));
                    Popup popup = new Popup(activity, buttonInfo);
                    popup.createPopup("Success", "Login was successful.");
                }
                else{
                    ButtonInfo buttonInfo = new ButtonInfo();
                    buttonInfo.popupFunctions.add(new ActivityChange(activity, GenderAgeActivity.class));
                    Popup popup = new Popup(activity, buttonInfo);
                    popup.createPopup("Success", "Login was successful.");
                }
            }

            @Override
            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
                // ユーザー名とパスワードを提供
                // 一度ログインが成功すると、セッションが持続する限りこの関数は呼ばれない
                AuthenticationDetails authenticationDetails = new AuthenticationDetails(username, password, null);
                authenticationContinuation.setAuthenticationDetails(authenticationDetails);
                authenticationContinuation.continueTask();
            }

            @Override
            public void getMFACode(MultiFactorAuthenticationContinuation continuation) {
                // MFAが設定されている場合の処理
                // この例ではMFAを考慮しないため、何もしない
            }

            @Override
            public void authenticationChallenge(ChallengeContinuation continuation) {
                // 認証に関連するチャレンジがある場合の処理
                // この例では何もしない
            }

            @Override
            public void onFailure(Exception exception) {
                // TODO:エラー種別によるポップアップ表示の変更
                // ログイン失敗時の処理
                Log.e("login", "ログイン失敗: " + exception.getMessage());
                // ログイン失敗時のポップアップ表示
                ButtonInfo buttonInfo = new ButtonInfo();
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup("Error", "Login was failed: " + exception.getMessage());
            }
        });

        return true;
    }

    public boolean checkLogin(String username) {

        // CognitoUserオブジェクトを作成
        CognitoUser user = cognitoUserPool.getUser(username);

        // ユーザープールにログインリクエストを送信
        user.getSessionInBackground(new AuthenticationHandler() {
            @Override
            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {

            }

            @Override
            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
                // ユーザー名とパスワードを提供
                // 一度ログインが成功すると、セッションが持続する限りこの関数は呼ばれない
                // セッションが切れた時にこの関数を通った場合、あえてログインを失敗させる
                AuthenticationDetails authenticationDetails = new AuthenticationDetails(username, "dummy", null);
                authenticationContinuation.setAuthenticationDetails(authenticationDetails);
                authenticationContinuation.continueTask();
            }

            @Override
            public void getMFACode(MultiFactorAuthenticationContinuation continuation) {
                // MFAが設定されている場合の処理
                // この例ではMFAを考慮しないため、何もしない
            }

            @Override
            public void authenticationChallenge(ChallengeContinuation continuation) {
                // 認証に関連するチャレンジがある場合の処理
                // この例では何もしない
            }

            @Override
            public void onFailure(Exception exception) {
                //ログイン画面に遷移
                ButtonInfo buttonInfo = new ButtonInfo();
                buttonInfo.popupFunctions.add(new AppSignOut(activity));
                buttonInfo.popupFunctions.add(new ActivityChange(activity, LoginActivity.class, true));
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup("再ログイン","メールアドレス再設定の前に再ログインをしてください");
            }
        });

        return true;
    }

    public boolean signOut(String username) {

        // CognitoUserオブジェクトを作成
        CognitoUser user = cognitoUserPool.getUser(username);

        // 一度ログインが成功すると、セッションが持続する限りgetAuthenticationDetailsは呼ばれない
        // その場合、入力されたパスワードが反映されない
        // その対策として、ボタンが押される度にSignOutを実行する
        user.signOut();

        //ログイン情報を更新
        UserMailAddress userMailAddress = new UserMailAddress(activity);
        userMailAddress.saveUserMailAddress(null);

        return true;
    }
}
