package com.example.bottomnav.cognito;

import android.app.Activity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.example.bottomnav.common.Popup;
import com.example.bottomnav.start.RegisterActivity;

public class CognitoDeleteUser {

    final private CognitoConfigure cognitoConfigure = new CognitoConfigure();

    private CognitoUserPool userPool;

    private Activity activity;

    private Popup popup;

    public CognitoDeleteUser(Activity activity){
        this.activity = activity;
        // ポップアップ用クラスのインスタンス生成
        popup = new Popup(this.activity);
        // Cognitoユーザープールの作成
        userPool = new CognitoUserPool(activity.getApplicationContext(),
                cognitoConfigure.userPoolId, cognitoConfigure.clientId,
                cognitoConfigure.clientSecret, cognitoConfigure.cognitoRegion);
    }

    public boolean deleteUser(String username) {

        // CognitoUserオブジェクトを作成
        CognitoUser user = userPool.getUser(username);

        user.deleteUserInBackground(new GenericHandler() {
            @Override
            public void onSuccess() {
                popup.showPopupWithActivityChange("退会", "退会が完了しました", RegisterActivity.class);
            }

            @Override
            public void onFailure(Exception exception) {
                popup.showPopupWithActivityFinish("退会","退会処理に失敗しました : " + exception.getMessage());
            }
        });

        return true;
    }
}
