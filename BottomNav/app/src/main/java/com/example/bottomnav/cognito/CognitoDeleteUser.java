package com.example.bottomnav.cognito;

import android.app.Activity;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.services.cognitoidentityprovider.model.NotAuthorizedException;
import com.example.bottomnav.R;
import com.example.bottomnav.popup.ActivityChange;
import com.example.bottomnav.popup.ActivityFinish;
import com.example.bottomnav.popup.AppSignOut;
import com.example.bottomnav.popup.ButtonInfo;
import com.example.bottomnav.popup.Popup;
import com.example.bottomnav.start.LoginActivity;
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
                popup.createPopup(activity.getString(R.string.delete_user_title), activity.getString(R.string.delete_user_complete));
            }

            @Override
            public void onFailure(Exception exception) {
                Log.e("[CognitoDeleteUser]deleteUser", "[onFailure]kinds of error: " + exception.getClass().getSimpleName());
                Log.e("[CognitoDeleteUser]deleteUser", "[onFailure]error message: " + exception.getMessage());
                ButtonInfo buttonInfo = new ButtonInfo();
                if (exception instanceof NotAuthorizedException) {
                    //ログイン画面に遷移
                    buttonInfo.popupFunctions.add(new AppSignOut(activity));
                    buttonInfo.popupFunctions.add(new ActivityChange(activity, LoginActivity.class, true));
                    Popup popup = new Popup(activity, buttonInfo);
                    popup.createPopup(activity.getString(R.string.re_login_title),activity.getString(R.string.re_login_message));
                } else {
                    buttonInfo.popupFunctions.add(new ActivityFinish(activity));
                    Popup popup = new Popup(activity, buttonInfo);
                    popup.createPopup(activity.getString(R.string.delete_user_title), activity.getString(R.string.cognito_internal_error));
                }
            }
        });

        return true;
    }
}
