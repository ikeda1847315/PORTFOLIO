### PowerShellのコマンドは、以下のルールで命名
```text
動詞-対象
```
Tab補完がある。<br><br>
例：
```powershell
Get-Service Spooler
```
　⇒プリンタサービスが動いているか確認
```powershell
Get-Process chrome
```
　⇒GoogleChromeのプロセス確認（応答確認）
```powershell
Get-ChildItem
```
　⇒Linuxのlsや、コマンドプロンプトのdirと同義<br>
　　現在地確認（Get-Location）を活用し、<br>
　　任意のフォルダへ移動（Set-Location）する。<br>

分からないものは、例として、以下の順で確認できる。
```powershell
Get-Command *service*
# コマンドに「service」が含まれるものの出力
Get-Help Get-Service
# Get-Serviceのヘルプ概要を表示
Get-Help Get-Service -Examples
# Get-Serviceの使用例を表示
Get-Help Get-Service -Online
# Get-Serviceのオンラインヘルプ（Microsoft Learn）をブラウザで起動
```
| オプション       | 内容            |
| ----------- | ------------- |
| なし          | 概要            |
| `-Examples` | 使用例のみ         |
| `-Detailed` | 詳細説明          |
| `-Full`     | 全部            |
| `-Online`   | ブラウザで公式ページを開く |


### Get-ChildItemのMode列
ファイルやフォルダの属性（Attributes）を短縮表示したもの
| 文字 | 意味                       |
| -- | ------------------------ |
| d  | Directory                |
| a  | Archive                  |
| r  | ReadOnly                 |
| h  | Hidden                   |
| s  | System                   |
| l  | ReparsePoint（シンボリックリンク等） |

通常ファイル：-a----<br>
フォルダ：d----- 等<br>
属性表示：
```powershell
Get-Item test.txt | Select-Object Attributes
```
権限確認：
```powershell
Get-Acl test.txt
```
プロパティ表示：
```powershell
Get-Item test.txt | Format-List *
```

## コマンド表

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
| Get-WinEvent     | イベントログ確認      |
| New-LocalUser    | ローカルユーザー作成    |
| Get-LocalUser    | ローカルユーザー確認    |
| Test-Connection  | ネットワーク疎通確認    |
| Get-NetIPAddress | IPアドレス確認      |
| Import-Csv       | CSV読込         |
| Export-Csv       | CSV出力         |
| Get-Help　　　　　| 使用方法を出力        |
| Get-Command      | コマンド一覧表示   |

## Ｍｅｍｏ
Test-Connection google.com<br>
　⇒ping google.comと同様に利用できる<br>
　　　※厳密には「プロパティを持ったオブジェクト」を、返すので演算制御できる。<br>
　　IPv6が利用できる場合、IPv6を優先表示

⇒名前解決結果で、通信状況を確認する。<br>
Resolve-DnsName google.com<br>
　⇒IPv4(Aレコード)と、IPv6(AAAAレコード)確認<br>

補足：<br>
Test-Connection google.com -Count 1<br>
　⇒一回のみ疎通確認<br>

パイプライン「 | 」（Stream処理）を活用する<br>
　　⇒左の結果を右へ渡す仕組み<br>
　　　かつ「-and」や、または「-or」とは、性質が違う点に注意
```powershell
Get-NetIPAddress | 
Where-Object {$_.AddressFamily -eq "IPv4"} | 
Select-Object InterfaceAlias,IPAddress
```
　⇒Get-NetIPAddressの結果を、AddressFamilyでIPv4の結果に絞り、<br>
　　出力結果をInterfaceAliasと、IPAddressだけにする<br>
　　　※$_.AddressFamily -eq "IPv4"<br>
　　　　　　⇒AddressFamilyがIPv4なら通す（$_はこのデータ、-eqは等しい）<br>

例（サービスが停止中または一時停止中）：
```powershell
Get-Service |
Where-Object {
    $_.Status -eq "Stopped" -or
    $_.Status -eq "Paused"
}
```

「*」は、ワイルドカードとして利用できるパラメーターがある。<br>
-like で、ワイルドカード付き比較も可能<br>
　⇒例：Get-Service | Where-Object {$_.Name -like "*SQL*"}<br>
「?」は、任意の一文字指定、文字指定の分、?をつける（しかし、*の利用のが多い）<br>

