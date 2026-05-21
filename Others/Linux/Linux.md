Oracle Virtual BoxにUbuntu Serverをインストール<br>
また、Windows実機からのSSH接続環境を構築。<br>
練習を兼ねて、Windows ServerとのActive Directory連携を実施。<br>

## 基本操作
| 概念   | 意味             |
| ---- | -------------- |
| `/`  | ルートディレクトリ（最上位） |
| `~`  | 自分のホームディレクトリ   |
| `.`  | 現在のディレクトリ      |
| `..` | 一つ上のディレクトリ     |

再起動
```bash
sudo reboot
```
サインアウト
```bash
exit　
```
シャットダウン
```bash
sudo poweroff　
```
<br>

## コマンド関連
### ファイル一覧操作：
ファイル一覧
```bash
ls
```
ファイルの詳細一覧
```bash
ls -l
```
隠しファイル含めた一覧
```bash
ls -a
```
結合パターン
```bash
ls -la
```
### ディレクトリ移動操作（チェンジ ディレクトリ）：
ホームへ戻る
```bash
cd
```
一つ上の階層へ
```bash
cd ..
```
現在地の表示
```bash
pwd
```
### ファイル・フォルダ操作関連：
ファイル作成
```bash
touch
```
ディレクトリ作成
```bash
mkdir
```
複数階層作成
```bash
mkdirmkdir -p　/
```
ファイルコピー
例：頭（元）の内容で、<br>
後（先）を作成、存在する場合、上書き<br>
尚、元ファイルは残る<br>
-iを付ける事で、確認オプション
```bash
cp
```
ディレクトリコピー
例：頭（元）の内容で、<br>
後（先）を作成（コピー）<br>
-iまたは、iを付ける事で、確認オプション
```bash
cp -r
```
移動後に名前変更
例：頭（元）の内容で、<br>
後（先）を作成（コピー）存在する場合、上書き<br>
また、元ファイルは消える<br>
-iを付ける事で、確認オプション
```bash
mv
```
ファイル削除
```bash
rm
```
ディレクトリ削除
```bash
rm -r
```
⚠️<br>
rm -rf dirは、強制削除<br>
さらに、rm -rf /は、全削除なので、要注意<br>

### ファイル内容確認操作：
内容表示
```bash
cat
```
スクロールしながら閲覧
```bash
less
```
閲覧終了
```bash
q
```
先頭へ
```bash
head
```
末尾へ
```bash
tail
```
### 検索操作：
ファイル検索
```bash
find . -name "*.txt"
```
文字列検索
```bash
grep "error" app.log
```
大文字・小文字無視
```bash
grep -i error app.log
```
### 権限変更操作：
権限変更
```bash
chmod
```
| 数字 | 権限  |
| -- | --- |
| 7  | rwx |
| 5  | r-x |
| 4  | r-- |

sudo chown user:user file.txt
　所有者変更
sudo apt update
　管理者権限実行
sudo apt update
　パッケージ情報更新
sudo apt upgrade
　アップデート
sudo apt install nginx
　インストール

その他操作：
tail -f app.log
　リアルタイム確認

which python
　コマンドの場所確認
ps aux
　プロセス確認
top
　CPU/メモリ確認　⇒q　終了

sudo apt install htop
　インストール
htop
　起動

df -h
　ディスク容量
free -h
　メモリ確認

ip a
　IP確認　＝ip addr
ip route
　ルーティング確認 ＝ip r

ping google.com
　疎通確認（名前解決[DNS]できるか）　⇒Ctrl + C　停止
　他、
　ping 8.8.8.8　DNS不要で、外に通信できるか
　ping 192.168.1.1　ルーターに届くか

cat /etc/resolv.conf
　DNS確認

curl https://example.com
　HTTP・API確認

ssh user@192.168.1.10
　リモート接続
ss -tuln
　ポート確認

自分用：
code .
　現在ディレクトリをVSCodeで開く
Ctrl + C
　処理停止
Ctrl + L
　画面クリア
Tab
　補完（超重要）
man ls
ls --help
　ヘルプ確認　⇒q　終了


enable
　# 特権EXECモードへ入る　Router#に変更　>は通常モード
configure terminal
　# 設定モードへ入る　conf tでも可　Router(config)#に変更
show running-config
　# 現在動作中の設定確認　show runでも可

| コマンド                   | 内容     |
| ------------------------- | ------ |
| `show ip interface brief` | IP一覧   |
| `show running-config`     | 現在設定   |
| `show vlan brief`         | VLAN確認 |
| `show interfaces`         | IF状態   |

## 学習サイト
[Bandit](https://overthewire.org/wargames/bandit)
