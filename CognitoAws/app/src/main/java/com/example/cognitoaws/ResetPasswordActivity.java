package com.example.cognitoaws;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler;

import java.util.Objects;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private Button buttonSendCode;
    private EditText editTextNewPassword;
    private EditText editTextPasscode;
    private Button buttonSetNewPassword;

    private ForgotPasswordContinuation forgotPasswordContinuation;

    final private CognitoConfigure cognitoConfigure = new CognitoConfigure();
    private CognitoUserPool userPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editTextUsername = findViewById(R.id.editTextUsername);
        buttonSendCode = findViewById(R.id.buttonSendCode);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextPasscode = findViewById(R.id.editTextPasscode);
        buttonSetNewPassword = findViewById(R.id.buttonSetNewPassword);

        buttonSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendForgotPasswordCode();
            }
        });

        buttonSetNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewPassword();
            }
        });
    }

    private void sendForgotPasswordCode() {
        String username = editTextUsername.getText().toString();

        // ユーザープールの初期化
        userPool = new CognitoUserPool(getApplicationContext(),
                cognitoConfigure.userPoolId, cognitoConfigure.clientId,
                cognitoConfigure.clientSecret, cognitoConfigure.cognitoRegion);

        CognitoUser cognitoUser = userPool.getUser(username);

        cognitoUser.forgotPasswordInBackground(new ForgotPasswordHandler() {
            @Override
            public void onSuccess() {
                // パスワード再設定に成功した場合の処理
                showPopup("Success", "Set NewPassword was success");
            }

            @Override
            public void getResetCode(ForgotPasswordContinuation continuation) {
                // パスワード再設定コードを取得した場合の処理
                // このコードをユーザーに提示して、新しいパスワードを設定させる
                forgotPasswordContinuation = continuation;
                showPopup("Success", "Send Code was Success. Check your email box");
            }

            @Override
            public void onFailure(Exception exception) {
                // パスワード再設定に失敗した場合の処理
                showPopup("Error", "Set NewPassword was failed: " + exception.getMessage());
            }
        });
    }

    private void setNewPassword() {
        String passcode = editTextPasscode.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();

        if(Objects.isNull(forgotPasswordContinuation)){
            showPopup("Empty", "No Send code: Please send code");
            return;
        }
        if (passcode.isEmpty()) {
            showPopup("Empty", "Passcode is empty");
            return;
        }
        if (newPassword.isEmpty()) {
            showPopup("Empty", "New Password is empty");
            return;
        }

        try {
            // 新しいパスワードを設定
            forgotPasswordContinuation.setPassword(newPassword);
            forgotPasswordContinuation.setVerificationCode(passcode);
            // この処理でForgotPasswordHandlerのonSuccessやonFailureに移る
            forgotPasswordContinuation.continueTask();
        } catch(Exception e){
            showPopup("Error", "No Send code: " + e.getMessage());
        }
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
