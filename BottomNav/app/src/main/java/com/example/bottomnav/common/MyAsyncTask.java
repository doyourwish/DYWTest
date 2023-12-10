package com.example.bottomnav.common;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyAsyncTask extends AsyncTask<Void, Void, String> {
    private Map<String, String> headers;

    public MyAsyncTask() {
        headers = new HashMap<>();
        headers.put("X-Example-Header", "Example-Value");
    }

    @Override
    protected String doInBackground(Void... voids) {
        String resultStr = null;
        try {
            HttpClient httpClient = new HttpClient();
            resultStr = httpClient.get("https://httpbin.org/get", "UTF-8", headers);
        } catch (IOException e) {
            e.printStackTrace(); // エラーログを出力することができます
        }
        return resultStr;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            // 通信が成功した場合の処理をここに記述
            Log.d("http_get",result);
        } else {
            // 通信に失敗した場合の処理をここに記述
        }
    }
}
