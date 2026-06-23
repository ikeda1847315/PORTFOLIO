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
　⇒ユーザーが利用できる追加UPNサフィックス一覧<br>
　　{}の場合は、フォレストに追加のUPNサフィックスが定義されていない<br>
　　＝利用可能なUPNサフィックスがドメイン名になる<br>
　　　※FSMO（Flexible Single Master Operations）<br>
  　　　　特定の重要な処理だけを、1台のDCに担当させるための役割（ロール）<br>
　　　　　　・新しいドメインを追加する<br>
　　　　　　・RID（セキュリティ識別子）を発行する<br>
　　　　　　・時刻同期の基準になる<br>

  　　　　FSMOの5つの役割：<br>
　　　　　　1. Schema Master（スキーママスター）<br>
　　　　　　2. Domain Naming Master（ドメイン名前付けマスター）<br>
　　　　　　3. RID Master（RIDマスター）<br>
　　　　　　4. PDC Emulator（PDCエミュレーター）<br>
　　　　　　5. Infrastructure Master（インフラストラクチャマスター）<br>

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

#### 同期用ユーザーデータ作成
　⇒[Test.csv](https://github.com/ikeda1847315/PORTFOLIO/blob/main/Others/ActiveDirectory%20etc/Test.csv)
 
```PowerShell
Import-Module ActiveDirectory

$Users = Import-Csv C:\AD\Test.csv


foreach ($User in $Users) {

    $SecurePassword = ConvertTo-SecureString `
        $User.Password `
        -AsPlainText `
        -Force


    New-ADUser `
        -SamAccountName $User.SamAccountName `
        -UserPrincipalName $User.UserPrincipalName `
        -Name $User.Name `
        -DisplayName $User.DisplayName `
        -GivenName $User.GivenName `
        -Surname $User.Surname `
        -Initials $User.Initials `
        -Description $User.Description `
        -Department $User.Department `
        -Title $User.Title `
        -Company $User.Company `
        -Office $User.Office `
        -OfficePhone $User.OfficePhone `
        -MobilePhone $User.MobilePhone `
        -EmailAddress $User.EmailAddress `
        -StreetAddress $User.StreetAddress `
        -City $User.City `
        -State $User.State `
        -PostalCode $User.PostalCode `
        -Country $User.Country `
        -EmployeeID $User.EmployeeID `
        -AccountPassword $SecurePassword `
        -Path $User.OU `
        -Enabled $true
        -ChangePasswordAtLogon $true
}
```

| CSV項目           | New-ADUserパラメータ | AD属性                     | 意味                |
| ----------------- | ------------------ | -------------------------- | ----------------- |
| SamAccountName    | -SamAccountName    | sAMAccountName             | Windowsログオン名（旧形式） |
| UserPrincipalName | -UserPrincipalName | userPrincipalName          | ユーザーログオン名（UPN形式）  |
| Name              | -Name              | Name                       | ADオブジェクト名（CN）＝ AD内部名が変わる 　|
| DisplayName       | -DisplayName       | displayName                | 表示名（Entra IDの表示名） |
| GivenName         | -GivenName         | givenName                  | 名（ファーストネーム）  |
| Surname           | -Surname           | sn                         | 姓 （ラストネーム）    |
| Department        | -Department        | department                 | 部署                |
| Title             | -Title             | title                      | 役職                |
| Company           | -Company           | company                    | 会社名               |
| Office            | -Office            | physicalDeliveryOfficeName | 勤務場所              |
| OfficePhone       | -OfficePhone       | telephoneNumber            | 会社の電話番号              |
| MobilePhone       | -MobilePhone       | mobile                     | 携帯電話番号            |
| EmailAddress      | -EmailAddress      | mail                       | メールアドレス           |
| StreetAddress     | -StreetAddress     | streetAddress              | 住所                |
| City              | -City              | l                          | 市区町村              |
| State             | -State             | st                         | 都道府県              |
| PostalCode        | -PostalCode        | postalCode                 | 郵便番号              |
| EmployeeID        | -EmployeeID        | employeeID                 | 社員番号              |
| OU       | -Path            | distinguishedName | ユーザーを作成する配置場所（OU）       |
| Password | -AccountPassword | -                 | 初期パスワード（AD属性として直接、保持しない） |

##### $SecurePasswordについて<br>
New-ADUserの「-AccountPassword」は、SecureString型を要求する<br>
その為、ConvertTo-SecureStringのコマンドを用い、通常の文字列（String）を<br>
暗号化されたパスワード形式（SecureString）へ変換する<br>

通常、ConvertTo-SecureStringは、暗号化された文字列を戻す用途がある<br>
その為、PowerShellは「これは暗号化されたデータ」という前提で処理する<br>
　例：ConvertTo-SecureString "暗号化済み文字列"<br>

もし、平文（Plain Text）で処理する場合は、-AsPlainTextをつけ、<br>
入力値は暗号化済みではなく、普通の文字列という指定を入れる<br>
また、-AsPlainTextを利用すると、PowerShellは警告する為、<br>
強制的に許可する場合に、-Forceを付ける。<br>
　⇒次回ログイン時にパスワード変更を強要するのが望ましい<br>
　　　-ChangePasswordAtLogon $true<br>

パスワード生成スクリプト化を用いる場合：<br>
```PowerShell
function New-RandomPassword {
      # パスワード生成に利用する文字一覧
    $chars = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ0123456789!@#$"
         #　-join：複数の文字を「1つの文字列」として結合する
         #  1..12 | ForEach-Object：12回繰り返す
         #  Get-Random -Maximum $chars.Length：$chars[○○]生成の際、
         #　○○をランダムな数字（$chars.Length範囲内）で取得する
    -join (1..12 | ForEach-Object {
        $chars[(Get-Random -Maximum $chars.Length)]
    })
}

$PasswordList = @()

foreach ($User in $Users) {

    # ランダムパスワード生成
    $Password = New-RandomPassword

    # SecureString変換
    $SecurePassword = ConvertTo-SecureString `
        $Password `
        -AsPlainText `
        -Force

    # ADユーザー作成
    New-ADUser `
    　　・・・
      -ChangePasswordAtLogon $true 

    # 初期パスワード記録
    $PasswordList += [PSCustomObject]@{
        SamAccountName  = $User.SamAccountName
        DisplayName     = $User.DisplayName
        InitialPassword = $Password
    }
}

# 初期パスワードCSV出力
$PasswordList | Export-Csv `
    "C:\AD\InitialPassword.csv" `
    -NoTypeInformation `
    -Encoding UTF8
```
⇒「偶然同じ文字が続く」や「記号が含まれていない」等<br>
　の可能性を排除できないので注意<br>

補足：<br>
必須文字がある場合、固定値で最初に組み込み、<br>
残りをランダムで処理とする方法がある<br>
連続禁止のメソッドにするのであれば、以下の方法になる<br>
```PowerShell
function New-RandomPassword {

    $chars = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ0123456789!@#$"

    $Password = ""

    while ($Password.Length -lt 12) {

        $NewChar = $chars[(Get-Random -Maximum $chars.Length)]

        # 直前の文字と同じならやり直し
        if ($Password.Length -eq 0 -or 
            $Password[$Password.Length - 1] -ne $NewChar) {
            $Password += $NewChar
        }
    }

    return $Password
}
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
⇒LockedOut：Trueはロック中<br>
　他、Enabled：True＝アカウント有効<br>
　PasswordExpired=False：パスワード期限切れなし<br>

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