| PowerShell演算子    | 意味        |
| ------ | --------- |
| -eq    | 等しい （=）       |
| -ne    | 等しくない （≠）    |
| -gt    | より大きい （>）     |
| -ge    | 以上 （>=）       |
| -lt    | より小さい （<）    |
| -le    | 以下 （<=）        |
| -like  | ワイルドカード比較 |
| -match | 正規表現一致    |
| -and   | 論理AND     |
| -or    | 論理OR      |
| -not   | 否定        |
| -band  | ビットAND    |

Windowsの属性は内部的に数字
| 属性        |  値 | 2進数 | 
| --------- | --- | --- | 
| ReadOnly  |  1 | 000001 | 
| Hidden    |  2 | 000010 | 
| System    |  4 | 000100 | 
| Directory | 16 | 010000 | 
| その他 |||
| Device       |     8 ||
| Archive      |    32 ||
| ReparsePoint |  1024 ||
| Compressed   |  2048 ||
| Encrypted    | 16384 ||

Hidden + Archiveなら「34」だが、<br>
10進数ではなく2進数で考える<br>
Archive　100000<br>
Hidden　 000010<br>
　　　 　100010<br>
```powershell
(Get-Item file.txt).Attributes -band [IO.FileAttributes]::Hidden
```
　[IO.FileAttributes]::Hiddenは「2（000010）」なので、 <br>
(Get-Item file.txt).Attributesの二進数に「000010」が、<br>
含まれるかを判定する式になる。<br>
[int](Get-Item file.txt).Attributesで、合計値を確認できる。<br>

ユーザー作成について
```powershell
New-LocalUser `
    -Name "testuser" `
    -Password $password `
    -FullName "テストユーザー" `
    -Description "練習用アカウント"
# 管理者権限付与
Add-LocalGroupMember `
    -Group "Administrators" `
    -Member "testuser"
```
| パラメータ                   | 説明              |
| --------------------------- | ---------------- |
| `-Name`                     | ユーザー名            |
| `-Password`                 | パスワード            |
| `-FullName`                 | フルネーム            |
| `-Description`              | 説明               |
| `-AccountNeverExpires`      | アカウント期限なし        |
| `-PasswordNeverExpires`     | パスワード期限なし        |
| `-UserMayNotChangePassword` | ユーザーによるパスワード変更禁止 |
|  その他       |  |
| `Set-LocalUser`        | ユーザー変更   |
| `Remove-LocalUser`     | ユーザー削除   |
| `Get-LocalGroup`       | グループ一覧   |
| `Add-LocalGroupMember` | グループへ追加  |

ローカルユーザー出力例：
```powershell
Get-LocalUser |
Export-Csv users.csv -NoTypeInformation -Encoding UTF8
```
日本語環境での文字化け対策含む

そのメンバーの型情報・定義（メソッド）の検索
```powershell
Get-Member
```
例：<br>
Get-Service | Get-Memberの抜粋
| Name        | MemberType | Definition |
|-------------|------------|------------|
| Stop        | Method     | void Stop(), void Stop(bool stopDependentServices) |
| ServiceName | Property   | string ServiceName {get;set;} |

　⇒void Stop()は、Methodなので、引数なし、戻り値なしのStop()で実行できる<br>
　　※メソッドでの絞り込みは「-MemberType Method」<br>
　string ServiceName {get;set;} は、Propertyなので、文字列(string)を、取得(get)できる<br>　
| MemberType     | 意味           |
| -------------- | ------------ |
| Property       | データ          |
| Method         | 実行できる処理      |
| AliasProperty  | 別名           |
| ScriptProperty | スクリプトで計算された値 |
| NoteProperty   | 追加されたプロパティ   |

順列処理：<br>
　例：
```powershell
"A","B","C" |
ForEach-Object {
    $_.ToLower()
}
```

$_(ループ変数)で、順番に処理していく<br>
Aを.ToLower()で小文字に変換し、abcと出力していく<br>
「| Select-Object -First 2」等を足す事で、<br>
出力結果を頭2つまでに絞る事もできる。<br>