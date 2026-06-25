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

概要に、以下の情報<br>
名前<br>
テナント ID<br>
プライマリ ドメイン<br>
ライセンス<br>
　※自身は「Microsoft Entra ID Free」

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
csvは、プライマリドメインの修正と、日本語登録する場合を考え、<br>
UTF-8（できれば UTF-8 BOM付き）で保存する

#### ユーザー作成
　一括作成：<br>
Entra ID> ユーザー（全てのユーザー）>･･･の一括操作> 一括作成<br>
　　⇒テンプレートは最新をダウンロードする<br>
参考：[Microsoft Entra ID でユーザーを一括作成する](https://learn.microsoft.com/ja-jp/entra/identity/users/users-bulk-add?utm_source=chatgpt.com#understand-the-csv-template)

PowerShellでのユーザー作成<br>
　※Microsoft Graph PowerShell SDK インストール
1. Microsoft Graph接続<br>

前回のアカウント接続：
```powershell
Connect-MgGraph -Scopes User.ReadWrite.All
```
接続確認：
```powershell
Get-MgContext
```
⇒ユーザーの読み取りと書き込み（登録）権限でサインインしているか<br><br>
別アカウント接続したい場合：
```powershell
Disconnect-MgGraph
```
※権限の一覧確認：
```powershell
(Get-MgContext).Scopes
```
| 権限                      | 内容        |
| ----------------------- | --------- |
| User.Read               | 自分を見る     |
| User.Read.All           | 全ユーザー閲覧   |
| User.ReadWrite.All      | ユーザー作成・変更 |
| Group.Read.All          | グループ閲覧    |
| Group.ReadWrite.All     | グループ変更    |
| Directory.Read.All      | Entra情報参照 |
| Directory.ReadWrite.All | Entra管理   |

2. CSV確認
```powershell
Import-Csv .\XXX.csv
```
⇒読み取れるか
```powershell
Get-Content .\XXX.csv
```
⇒登録内容の確認

3. CSVインポート（変数代入）
```powershell
$Users = Import-Csv C:\Users\User\Desktop\XXX.csv
```
⇒CSVの1行 = オブジェクト<br>
　確認：
```powershell
$Users
```
⇒$Users[0]等で、1行単位で見れる。<br>
　.Password等を後ろに加え、1セル単位も可能。

4. 一括登録（拡張for文で回す）<br>
　例：
```powershell
foreach ($User in $Users) {

    $PasswordProfile = @{
        Password = $User.Password
        ForceChangePasswordNextSignIn = $true
    }

    New-MgUser `
        -DisplayName $User.DisplayName `
        -UserPrincipalName $User.UserPrincipalName `
        -MailNickname ($User.UserPrincipalName.Split('@')[0]) `
        -GivenName $User.GivenName `
        -Surname $User.Surname `
        -Department $User.Department `
        -JobTitle $User.JobTitle `
        -AccountEnabled `
        -PasswordProfile $PasswordProfile

    Write-Host "$($User.DisplayName) 作成完了"
}
```
⇒Graph APIは、パスワード情報を、1つのオブジェクトとして渡す。<br>
　その為、ハッシュテーブルを用い、1つの変数に格納する
```powershell
$PasswordProfile　@{ パスワード情報}
```
⇒ハッシュテーブル = キーと値のセット（JavaのMapに近い）<br>
　なので、$PasswordProfile.Passwordで、値を確認できる。

#### ユーザー削除<br>
ユーザー削除後も一定期間（30日）はゴミ箱に残る

ユーザー削除：
```powershell
Remove-MgUser -UserId アカウント情報.onmicrosoft.com
```
⇒ -Confirm:$falseを後ろに付けると確認無し

削除済ユーザー 一覧確認
```powershell
Get-MgDirectoryDeletedItemAsUser
```

復元
```powershell
Restore-MgDirectoryDeletedItem
```
⇒DirectoryObjectId:に、復元したい「Id」を入力

#### グループ作成
基礎として、以下は、オンプレミスADに多い管理
```text
OU
 ↓
ユーザーを入れる
 ↓
グループ　
```

Entraでは以下のフローになる
```text
ユーザー
 ↓　作成
グループ
 ↓　グループ作成
 ↓  グループへユーザー追加
 ↓　グループ所有者設定
ロール
 ↓　管理者権限等の付与（管理者ロール割り当て）
アプリ
 ↓　エンタープライズアプリ確認
