もちろんです。「Windows運用担当が最初の1週間で覚えるべきPowerShellコマンド20選」を、実務で使う頻度が高い順に近い形で紹介します。

### まずはこれだけ

最初の1週間で特に優先度が高いのは次の3つです。

1. Get-ChildItem — ファイル一覧を見る

   Linuxの ls、Windowsの dir に相当。

2. Get-Service — サービス状態を確認

   サーバ監視・障害対応で頻出。

3. Get-Process — 実行中プロセスを確認

   CPU高騰・メモリ不足調査で使います。

### Windows運用担当が覚えるべき20コマンド

| コマンド             | 何をするか         |
| ---------------- | ------------- |
| Get-Date         | 現在日時を取得       |
| Get-Location     | 現在のフォルダを確認    |
| Get-ChildItem    | ファイル・フォルダ一覧表示 |
| Set-Location     | フォルダ移動        |
| Get-Content      | テキストファイル内容表示  |
| Copy-Item        | コピー           |
| Move-Item        | 移動            |
| Remove-Item      | 削除            |
| Get-Service      | サービス一覧確認      |
| Start-Service    | サービス起動        |
| Stop-Service     | サービス停止        |
| Restart-Service  | サービス再起動       |
| Get-Process      | プロセス確認        |
| Stop-Process     | プロセス終了        |
| Get-EventLog     | イベントログ確認      |
| Get-LocalUser    | ローカルユーザー確認    |
| Test-Connection  | ネットワーク疎通確認    |
| Get-NetIPAddress | IPアドレス確認      |
| Import-Csv       | CSV読込         |
| Export-Csv       | CSV出力         |

### 実務での使い方例

### 1. サービスが止まっているか確認

プリンタ関連障害でよく使います。

### 2. サービスを再起動

GUIで操作するより速いことが多いです。

### 3. イベントログ確認

直近20件のシステムログを見る。

### 4. ネットワーク疎通確認

Linuxの ping に近い。

### 5. IPアドレス確認

NICのIP設定を確認。

### 新人がまず覚えるべき考え方

### PowerShellの基本は「Getから始める」

危険度が低く、情報を見るだけです。

まずは「見る」コマンドを使い慣れるのが重要。

### 次に「操作系」を覚える

変更を加えるコマンドなので、実行前に確認を習慣化。

### 削除系は最後

本当に消える／止まるので慎重に。

### 実務で最初に覚える鉄板3パターン

### ① 一覧を見る

### ② 状態を確認する

### ③ ネットワークを確認する

### 現場でよくある流れ

「サーバにつながらない」

→ `Test-Connection`

→ 「サービスが落ちている」

→ `Get-Service`

→ 「再起動する」

→ `Restart-Service`

1週間でのおすすめ練習メニュー

1. PowerShellを開く

2. `Get-ChildItem` を実行

3. `Get-Service` を実行

4. `Get-Process` を実行

5. `Get-EventLog -LogName System -Newest 10` を実行

6. `Test-Connection localhost` を実行

7. `Get-Help Get-Service -Examples` を実行

この7つを実際に触るだけでも、現場での「何を見ればいいか」がかなり分かるようになります。

次に進めるなら、 ユーザー管理系のPowerShellコマンド（Entra ID/AD寄り）を学ぶ や イベントログ調査の実践的な読み方を学ぶ がおすすめです。

就業前の事前学習としては、とても良いテーマですね。

記載されている内容を見ると、実際には

* Azure（特にユーザー管理）
* Windows Server運用
* PowerShell
* Active Directory系のアカウント管理
* ネットワーク基礎

を担当する「インフラ運用・ヘルプデスク寄り」の業務だと思われます。

優先順位を付けると以下になります。

# ① Microsoft Entra 管理センター（最優先）

以前のAzure ADの名称が現在は「Microsoft Entra ID」です。

まず覚えること

### ユーザー管理

* ユーザー作成
* パスワードリセット
* ユーザー無効化
* ユーザー削除
* グループ作成
* グループへのユーザー追加

### 権限管理

* 管理者ロール
* グループベース権限
* アプリケーションへのアクセス権

### 多要素認証（MFA）

* MFAとは何か
* MFA有効化方法
* 認証アプリ

実務では

「社員が入社したのでアカウント作ってください」

