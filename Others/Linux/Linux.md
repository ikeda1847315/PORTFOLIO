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
複数階層作成 ⇒親/子ディレクトリ
```bash
mkdir -p /
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
-riにする事で、確認オプション
```bash
cp -r
```
移動後に名前変更
後（先）へ移動し改名する<br>
存在する場合は、上書き<br>
元ファイルは消える<br>
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
さらに、rm -rf /は、全削除なので、要注意

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
| 777  | フル権限（通常は非推奨）|

所有者変更<br>
コマンド　所有者：グループ　対象ファイル
```bash
sudo chown　
```
最新のカタログを取得
```bash
sudo apt update
```
実際に、インストール済みソフトを、上記で取得した最新版へ、<br>
安全に更新（他の要素に依存しない部分のみ）
```bash
sudo apt upgrade
```
他の要素含め、必要なら追加・削除もして更新
```bash
sudo apt full-upgrade
```
　※sudo apt dist-upgradeと同義<br>
インストール
```bash
sudo apt install
```
### その他操作：
リアルタイム確認
```bash
tail -f app.log
```
コマンドの場所確認
```bash
which python
```
プロセス確認
```bash
ps aux
```
CPU/メモリ確認　⇒q　終了
```bash
top
```
ディスク容量
```bash
df -h
```
メモリ確認
```bash
free -h
```
IP確認 ＝ ip addr
```bash
ip a
```
ルーティング確認 ＝ ip r
```bash
ip route
```
疎通確認（名前解決[DNS通信]できるか） ⇒Ctrl + C　停止
```bash
ping google.com
```
DNS不要で、外に通信できるか ⇒IP指定で、どこまで通信できるかを確認
```bash
ping 8.8.8.8
```
DNS確認
```bash
cat /etc/resolv.conf
```
HTTP・API確認
```bash
curl
```
ポート確認
```bash
ss -tuln
```
### 自分用：
リモート接続（接続元のPCのPowerShellから）
```powershell
ssh アカウント名@IPアドレス
```
現在ディレクトリをVSCodeで開く
```bash
code .
```
補完（超重要）<br>
<kbd>Tab</kbd><br>
画面クリア<br>
<kbd>Ctrl</kbd> + <kbd>L</kbd><br>
ヘルプ確認 ⇒q 終了
```bash
man ls
ls --help
```
特権EXECモードへ入る　Router#に変更　>は通常モード
```bash
enable
```
設定モードへ入る　conf tでも可　Router(config)#に変更
```bash
configure terminal
```
現在動作中の設定確認 ⇒show runでも可
```bash
show running-config
```
## Cisco系コマンド
IP一覧
```bash
show ip interface brief
```
VLAN確認（1台のスイッチを論理的に分割する仕組み）
```bash
show vlan brief
```
インターフェース状態を表示（通信口の状態確認）
```bash
show interfaces
```
## 学習サイト
[Bandit](https://overthewire.org/wargames/bandit)
