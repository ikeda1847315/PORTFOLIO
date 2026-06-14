

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