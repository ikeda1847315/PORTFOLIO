###　基本操作
| 概念   | 意味             |
| ---- | -------------- |
| `/`  | ルートディレクトリ（最上位） |
| `~`  | 自分のホームディレクトリ   |
| `.`  | 現在のディレクトリ      |
| `..` | 一つ上のディレクトリ     |


###　コマンド関連
```bash
ファイル一覧操作：
ls
　ファイル一覧
ls -l
　ファイルの詳細一覧
ls -a
　隠しファイル含めた一覧
ls -la
　結合パターン

ディレクトリ移動操作（チェンジ ディレクトリ）：
cd
　ホームへ戻る
cd ..
　一つ上の階層へ
pwd
　現在地の表示

ファイル・フォルダ操作関連：
touch 〇〇
　ファイル作成
mkdir 〇〇
　ディレクトリ作成
mkdir -p 〇〇/▢▢
　複数階層作成
cp file1.txt file2.txt
　ファイルコピー
cp -r dir1 dir2
　ディレクトリコピー
mv old.txt new.txt
　移動後に名前変更
rm test.txt
　ファイル削除
rm -r dir
　ディレクトリ削除
　⇒rm -rf dir　⚠️強制削除
　　　⇒rm -rf /は、全削除なので、伝説級に危険💀

ファイル内容確認操作：
cat file.txt
　内容表示
less logfile.log
　スクロールしながら閲覧
q
　閲覧終了
head file.txt
　先頭へ
tail file.txt
　末尾へ

検索操作：
find . -name "*.txt"
　ファイル検索
grep "error" app.log
　文字列検索
grep -i error app.log
　大文字・小文字無視

権限変更操作：
chmod 755 script.sh
　権限変更
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
```

| コマンド                   | 内容     |
| ------------------------- | ------ |
| `show ip interface brief` | IP一覧   |
| `show running-config`     | 現在設定   |
| `show vlan brief`         | VLAN確認 |
| `show interfaces`         | IF状態   |

###　サイト
(Bandit)[https://overthewire.org/wargames/bandit/]