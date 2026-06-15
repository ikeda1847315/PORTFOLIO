# 目次

- [Microsoft Entra 管理センター](#microsoft-entra-管理センター)
- [Active Directory](#active-directory)
- [Windows Server管理](#windows-server管理)
- [ネットワーク基礎](#ネットワーク基礎)
- [無線LAN](#無線lan)

---
## Microsoft Entra 管理センター
Azure AD ⇒「Microsoft Entra ID」<br>
　参考サイト：[Microsoft Learn](https://learn.microsoft.com/ja-jp/training/?utm_source=chatgpt.com)<br>

Microsoft Entra IDとは、Microsoftのユーザー管理システム<br>
　▶オンプレミス：Active Directory<br>
　▶クラウド：Microsoft Entra ID<br>

### Azureの基本概念
* テナントとは、会社専用の管理領域
* ユーザーとグループ管理<br>
ユーザーとは、テナントの利用者<br>
　アカウント例：tanaka@example.onmicrosoft.com<br>
グループとは、ユーザーをまとめる箱<br>
　基本は個別設定を避け、グループにアクセス権を設定する<br>
　　⇒アクセス権とは、アプリやSharePoint等へのアクセスする権利<br>
ロールとは、管理権限<br>
　　⇒管理権限とは、ユーザー作成や、パスワードリセットの操作権限<br>

* サブスクリプション<br>
テナントに紐付けるAzure利用契約（従量課金）
```text
テナント
├─ 開発サブスクリプション
└─ 本番サブスクリプション　等
```

* リソースグループとは、Azureリソースをまとめる箱<br>
例：
```text
├─ VM
├─ Database
└─ Storage
```

### ユーザー管理

* ユーザー作成
* パスワードリセット
* ユーザー無効化 ⇒退職者等の対応
* ユーザー削除
* グループ作成
* グループへのユーザー追加

### 権限管理

* 管理者ロール<br>
Global Administrator：<br>
　フルコントロール<br>
User Administrator：<br>
　ユーザー管理<br>
　パスワードリセット<br>
Helpdesk Administrator：<br>
　パスワードリセット<br>

* グループベース権限
* アプリケーションへのアクセス権

### 多要素認証（MFA［Multi-Factor Authentication］）

* MFAとは、1要素だけではない「異なる要素を組み合わせ」る認証
```text
ID
+
パスワード（知識情報）
+
スマホ認証（所持情報や、生体情報）
```
* MFA有効化方法
* 認証アプリ「Microsoft Authenticator」

## Active Directory

### ADユーザー管理
CN=ユーザーというオブジェクト管理

* ユーザー作成<br>
　　Get-ADUser yamada<br>
* 無効化（無効化するとログインできない）<br>
　　Disable-ADAccount<br>
* パスワード変更（次回ログイン時に変更させる設定も可能）<br>
　　Set-ADAccountPassword<br>

### OU（Organizational Unit）
組織単位（フォルダのようなもの）<br>
OUの中には、ユーザーやグループを格納
```text
会社
├ 総務部（OU=総務部）
├ 開発部（OU=開発部）
└ 営業部（OU=営業部）
    ├ 山田
    ├ 佐藤
    └ SG_営業部
```
管理を分ける為、営業部の管理者には、<br>
OU=営業部だけ管理権限を与える等の運用をする。<br>

### グループ
複数ユーザーをまとめ、単位で<br>
権限を付与や、アクセス制御する仕組み<br>
* Security Group<br>
　　権限管理用グループ（権限付与、メール配信、アクセス制御）<br>
* Distribution Group<br>
　　メール配信用グループ（メール配信のみ、メールグループ）<br>

### GPO（Group Policy Object）
Windows PCやユーザーに、グループポリシーとして、<br>
設定を配布（一括設定）する仕組み<br>
　⇒Site、Domainや、OUを指定できる<br>
例：
```text
パスワードポリシー（8文字以上、英数字混在、90日で変更 等）
壁紙(会社指定壁紙)
USB禁止
コントロールパネル禁止 等
```
イメージ：
```text
OU=営業部
 ↓
GPO適用
 ↓
営業部全員に反映
```

## Windows Server管理

### サービス管理
Windowsにはバックグラウンドで動作するプログラム（サービス）が存在
```text
DNS Server
DHCP Server
Print Spooler
Windows Update
IIS　等
```
サービスが停止すると機能が利用できなくなる。<br>
サービス状態確認：
```PowerShell
Get-Service
```
Statusが「Running」は起動中<br>
例えば「Stopped」であれば、停止中<br>
サービス起動：
```PowerShell
Start-Service
```
Stoppedから、Runningに変われば起動<br>
サービス停止：
```PowerShell
Stop-Service
```
サービス再起動
```PowerShell
Restart-Service
```
　⇒Stop-Serviceから、Start-Serviceと同義

### イベントログ
Windowsの「監査ログ」<br>
確認ツール（イベントビューアー）：
```cmd
eventvwr.msc
```
Application：アプリが落ちたなどを記録<br>
System：OS関連のサービス停止、ドライバ異常、ディスク障害等の記録<br>
Security：監査ログとして、ログオン成功、ログオン失敗、権限変更の記録<br>
```text
情報 (Information)　：正常動作
警告 (Warning)      ：今は動いているが問題の兆候あり
エラー (Error)      ：正常に処理できなかった
```

### タスクスケジューラ
定期実行ジョブとして、自動実行機能がある<br>
ファイルバックアップや、バッチ処理等

### Windows Update
OSの更新管理<br>
専用モジュール利用が一般的<br>

WSUS（Windows Server Update Services）を利用するケースがある<br>
　⇒Microsoftへ接続せずに、WSUSサーバーへ接続し、更新取得する。<br>
　　通信量削減、更新配布管理、適用タイミング統制のメリット

### NTFS権限
フォルダやファイルのアクセス権
* Read（読み取り）
```text
閲覧可能
変更不可
削除不可
```
* Modify（変更）
```text
閲覧
編集
作成
削除
```
* Full Control（完全制御）
```text
閲覧
編集
削除
権限変更
```
* 継承（Inheritance）<br>
親フォルダの権限を、子フォルダへ引き継ぐ（継承）仕組み<br>
管理が楽だが、意図しない権限が、付与されていないか注意する。

### 共有フォルダ権限
ユーザーがネットワーク経由でアクセスすると、<br>
共有権限 +NTFS権限の両方が、評価される。<br>
原則として、「より厳しい権限」が適用される
```text
共有権限
　Everyone
　Full Control
NTFS権限
　Read

結果
　　Read
```
細かい制御は「NTFS権限」で管理することが多い

## ネットワーク基礎

### IPアドレス
ネットワーク上の機器を識別するための住所<br>
同じネットワーク内ではIPアドレスが重複できない
* プライベートIPアドレス（IPv4の構成）
社内ネットワークなどで使用
```text
192.1XX.X.XX
 ↑
32bit　4つの数字で構成され、0～255の範囲
```

### サブネットマスク
IPアドレスの「どこまでがネットワーク部分」か
「どこからがホスト部分」かを示す。
```text
IPアドレス
192.168.1.10

サブネットマスク
255.255.255.0
```
　⇒「192.168.1.」まで同じなら、同一ネットワーク

* CIDR表記
```text
255.255.255.0 ＝ /24
```
| CIDR | サブネットマスク        |
| ---- | --------------- |
| /24  | 255.255.255.0   |
| /25  | 255.255.255.128 |
| /26  | 255.255.255.192 |
| /27  | 255.255.255.224 |


### デフォルトゲートウェイ
他ネットワークへ通信するときの出口<br>
ゲートウェイが間違っていると、社内通信は<br>
できるが、インターネット通信できない

### DNS
名前解決とは、URLをIPアドレスへ変換する仕組み<br>
google.com = 142.250.xxx.xxxへDNSで自動変換する。<br>
　補足：
```cmd
nslookup google.com
```
Addresses:にあるIPでアクセスする事ができる。

### DHCP（Dynamic Host Configuration Protocol）
IPアドレスを自動配布する仕組み<br>
　⇒動的ホスト構成プロトコル<br>
　　ネットワーク機器に対して、IPアドレスやDNSサーバーなどの<br>
　　ネットワーク設定を、自動配布するためのプロトコル（通信ルール）
```text
PC起動
　↓
DHCPサーバーへ要求
　↓
以下の情報等を自動取得
IPアドレス
サブネットマスク
ゲートウェイ
DNS
```
DHCPなしの場合、上記は手動設定が必要<br>
管理もあるので、一般家庭レベルのルーターにもDHCPサーバー機能がある。

## 無線LAN
LANケーブルを使わず、電波で通信するネットワーク。
```text
PC
 ↓
Wi-Fi
 ↓
アクセスポイント
 ↓
ルーター
 ↓
インターネット
```
### SSID（Service Set Identifier）
Wi-Fiのネットワーク名<br>
スマホやPCで表示される「利用可能なネットワーク」の名前<br>
ステルスSSIDの場合もある。<br><br>
保存済みWi-Fi一覧：
```cmd
netsh wlan show profiles
```
接続済Wi-Fi：
```cmd
netsh wlan show interfaces
```

### WPA（Wi-Fi Protected Access）
Wi-Fi通信を保護する暗号化方式<br>
Wi-Fi保護アクセス
* WPA3<br>
WPA2の後継　現在はこちらが推奨
```text
より強力な暗号化
辞書攻撃への耐性向上　
```

* WPA2<br>
現在でも広く利用される暗号化方式
```text
AES暗号
高い安全性
多くの機器が対応
```

### 周波数帯

* 2.4GHz
特徴：
```text
遠くまで届く
壁に強い
速度は遅め
```
デメリットとして、家電と周波数が被る。<br>
そのため干渉しやすい。

* 5GHz
特徴：
```text
高速
安定
干渉が少ない
```
デメリットは、壁に弱い為、遠くまで届きにくい

### アクセスポイント(AP)<br>
Wi-Fiの電波を出す機器。

SSIDが見えない
```cmd
netsh wlan show networks
```

接続できない
```cmd
netsh wlan show interfaces
```

インターネットに繋がらない
```cmd
ipconfig
```

⇒ipconfigの出力結果で、確認する場所：
```text
IPv4 Address
Default Gateway
DNS Server
```