「退職したので無効化してください」

が非常に多いです。

まずはここを理解すると強いです。

---

# ② PowerShell（かなり重要）

Windows運用では必須です。

覚えるべき基本

### コマンド実行

```powershell
Get-Date
Get-Service
Get-Process
```

### ファイル操作

```powershell
Get-ChildItem
Copy-Item
Move-Item
Remove-Item
```

### ユーザー管理

```powershell
Get-LocalUser
New-LocalUser
```

### CSV操作

```powershell
Import-Csv
Export-Csv
```

実務では

```powershell
ユーザー100人を一括登録
```

みたいな作業でよく使います。

---

# ③ Windows Server管理

かなり重要です。

覚えるべき項目

### サービス管理

```text
サービスの起動
サービスの停止
サービスの再起動
```

### イベントログ

イベントビューア

```text
Application
System
Security
```

の違い

### タスクスケジューラ

定期実行ジョブ

### Windows Update

* 更新確認
* 更新適用
* 再起動

---

# ④ Active Directory（超重要）

求人票に直接書かれていなくてもほぼ出てきます。

覚えること

### ADユーザー管理

* ユーザー作成
* 無効化
* パスワード変更

### OU

組織単位

```text
営業部
総務部
開発部
```

みたいな管理

### グループ

* Security Group
* Distribution Group

### GPO

グループポリシー

---

# ⑤ アクセス権設定

実務頻度高いです。

### NTFS権限

* Read
* Modify
* Full Control

### 共有フォルダ権限

```text
共有権限
＋
NTFS権限
```

で決まること

### よくある依頼

```text
○○フォルダに入れません
```

```text
○○さんに閲覧権限を追加してください
```

---

# ⑥ ネットワーク基礎

ここは深くなくて大丈夫です。

最低限

### IPアドレス

```text
192.168.1.10
```

### サブネットマスク

```text
255.255.255.0
```

### デフォルトゲートウェイ

### DNS

名前解決

---

### 確認コマンド

```cmd
ipconfig
```

```cmd
ping
```

```cmd
nslookup
```

```cmd
tracert
```

---

# ⑦ 無線LAN

覚えること

### SSID

Wi-Fi名

### WPA2

暗号化

### WPA3

新しい暗号化

### よくある障害

* 接続できない
* 認証エラー
* IP取得失敗

---

# Azure関連で最低限触っておくべき内容

もし個人で学習するなら、まずは

