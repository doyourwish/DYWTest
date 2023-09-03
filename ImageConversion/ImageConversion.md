# ImageConversion
ユーザーの服装登録やおすすめコーデ表示をするために、androidとawsのlambda間で画像をやり取りする必要がある。<br>
androidとlambda間はjsonでメッセージを送受信するため、画像をbase64形式に変換する。<br>
今回は使用しないが、画像をzlib形式で圧縮するコードも記述した。<br>
lambda内の言語はPythonを使用するため、JavaだけでなくPythonのコードも用意した。<br>

## 環境
OS: macOS M1チップ<br>

### android
Android Studio: Electric Eel | 2022.1.1 Patch 2<br>
Device: Pixel_3a_API_33_arm64-v8a

### python
IDE: Spyder<br>
Spyder Version: 5.1.5<br>
Python Version: Python 3.9.5 64-bit

## 準備

### android
app/res/drawable/ に変換したい画像を置き、以下のファイルの変数を書き換える。<br>
以下は ***pochama***.jpeg を app/res/drawable/ に置いた時の例。<br><br>

- activity_main.xml<br>
android:src="@drawable/***pochama***"
- MainActivity.java<br>
byte[] compressedData = ImageConversion.compressImage(getResources(),R.drawable.***pochama***);
<br><br>

また、「デコード・解凍実行」の元データ(MainActivity.javaのbase64String)は、予めzlib圧縮後にbase64エンコードしたものを用意する。<br>

### python
ソースコードはpythonフォルダに格納されている。<br>
注意：パスの指定はmacOSで動かす想定で作成したので、それ以外の環境では必要に応じてコードを変更する。
- base64.py<br>
変換対象の画像パスを指定する。<br>
具体的には、image_path,base64_decoded_pathを編集する。<br>

- zlib.py<br>
変換対象の画像パスを指定する。<br>
具体的には、image_path,compressed_path,decompressed_pathを編集する。<br>

## 実行方法

### android
「Run 'App'」ボタンを押下する。<br>
「圧縮・エンコード実行」ボタンはzlib圧縮後にbase64エンコード、「デコード・解凍実行」ボタンはbase64デコード後にzlib解凍の順で処理が行われる。<br>
画像は保存できないため、結果はログなどで判断する。

### python
Spyderであれば、実行ファイルを開いた状態で「Run File」ボタンを押下する。<br>
結果の画像が準備で指定したパスに保存される。

## 参考
- base64<br>
https://wa3.i-3-i.info/word11338.html

- zlib<br>
https://e-words.jp/w/zlib.html