権限
　　アクセス制御（アプリの使用可否）
　　条件付きアクセス
```

※エンタープライズアプリ
```text
SAML認証
SSO
アプリ割り当て
　アプリ例：
Microsoft Teams
Microsoft Outlook
Salesforce
Box
ServiceNow　等
```

※条件付きアクセス（Conditional Access）
```text
・社内IPのみ許可
・海外からのアクセス拒否
・管理者は必ずMFA
・Windows端末のみ許可　等

条件：
　ユーザー
　グループ
　場所
　デバイス
　　⇓
制御：
　MFA要求
　ブロック
　許可
```

作成手順<br>
Microsoft Entra 管理センター >Entra ID（グループ） >すべてのグループの「新しいグループ」<br>
必要項目を入力後「作成」

* グループの種類

| 種類            | 用途               |
| ------------- | ---------------- |
| Security      | 権限管理             |
| Microsoft 365 | Teamsや共有メールボックス等 |

* グループ名
* グループの説明
* メンバーシップの種類
```text
Assigned
  手動で追加

Dynamic User
  条件に一致したユーザーを自動追加

Dynamic Device
  条件に一致したデバイスを自動追加
```

* グループの削除<br>
概要上部に削除ボタン<br>
一覧からは消えるが、すぐには完全削除されない。<br>
一定期間「論理削除」の状態になる。<br>

* 復元<br>
削除したグループ >チェックして「グループの復元」<br>
当時の登録内容のまま、復元される。<br>
　⇒一覧にない または「完全に削除」を押下すると、元に戻せない

#### グループへのユーザー追加
ダイレクト メンバー = メンバーの合計数<br>
所有者は「このグループを管理できる担当者」<br>
　⇒オンプレADの「Managed By」に近い概念<br>
　　＝管理者ロールではない<br>
　　　　グループ所有者 ≠ グローバル管理者<br>

| 項目         | 意味              |
| ---------- | --------------- |
| 所有者        | グループを管理できる人     |
| メンバー       | グループに所属する人      |
| ダイレクト メンバー | 直接追加されたメンバー     |
| メンバーの合計数   | グループに所属する全メンバー数 |

#### パスワードリセット
Microsoft Entra 管理センター> ユーザー> 対象ユーザークリック<br>
右上「･･･」 >パスワードのリセット<br>

「パスワードのリセット」を押下し、<br>
成功後「一時パスワード」を対象ユーザーに伝える。<br>
PowerShell例：
```powershell
Connect-MgGraph -Scopes User.ReadWrite.All

$PasswordProfile = @{
    Password = "TempPass123!"
    ForceChangePasswordNextSignIn = $true
}

Update-MgUser `
    -UserId testuser@tenant.onmicrosoft.com `
    -PasswordProfile $PasswordProfile
```
セルフサービスパスワードリセット（SSPR）も仕組みとしてある<br>
　⇒本人確認の後、自分で再設定

#### ユーザー無効化
⇒退職者等の対応<br>
Microsoft Entra 管理センター> ユーザー> 対象ユーザークリック<br>
上部「プロパティの編集」 >設定

「アカウントが有効化されました」のチェックボックスのフラグを外し、保存<br>
PowerShell例：
```powershell
Update-MgUser `
    -UserId testuser@tenant.onmicrosoft.com `
    -AccountEnabled:$false
```
補足 有効化：
```powershell
Update-MgUser `
    -UserId testuser@tenant.onmicrosoft.com `
    -AccountEnabled:$true
