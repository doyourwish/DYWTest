package com.example.bottomnav.cognito;

import android.app.Activity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.example.bottomnav.popup.ActivityChange;
import com.example.bottomnav.popup.ActivityFinish;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.Popup;
import com.example.bottomnav.start.RegisterActivity;

public class CognitoDeleteUser {

    final private CognitoConfigure cognitoConfigure = new CognitoConfigure();

    private CognitoUserPool userPool;

    private Activity activity;

    public CognitoDeleteUser(Activity activity){
        this.activity = activity;

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
                ButtonInfo buttonInfo = new ButtonInfo();
                buttonInfo.popupFunctions.add(new ActivityChange(activity, RegisterActivity.class));
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup("退会", "退会が完了しました");
            }

            @Override
            public void onFailure(Exception exception) {
                ButtonInfo buttonInfo = new ButtonInfo();
                buttonInfo.popupFunctions.add(new ActivityFinish(activity));
                Popup popup = new Popup(activity, buttonInfo);
                popup.createPopup("退会","退会処理に失敗しました : " + exception.getMessage());
            }
        });

        return true;
    }
}
