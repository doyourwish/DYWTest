package com.example.sendmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // アプリの専用ディレクトリのパスを取得
                File appDir = getExternalFilesDir(null);
                // tokensディレクトリのパスを作成
                File tokensDir = new File(appDir, "/asset/tokens");
                if (!tokensDir.exists()) {
                    tokensDir.mkdirs();
                }
                // 認証ファイルの取得
                Context appContext = getApplicationContext();
                InputStream in = appContext.getResources().openRawResource(R.raw.credentials);

                // メール送信
                // gmail apiをandroidに組み込もうとしたが、java標準のライブラリが取り込めずエラーが出る
                String email_address = "xxxyyy@gmail.com";
                try {
                    SendMessage.sendEmail(email_address,email_address,in,tokensDir);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}