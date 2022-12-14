# DYWTest
githubの機能を自由に試すことができます。<br>
マージ、コミットなど、不安があるところはこのリポジトリを利用して試していきましょう。

## はじめに
mainから適当なブランチをCreateしてからそのブランチで作業するとより安心です。

## Git
プログラム等のバージョン管理システムです。<br>
以下のサイトで、基本的な概念が学べます。<br>
[Git](https://tcd-theme.com/2019/12/what-is-git.html)

## コマンドプロンプトで操作する場合（CUI）
以下のサイトで、Gitの基本的な操作が学べます。<br>
[コマンドプロンプトでの操作](https://tech-blog.rakus.co.jp/entry/20200529/git)

## Gitクライアントで操作する場合（GUI）
Gitクライアントとはソースコードの履歴、共有の管理サービス「Git」をGUIで扱うためのアプリケーションです。<br>
Sourcetreeを使用することをお勧めします。<br>
もちろん、他のを使用しても問題ありません。<br>
[使い方](https://qiita.com/TAKANEKOMACHI/items/53058acc15d965d66798)
[インストーラ](https://ja.atlassian.com/software/sourcetree)

## 課題
以下のことができれば、雰囲気は掴めるでしょう。<br>
何からやればいいか分からない人は、参考にしてください。<br>
書くのが面倒になったので省きますが、プルリクエストを投げてコードをマージする作業も必要になってくると思います。<br>

①：自分の作業用ブランチを作成する(create)<br>
Githubで操作。<br>
mainから枝を生やすイメージ。<br>

②：リモートリポジトリからコードを落とす(clone)<br>
ローカルのコマンドプロンプトもしくはGitクライアントで操作。<br>
cloneはローカルにコードを落とす作業のこと。<br>

③：ファイルを追加、編集する<br>
ローカルで操作。<br>
空のテキストファイル作成だけでも良い。<br>

④：③の内容を①のブランチに反映させる(push)<br>
ローカルで操作。<br>
厳密にはcommitしてからpushを行う。<br>

⑤：編集内容をGithubで確認する<br>
Githubで操作。<br>
③の内容が反映されてればok。
