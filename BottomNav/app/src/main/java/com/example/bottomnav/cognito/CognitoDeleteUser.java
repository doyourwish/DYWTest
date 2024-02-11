package com.example.bottomnav.cognito;

import android.app.Activity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.example.bottomnav.popup.ActivityChange;
import com.example.bottomnav.popup.ActivityFinish;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.Popup;
import com.example.bottomnav.start.RegisterActivity;

public class CognitoDeleteUser extends CognitoManager{

    public CognitoDeleteUser(Activity activity){
        super(activity);
    }

    public boolean deleteUser(String username) {

        // CognitoUserオブジェクトを作成
        CognitoUser user = cognitoUserPool.getUser(username);

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
