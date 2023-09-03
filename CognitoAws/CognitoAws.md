# CognitoAws
AWSのCognitoにandroidから接続するためのソースコード<br>

## 環境
OS: macOS M1チップ<br>
Android Studio: Electric Eel | 2022.1.1 Patch 2<br>
Device: Pixel_3a_API_33_arm64-v8a

## 準備
AWSの環境構築が完了してる前提とする。<br>

以下のファイルで使用するAWSの環境を設定する。<br>
/CognitoAws/app/src/main/java/com/example/cognitoaws/CognitoConfigure.java<br>

以下のファイルで実行プログラムを選択する。<br>
/CognitoAws/app/src/main/AndroidManifest.xml

## 実行方法
「Run 'App'」ボタンを押下する<br>
本アプリで要求されるユーザー名とパスワードは、Cognitoのユーザープールに登録されたものを入力する。

## 参考
- API reference
https://aws-amplify.github.io/aws-sdk-android/docs/reference/

- source
https://github.com/aws-amplify/aws-sdk-android