```

### 権限管理
Microsoft Entra 管理センター >（役割と管理者）ロールと管理者

#### 管理者ロール（RBAC）<br> 
Role Based Access Control（ロールベースアクセス制御）<br>
ユーザーに直接、権限を与えるのではなく、以下で管理
```text
ロール（役割）
　　↓
ユーザーへ割り当て
```

* Global Administrator<br>
　フルコントロール<br>
　Windowsの「Administrators グループ」に近い<br>

できる事
```text
ユーザー作成
ユーザー削除
グループ管理
ロール割り当て
アプリ登録
ライセンス管理
認証設定
条件付きアクセス（P1以上）
```

* User Administrator<br>
　ユーザー管理専用管理者<br>

できる事
```text
ユーザー作成
ユーザー削除
パスワードリセット
グループ管理
```
できない事
```text
Global Administrator追加
認証設定変更
テナント設定変更
```

* Helpdesk Administrator：<br>
　ヘルプデスク向け<br>

できる事
```text
パスワードリセット
サインイン問題対応
```
できない事
```text
ユーザー作成
ユーザー削除
```

#### グループベース権限<br>
ユーザー毎に権限を付与していった場合、<br>
人数が増えると管理不能<br>
その為、作成したグループに対し、権限を設定する。

#### アプリケーションへのアクセス権<br>
Microsoft Entra 管理センターの<br>
「エンタープライズ アプリ」で、割り当てる
### 多要素認証（MFA［Multi-Factor Authentication］）

MFAとは、1要素だけではない<br>
「異なる要素を組み合わせ」る認証<br>
もし、ID（メールアドレス）と、パスワードの組み合わせが<br>
漏洩しても、悪意あるログインを防げる可能性が高くなる

#### 認証要素の種類<br>
* 知識情報（Something You Know）<br>
本人しか知らない情報
```text
パスワード
PINコード
秘密の質問
```

* 所持情報（Something You Have）<br>
本人しか持っていないもの
```text
スマートフォン
認証アプリ
ICカード
セキュリティキー
```

* 生体情報（Something You Are）<br>
身体的特徴
```text
指紋
顔認証
虹彩認証
```

組み合わせのケース：
```text
ID
+
パスワード（知識情報）
+
スマホ認証（所持情報や、生体情報）
```
#### MFA有効化方法<br>
Microsoft Entra 管理センター >認証方法 からセットアップできる<br>
ユーザー毎に設定できるが、上記では全社設定になる<br>

#### 認証アプリ
```text
Microsoft Authenticator
Google Authenticator 等
```
サインイン要否のプッシュ通知や、<br>
OTP（One Time Password）の確認ができる

#### サインインログの確認
Microsoft Entra 管理センター >監視と正常性 >サインイン ログ<br>
確認できる項目 抜粋：
```text
日時
ユーザー
アプリケーション
IPアドレス
場所
状態
```
日時（リンク）をクリックする事で<br>
「アクティビティの詳細」が確認できる。<br>
その中の［場所］の詳細で「IPアドレス」や［デバイス情報］で<br>
「オペレーティング システム」や「デバイス ブラウザー」等の詳細が確認できる。

## Active Directory
#### Active Directoryを動かしているサーバ ＝ ドメインコントローラー（DC）
役割：<br>
　認証<br>
　GPO配布<br>
　DNS<br>

故障等の障害で、認証不可になるのを防ぐ為、<br>
2台以上の複数DCで運用する事が多い。<br>
複数DCの場合、データ同期処理として、<br>
レプリケーションされる（＝冗長化）<br>

ユーザーが登録されていて、階層があるOU（組織単位）を作成した場合、<br>
次のようなLDAP（Lightweight Directory Access Protocol）形式で管理される<br>
　※LDAP形式：LDAPディレクトリ内で、オブジェクトの場所を表す<br>
　　DN（Distinguished Name）の記述形式<br>
　　組織内のユーザーやコンピュータなどの情報を階層的に管理し、<br>
　　検索・認証するための標準プロトコル
```text
例：
ユーザー名: taro
OU: Nagoya
親OU: Japan
ドメイン: example.local

CN=taro,OU=Nagoya,OU=Japan,DC=example,DC=local
　⇒CN=<ユーザー名>,OU=<OU名>,OU=<親OU名>,DC=<ドメイン名>,DC=<トップレベルドメイン>
　　LDAPでは 下位オブジェクトから、上位オブジェクトへの順で記述する。

参考：
CN = Common Name（ユーザー名やコンピュータ名）
OU = Organizational Unit
DC = Domain Component
```

クラウド連携する場合、AD ⇒ Entraの「片方向同期」になる<br>
```text
Windows Server［Active Directory Users and Computers（ADUC）］
　⇒オンプレAD（認証基盤）
　　CN、OU、DCの一括りで個別のユーザー管理

　⇓ Entra Connect Sync（旧Azure AD Connect）

