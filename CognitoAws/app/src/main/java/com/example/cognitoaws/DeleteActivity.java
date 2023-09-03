package com.example.cognitoaws;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;

//確認ステータスが「確認済」の時は削除できるが、他のステータスでは管理者権限が必要らしい
//管理者で削除する場合は、CLIで以下の操作で削除できることを確認済
//$ aws cognito-idp admin-delete-user \
//        --user-pool-id ユーザープールID \
//        --username ユーザー名

//ユーザー削除時に、LambdaによるDB情報の削除も必要

public class DeleteActivity extends AppCompatActivity {
    final private CognitoConfigure cognitoConfigure = new CognitoConfigure();

    private Button deleteButton;
    private EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        deleteButton = findViewById(R.id.deleteButton);
        usernameEditText = findViewById(R.id.usernameEditText);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationPopup();
            }
        });
    }

    private void showConfirmationPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete user")
                .setMessage("Are you sure you want to delete the user?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteUser();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void deleteUser() {
        String usernameToDelete = usernameEditText.getText().toString();

        if (usernameToDelete.isEmpty()) {
            showPopup("Error", "Please input user name");
            return;
        }

        CognitoUserPool userPool = new CognitoUserPool(getApplicationContext(),
                cognitoConfigure.userPoolId, cognitoConfigure.clientId,
                cognitoConfigure.clientSecret, cognitoConfigure.cognitoRegion);

        CognitoUser cognitoUser = userPool.getUser(usernameToDelete);

        cognitoUser.deleteUserInBackground(new GenericHandler() {
            @Override
            public void onSuccess() {
                showPopup("Success", "Delete user was successful.");
            }

            @Override
            public void onFailure(Exception exception) {
                showPopup("Error", "Delete user failed: " + exception.getMessage());
            }
        });
    }

    private void showPopup(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    // ...

}