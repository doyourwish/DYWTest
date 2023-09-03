package com.example.cognitoaws;

import com.amazonaws.regions.Regions;

public class CognitoConfigure {
    // Cognitoユーザープールの設定
    // 以下の画面から遷移するとする
    // [Amazon Cognito > ユーザープール > cognito_userpool]
    //
    // @param userPoolId 作成したユーザープールのID
    // @param clientId アプリケーションクライアントのクライアントID
    // [アプリケーションの統合 > アプリクライアントと分析 > アプリケーションクライアント名]
    // @param clientSecret アプリケーションクライアントのクライアントSecret
    // [アプリケーションの統合 > アプリクライアントと分析 > アプリケーションクライアント名]
    // @param cognitoRegion 使用するcognitoのRegion

    final String userPoolId = "sample";
    final String clientId = "sample";
    final String clientSecret = "sample";
    final Regions cognitoRegion = Regions.US_WEST_2;

}