Microsoft Entra 管理センター
　⇒クラウド（MicrosoftAzure内）
　　アカウントの登録内容で、個別のユーザー管理
　　例：･･･.onmicrosoft.com
```
パスワード同期は、Password Hash Synchronization(PHS)<br>
　⇒ADに登録されたパスワードのハッシュ値を同期<br>
これでSSO（シングルサインオン）が実現できる<br>
　※PTA (Pass-through Authentication)<br>
　　　ログイン時に毎回、ADへ問い合わせる仕組み<br>
　　　その為、AD環境が停止すると、認証不可になる可能性があり、PHS推奨<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Federation (Active Directory Federation Services）<br>
　　　認証を全て、ADFSサーバへ任せる方式もある<br>
　　　認証ルールを細かく制御可能だが、構築が大変なので、最近は減少傾向<br>

　
OUは同期対象を決められる<br>
　⇒注意として、OUそのものは、同期されない<br>
　　ユーザー、グループ、属性が同期対象で、<br>
　　そこに紐付けるイメージ<br>

GPOはEntraに同期されない

### ADユーザー管理
CN=ユーザーというオブジェクト管理

* 既存ユーザーの照会（取得）<br>
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
AD参加前のWindowsは、<br>
PC自身がアカウントを管理（＝ローカルアカウント）<br>
AD参加後は、ドメインアカウントで認証する

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

### リモートデスクトップ(RDP)
離れた場所からサーバーを操作
```cmd
mstsc
```
サーバーIP又は、サーバー名で接続する<br>
ポート番号：3389/TCP<br>
疎通の確認方法：
```PowerShell
Test-NetConnection IPアドレス -Port 3389
```

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

### パフォーマンス監視
サーバーが重い時に調査<br>
　リソースモニター起動：
```cmd
resmon
```
どのプロセスがCPUを使うか<br>
どのプロセスが通信しているか

　パフォーマンスモニター起動：
```cmd
perfmon
```
CPU使用率<br>
メモリ使用量<br>
ディスク待ち<br>
ネットワーク使用率<br>

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
細かい制御は「NTFS権限」で管理することが多い<br>
確認方法：
```PowerShell
net share
```
管理共有として、管理者ならアクセス可能なフォルダを持てる
```text
C$
D$
ADMIN$
IPC$
　※末尾の「$」は隠し共有
```

## ネットワーク基礎

### IPアドレス
ネットワーク上の機器を識別するための住所<br>
同じネットワーク内ではIPアドレスが重複できない
* プライベートIPアドレス（IPv4の構成）<br>
社内ネットワークなどで使用
```text
192.1XX.X.XX
 ↑
32bit　4つの数字で構成され、0～255の範囲
```

### サブネットマスク
IPアドレスの「どこまでがネットワーク部分」か<br>
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

### TCP/IP
IPアドレスを、TCPや、UDPに則り変換する
| プロトコル | ポート  |
| ----- | ---- |
| HTTP  | 80   |
| HTTPS | 443  |
| DNS   | 53   |
| RDP   | 3389 |
| LDAP  | 389  |
| LDAPS | 636  |

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

### 疎通の確認方法
```cmd
ping
```
　⇒通信確認<br>
応答がない場合：
```cmd
tracert
```
　⇒どこで通信が止まっているかの詳細確認

補足：
現在、どこと、どんな通信（接続）をしているかを一覧表示するコマンド
```cmd
netstat -ano
```
-a：すべての接続状態（待ち受け中も含む）を表示する<br>
-n：名前（ドメイン名など）ではなく、<br>
　　数字（IPアドレスやポート番号）で表示する（処理が早くなる）<br>
-o：その通信を行っているプログラムの識別番号（PID：プロセスID）を表示する<br>

不審な通信（ウイルスなど）が勝手に行われていないか確認する<br>
「ポートが重複してアプリが起動できない」というときに、<br>
どのプログラム（PID）がそのポートを占有しているかを特定する。<br>

IPアドレスからMACアドレスを調べる（変換する）：
```cmd
arp -a
```
一度、通信した相手の「IPアドレスと、<br>
MACアドレスの対応表（ARPキャッシュ）」を画面に表示するコマンド

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
一般家庭は、WPA2-PSK（共通のパスワード）が多いが、<br>
企業はWPA2-Enterpriseが多い<br>
　⇒個人ごと（または端末ごと）に、IDとパスワード、<br>
　　あるいは電子証明書を使って個別に認証する方式<br>
　　認証サーバー(RADIUS)を用い、802.1X認証を行う<br>
　　　※PC ⇒ AP ⇒ RADIUS ⇒ AD<br>
　　パスワード漏洩がなく、アカウント（ID）を<br>
　　無効化するだけで接続不可にできる<br>

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
デメリットは、壁に弱い為、遠くまで届きにくい<br>
近年は、6GHz（Wi-Fi 6E）がある

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