[Microsoft Learn](https://learn.microsoft.com/ja-jp/training/?utm_source=chatgpt.com)

で以下を学ぶのがおすすめです。

* Microsoft Entra IDとは
* Azureの基本概念
* Azureポータルの使い方
* ユーザーとグループ管理

---

# 2週間で学習するなら

### 1〜3日目

* Entra ID
* ユーザー管理
* グループ管理

### 4〜6日目

* PowerShell基本
* コマンド練習

### 7〜10日目

* Windows Server
* イベントログ
* サービス管理

### 11〜12日目

* Active Directory
* GPO

### 13〜14日目

* IPアドレス
* DNS
* ping
* nslookup

---

この案件内容を見る限り、最初の1〜3か月で最も多く触る可能性が高いのは

**① Microsoft Entra ID（Azure AD）**
**② PowerShell**
**③ Active Directoryのユーザー管理**

です。

まずこの3つを重点的に学習すると、現場でかなりスムーズに入れると思います。

もちろんです。

PowerShellは最初は「黒い画面で呪文を打つもの」に見えますが、実はコマンド名に意味があるので覚えやすいです。

まずPowerShellのコマンドは、

```text
動詞-対象
```

というルールで命名されています。

例えば、

```powershell
Get-Date
```

は

```text
Get（取得する）
Date（日付）
```

なので、

**「現在の日付を取得する」**

という意味になります。

---

# Get-Date

```powershell
Get-Date
```

実行結果例

```text
2026年6月12日 21:30:15
```

用途

* 現在日時確認
* スクリプト内で日時取得

---

# Get-Service

```powershell
Get-Service
```

意味

```text
Get = 取得
Service = サービス
```

Windowsサービス一覧を表示します。

実行例

```powershell
Get-Service
```

結果

```text
Status Name
------ ----
Running Spooler
Running EventLog
Stopped WSearch
```

よく見るサービス

* Spooler（印刷）
* EventLog（イベントログ）
* WSearch（検索）

---

## 特定のサービス確認

```powershell
Get-Service Spooler
```

結果

```text
Running
```

プリンタサービスが動いているか確認できます。

---

# Get-Process

```powershell
Get-Process
```

意味

```text
Get = 取得
Process = プロセス
```

起動中のプログラム一覧を取得します。

例

```powershell
Get-Process
```

結果

```text
chrome
explorer
notepad
```

---

## Chromeだけ確認

```powershell
Get-Process chrome
```

実務でよくあります。

```text
Chromeが応答しない
```

↓

```powershell
Get-Process chrome
```

で確認。

---

# Get-ChildItem

```powershell
Get-ChildItem
```

意味

```text
Get = 取得
ChildItem = 配下の項目
```

Linuxの

```bash
ls
```

Windowsの

```cmd
dir
```

と同じです。

---

実行

```powershell
Get-ChildItem
```

結果

```text
Documents
Downloads
test.txt
```

フォルダやファイル一覧を表示します。

---

## 短縮形

```powershell
ls
```

でもOKです。

---

# Copy-Item

```powershell
Copy-Item
```

意味

```text
Copy = コピー
Item = ファイルやフォルダ
```

例

```powershell
Copy-Item test.txt backup.txt
```

結果

```text
test.txt
↓
backup.txt
```

が作成されます。

---

# Move-Item

```powershell
Move-Item
```

意味

```text
Move = 移動
Item = ファイルやフォルダ
```

例

```powershell
Move-Item test.txt C:\Backup\
```

結果

```text
test.txt
↓
C:\Backup\
```

へ移動。

---

# Remove-Item

```powershell
Remove-Item
```

意味

```text
Remove = 削除
Item = ファイルやフォルダ
```

例

```powershell
Remove-Item test.txt
```

注意

```text
本当に削除される
```

ので実務では慎重に。

---

# Get-LocalUser

```powershell
Get-LocalUser
```

意味

```text
Get = 取得
LocalUser = ローカルユーザー
```

Windowsに登録されているユーザー一覧を取得。

結果例

```text
Administrator
Guest
user01
```

---

# New-LocalUser

```powershell
New-LocalUser
```

意味

```text
New = 新規作成
LocalUser = ローカルユーザー
```

例

```powershell
New-LocalUser -Name user01
```

ユーザーを新規作成します。

---

# Import-Csv

```powershell
Import-Csv
```

意味

```text
Import = 読み込み
Csv = CSVファイル
```

例

users.csv

```csv
Name
user01
user02
user03
```

PowerShell

```powershell
Import-Csv users.csv
```

結果

```text
user01
user02
user03
```

がデータとして扱えます。

---

# Export-Csv

```powershell
Export-Csv
```

意味

```text
Export = 出力
Csv = CSVファイル
```

例

```powershell
Get-LocalUser | Export-Csv users.csv
```

結果

```text
ユーザー一覧
↓
users.csv
```

として保存。

---

# 実務で最もよく見る形

PowerShellの強みは「パイプライン」です。

この記号です。

```powershell
|
```

読み方

```text
パイプ
```

意味

```text
左の結果を右へ渡す
```

例

```powershell
Get-Process | Sort-Object CPU
```

意味

```text
プロセス一覧取得
↓
CPU順に並べる
```

---

例えば、

```powershell
Get-LocalUser | Export-Csv users.csv
```

は

```text
ユーザー一覧取得
↓
CSV出力
```

という流れです。

---

現場に入る前なら、まず以下の10個を実際に打ってみるのがおすすめです。

```powershell
Get-Date
Get-ChildItem
Get-Location
Get-Service
Get-Process
Get-LocalUser
Get-Help
Get-Command
Import-Csv
Export-Csv
```

特に

```powershell
Get-Help Get-Service
```

は非常に重要です。

「Get-Serviceの説明を表示して」

という意味で、PowerShellを覚える近道になります。実務でも分からないコマンドが出たら `Get-Help` をよく使います。

現場でよく使う調べ方

私は新人向けには次の流れをおすすめします。

Get-Command *service*

↓

Get-Help Get-Service

↓

Get-Help Get-Service -Examples

↓

Get-Help Get-Service -Online
