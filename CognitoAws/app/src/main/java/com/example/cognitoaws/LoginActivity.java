package com.example.cognitoaws;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;

public class LoginActivity extends AppCompatActivity {
    final private CognitoConfigure cognitoConfigure = new CognitoConfigure();
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // EditTextとButtonのインスタンスを取得
        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        // ログインボタンがクリックされたときの処理
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ユーザー名とパスワードをEditTextから取得
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Cognitoユーザープールの作成
                CognitoUserPool userPool = new CognitoUserPool(LoginActivity.this,
                        cognitoConfigure.userPoolId, cognitoConfigure.clientId,
                        cognitoConfigure.clientSecret, cognitoConfigure.cognitoRegion);

                // CognitoUserオブジェクトを作成
                CognitoUser user = userPool.getUser(username);

                // 一度ログインが成功すると、セッションが持続する限りgetAuthenticationDetailsは呼ばれない
                // その場合、入力されたパスワードが反映されない
                // その対策として、ボタンが押される度にSignOutを実行する
                // TODO:ログイン情報保持方法検討
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

                        // ログイン成功時のポップアップ表示
                        showPopup("Success", "Login was successful.");
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
                        showPopup("Error", "Login was failed: " + exception.getMessage());
                    }
                });
            }
        });
    }

    private void showPopup(String title, String message) {
        android.app.AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        alertDialog.show();
    }
}


