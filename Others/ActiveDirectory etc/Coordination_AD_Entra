#### ADドメインの確認：
Windows Server >サーバーマネージャー >ローカル サーバー
```text
コンピューター名
ドメイン　
ワークグループ
```
```PowerShell
(Get-ADDomain).DNSRoot
```

ドメインコントローラー詳細：
```PowerShell
Get-ADDomain
```
例：
```text
DNSRoot
NetBIOSName
PDCEmulator
```

#### Active Directoryフォレスト全体の構成情報
```PowerShell
Get-ADForest
```
以下を確認できる
```text
ADの構造
├─ フォレストはいくつ？
├─ ドメインはいくつ？（Domains）
├─ UPNサフィックスは？（UPNSuffixes）
├─ グローバルカタログは？（GlobalCatalogs ＝ DCの数）
└─ FSMOはどこ？（UPNSuffixes）
```
* 複数ドメインだったり、複数フォレストでないか確認する<br>
　 ⇒複数の場合、Entra Connectの設定が複雑になる
* Domains<br>
   　⇒ADで管理しているドメイン
* UPNSuffixes<br>
   　⇒ユーザーがログイン時に利用できるUPNサフィックスの追加一覧

#### 同期用OU作成
サーバー マネージャー >ツール >Active Directory ユーザーとコンピューター
1. ドメインを右クリック
2. 新規作成 >組織単位(OU)
3. 名前を入力し、OK
作成確認：
```PowerShell
Get-ADOrganizationalUnit `
-Filter 'Name -eq "登録した名前"'
```

#### UPN（User Principal Name）とは
2種類のログオン名がある
```text
SamAccountName：アカウント名
UPN：アカウント名@ADドメイン
```
その為、以下のドメインアカウントで、Windowsにサインインできる
```text
NetBIOSName\sAMAccountName ：Down-Level Logon Name
sAMAccountName@UPNSuffixes ：UPN
　⇒表記上の見分けとして記載しているが、
　　本来はこれ１つでUserPrincipalName (UPN)扱い
```
sAMAccountName一覧出力：
```PowerShell
Get-ADUser -Filter * -Properties sAMAccountName,UserPrincipalName |
    Select-Object Name,sAMAccountName,UserPrincipalName
```
特定ユーザー検索：
```PowerShell
Get-ADUser ○○ -Properties UserPrincipalName,sAMAccountName
```

#### Microsoft Entra Connect Syncのインストール
今回は、DCと兼務<br>
しかし、Entra Connectは、オンプレADとEntra IDを<br>
つなぐ非常に重要なシステムの為、<br>
一般的には、役割を分離で、分けて準備する事が多い。（冗長化含む）<br>
　⇒Entra Connectには「ADへの権限」と「Entraへの権限」の両方が必要<br>
```text
サーバー障害：AD認証停止と、同期停止が同時発生
Entra Connectのアップグレード時：同期サービス再起動
```
AzureADConnect.msiを用い、<br>
Microsoft Entra Connect Sync をインストール<br>
　⇒Microsoft Entra 管理センター >Entra Connect >管理<br>

Azure AD Connectのインストール完了後から、<br>
AD ⇒ Entraの同期が可能になる。<br>

起動確認：
```PowerShell
Get-Service ADSync
```
　⇒Statusが「Running」で起動中

MicrosoftEntraConnectの同期スケジュールや、設定情報を確認：
```PowerShell
Get-ADSyncScheduler
```
SyncCycleEnabled : True　⇒自動同期有効<br>
CurrentlyEffectiveSyncCycleInterval : 00:30:00　⇒同期間隔（30分）<br>
StagingModeEnabled : False　⇒本番同期モード（ステージングモードではない）<br>

同期処理：<br>
Entra Connectは、デフォルトで「30分ごと」に、<br>
差分同期（Delta Sync）を実行する<br>

手動同期は、２パターンある<br>
変更分だけ同期：
```PowerShell
Start-ADSyncSyncCycle -PolicyType Delta
```

フル同期：
```PowerShell
Start-ADSyncSyncCycle -PolicyType Initial
```
⇒全オブジェクトを再評価<br>
　以下の場合に、フル同期が多い
```text
OUフィルタ変更
同期ルール変更
大規模な構成変更
```

#### パスワードリセット例
パスワード再設定：
```PowerShell
Set-ADAccountPassword `
-Identity アカウント名 `
-Reset `
-NewPassword (ConvertTo-SecureString "仮パスワード" -AsPlainText -Force)
```

項目説明：
```text
-Identity：対象ユーザー
-Reset：現在のパスワードを知らなくても変更
-NewPassword：新しいパスワード
```

次回サインイン時にパスワード変更：
```PowerShell
Set-ADUser `
-Identity アカウント名 `
-ChangePasswordAtLogon $true
```
⇒$falseにすれば、変更なしにできる<br>

パスワード変更強制確認：
```PowerShell
Get-ADUser TesTaro `
-Properties PasswordLastSet |
Select Name,PasswordLastSet
```
⇒PasswordLastSetに、日時が<br>
　入っていれば、強制していない<br>

パスワード変更は、管理者（人間）側で、<br>
有効期限切れは、ポリシーに則り、システム側で<br>
パスワード変更を強制する

#### アカウントロック
ロック状態等の確認
```PowerShell
Get-ADUser アカウント名 `
-Properties Enabled,LockedOut,PasswordExpired |
Select Name,Enabled,LockedOut,PasswordExpired
```
⇒LockedOut：Trueはロック中
　他、Enabled：True＝アカウント有効
　PasswordExpired=False：パスワード期限切れなし

アカウントロック解除
```PowerShell
Unlock-ADAccount -Identity アカウント名
```

パスワードポリシー確認：
```PowerShell
Get-ADDefaultDomainPasswordPolicy
```
項目説明：<br>
ComplexityEnabled：
```text
パスワード複雑性要件
True = 有効

有効の場合は以下のルール
・ユーザー名を含まない
・4種類中3種類以上を含む
　大文字
　小文字
　数字
　記号
```

LockoutDuration :
```text
アカウントロック時間 ＝ ロックされたら何分間、解除しないか
00:10:00であれば、ロックされたら「10分後に自動解除」
```

LockoutObservationWindow :
```text
ロック判定時間 = カウント期間
何分間の失敗をカウントするか
```

LockoutThreshold：
```text
0 = アカウントロックしない
何回、ロック判定時間の失敗でロックするかの設定
```

MaxPasswordAge：
```text
最大パスワード有効期間
```

MinPasswordAge：
```text
最小パスワード有効期間
1.00:00:00 ＝ 1日
パスワード変更後、1日経過しないと、再変更できない制御

目的は、パスワード履歴を回避するために、
連続変更することを防ぐ ＝ 再利用したいから、
PasswordHistoryCountを回すような操作の禁止
```

MinPasswordLength：
```text
最小文字数
設定文字数未満は、ポリシー違反にする
```

PasswordHistoryCount：
```text
保持している履歴内で、同一パスワード設定の禁止
```

ReversibleEncryptionEnabled：
```text
可逆暗号化 ＝ 復号できる形で保存
通常「False」
```