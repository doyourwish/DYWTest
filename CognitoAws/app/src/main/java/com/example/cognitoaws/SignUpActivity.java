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
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.services.cognitoidentityprovider.model.CodeDeliveryDetailsType;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;

//現在はSESでメールアドレスが認証されてる時のみ、SignUpが成功する

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signUpButton;

    final private CognitoConfigure cognitoConfigure = new CognitoConfigure();

    // CognitoUserPoolオブジェクト
    private CognitoUserPool userPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signUpButton);

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
                CodeDeliveryDetailsType codeDeliveryDetails = signUpResult.getCodeDeliveryDetails();
                // 確認コードの送信方法などの情報を取得できます。
                // SignUp 成功時のポップアップ表示
                showPopup("Success", "SignUp was successful.");
            }

            @Override
            public void onFailure(Exception exception) {
                // SignUp 失敗時の処理
                // エラーメッセージの表示や適切な処理を行うことができます。
                // SignUp 失敗時のポップアップ表示
                showPopup("Error", "SignUp failed: " + exception.getMessage());
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
