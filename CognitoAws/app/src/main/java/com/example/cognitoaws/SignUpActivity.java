package com.example.cognitoaws;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.services.cognitoidentityprovider.model.CodeDeliveryDetailsType;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;


public class SignUpActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmationCodeEditText;
    private Button signUpButton;
    private Button confirmButton;

    final private CognitoConfigure cognitoConfigure = new CognitoConfigure();

    // CognitoUserPoolオブジェクト
    private CognitoUserPool userPool;
    private CognitoUser cognitoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmationCodeEditText = findViewById(R.id.confirmationCodeEditText);
        signUpButton = findViewById(R.id.signUpButton);
        confirmButton = findViewById(R.id.confirmButton);

        // ユーザープールの初期化
        userPool = new CognitoUserPool(getApplicationContext(),
                cognitoConfigure.userPoolId, cognitoConfigure.clientId,
                cognitoConfigure.clientSecret, cognitoConfigure.cognitoRegion);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmSignUp();
            }
        });

    }

    private void signUpUser() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // UserAttributeの設定（必要に応じて追加）
        CognitoUserAttributes userAttributes = new CognitoUserAttributes();
        userAttributes.addAttribute("email", username);
        userAttributes.addAttribute("preferred_username", username); // ユーザー名をメールアドレスに合わせる

        // SignUp処理
        userPool.signUpInBackground(username, password, userAttributes, null, new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser user, SignUpResult signUpResult) {
                // SignUp 成功時の処理
                // 確認コードの送信方法などの情報を取得できます。
                CodeDeliveryDetailsType codeDeliveryDetails = signUpResult.getCodeDeliveryDetails();
                cognitoUser = user;
                // SignUp 成功時のポップアップ表示
                showPopup("Success", "Send Code was Success. Check your email box");
            }

            @Override
            public void onFailure(Exception exception) {
                // SignUp 失敗時の処理
                // エラーメッセージの表示や適切な処理を行うことができます。
                // SignUp 失敗時のポップアップ表示
                showPopup("Error", "Send Code failed: " + exception.getMessage());
            }
        });
    }

    private void confirmSignUp() {
        String confirmationCode = confirmationCodeEditText.getText().toString();

        cognitoUser.confirmSignUpInBackground(confirmationCode, false, new GenericHandler() {
            @Override
            public void onSuccess() {
                showPopup("Success", "Account confirmed. You can now sign in.");
            }

            @Override
            public void onFailure(Exception exception) {
                showPopup("Error", "Confirmation failed: " + exception.getMessage());
            }
        });
    }

    private void showPopup(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
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
