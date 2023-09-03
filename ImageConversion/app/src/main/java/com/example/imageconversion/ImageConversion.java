package com.example.imageconversion;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageConversion {

    // 画像をZlib形式で圧縮する関数
    public static byte[] compressImage(Resources resources, int drawableResourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableResourceId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Deflater deflater = new Deflater();
        deflater.setInput(bitmapToByteArray(bitmap));
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            byteArrayOutputStream.write(buffer, 0, count);
        }
        deflater.end();
        return byteArrayOutputStream.toByteArray();
    }

    // バイト配列をBase64にエンコードする関数
    public static String encodeToBase64(byte[] data) {
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    // Base64文字列をバイト配列にデコードする関数
    public static byte[] decodeBase64(String base64String) {
        return Base64.decode(base64String, Base64.DEFAULT);
    }

    // Zlib形式のデータを解凍する関数
    public static byte[] decompressData(byte[] compressedData) {
        Inflater inflater = new Inflater();
        inflater.setInput(compressedData);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                byteArrayOutputStream.write(buffer, 0, count);
            }
            inflater.end();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    //androidのdrawableに置いた画像からbyte形式に変換する関数
    public static byte[] drawableToByteArray(Resources resources, int drawableResourceId) {
        Drawable drawable = resources.getDrawable(drawableResourceId);
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            android.graphics.Bitmap bitmap = bitmapDrawable.getBitmap();
            return bitmapToByteArray(bitmap);
        } else {
            // 他の種類のDrawableに対する処理を追加することも可能
            Log.e("drawableToByteArray","drawable is null");
            return null;
        }
    }
}
