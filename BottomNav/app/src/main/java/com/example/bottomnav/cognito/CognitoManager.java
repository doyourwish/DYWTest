package com.example.bottomnav.cognito;

import android.app.Activity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;

public class CognitoManager {
    protected CognitoUserPool cognitoUserPool;
    protected CognitoUser cognitoUser;
    protected Activity activity;

    CognitoManager(Activity activity){
        this.activity = activity;

        // Cognitoユーザープールの作成
        CognitoConfigure cognitoConfigure = new CognitoConfigure();
        cognitoUserPool = new CognitoUserPool(activity.getApplicationContext(),
                cognitoConfigure.userPoolId, cognitoConfigure.clientId,
                cognitoConfigure.clientSecret, cognitoConfigure.cognitoRegion);
    }
}
