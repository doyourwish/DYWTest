package com.example.cognitoaws;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.UpdateAttributesHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.VerificationHandler;
import com.amazonaws.regions.Regions;

import java.util.List;

public class ResetMailAddressActivity extends AppCompatActivity {

    private CognitoUser user;
    private EditText oldEmailEditText, passwordEditText, newEmailEditText, confirmationCodeEditText;

    final private CognitoConfigure cognitoConfigure = new CognitoConfigure();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_mail_address);

        oldEmailEditText = findViewById(R.id.oldEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        newEmailEditText = findViewById(R.id.newEmailEditText);
        confirmationCodeEditText = findViewById(R.id.confirmationCodeEditText);

        Button LoginButton = findViewById(R.id.loginButton);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        Button requestConfirmationCodeButton = findViewById(R.id.requestConfirmationCodeButton);
        requestConfirmationCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestConfirmationCode();
            }
        });

        Button changeEmailButton = findViewById(R.id.changeEmailButton);
        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeEmail();
            }
        });

    }

    private void login(){
        String username = oldEmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Cognitoユーザーの初期化
        CognitoUserPool userPool = new CognitoUserPool(ResetMailAddressActivity.this,
                cognitoConfigure.userPoolId, cognitoConfigure.clientId,
                cognitoConfigure.clientSecret, cognitoConfigure.cognitoRegion);
        user = userPool.getUser(username);

        //パスワード入力を反映させるためにサインアウト
        user.signOut();

        // ユーザープールにログインリクエストを送信
        user.getSessionInBackground(new AuthenticationHandler() {
            @Override
            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                // ログイン成功時の処理
                String accessToken = userSession.getAccessToken().getJWTToken();
                String idToken = userSession.getIdToken().getJWTToken();
                String refreshToken = userSession.getRefreshToken().getToken();

                // ログイン後の処理をここに記述
                Log.d("login", "ログイン成功");
                Log.d("login", "アクセストークン: " + accessToken);
                Log.d("login", "IDトークン: " + idToken);
                Log.d("login", "リフレッシュトークン: " + refreshToken);

                Toast.makeText(ResetMailAddressActivity.this, "Login is successful.", Toast.LENGTH_SHORT).show();
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
                // ログイン失敗時の処理
                Toast.makeText(ResetMailAddressActivity.this, "Login is failed. : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("login", "ログイン失敗: " + exception.getMessage());
            }
        });
    }

    private void requestConfirmationCode() {
        String newEmail = newEmailEditText.getText().toString();

        CognitoUserAttributes cognitoUserAttributes = new CognitoUserAttributes();
        cognitoUserAttributes.addAttribute("email", newEmail);

        user.updateAttributesInBackground(cognitoUserAttributes, new UpdateAttributesHandler() {
            @Override
            public void onSuccess(List<CognitoUserCodeDeliveryDetails> attributesVerificationList) {
                // パスコード送信が成功した場合の処理
                Toast.makeText(ResetMailAddressActivity.this, "Confirmation code sent to your email.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception exception) {
                // 失敗時の処理
                Toast.makeText(ResetMailAddressActivity.this, "Failed to change email address.", Toast.LENGTH_SHORT).show();
                Log.e("requestConfirmationCode","Failed to confirmation code sent. : " + exception.getMessage());
            }
        });
    }

    private void changeEmail() {
        String confirmationCode = confirmationCodeEditText.getText().toString();

        user.verifyAttributeInBackground("email", confirmationCode, new GenericHandler() {
            @Override
            public void onSuccess() {
                // 属性変更成功した場合の処理
                Toast.makeText(ResetMailAddressActivity.this, "Email address changed successfully.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception exception) {
                // 失敗時の処理
                Toast.makeText(ResetMailAddressActivity.this, "Failed to change email address. : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("changeEmail","Failed to change email address. : " + exception.getMessage());
            }
        });
    }
}
