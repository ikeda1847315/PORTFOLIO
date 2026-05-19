Oracle Virtual BoxにWindows Server及び、Windows11をインストール<br>
また、Active Directory連携を実施。

# Windows Server
初期状態保存のため、VirtualBox のスナップショットを作成後、<br>
仮想マシンに、環境を整える為、VirtualBox Guest Additions をインストールした。<br>
　※画面サイズ自動調整やクリップボード共有の有効化<br>

仮想社内LANのネットワーク構成を設定し、ipconfigやpingコマンドで、ネットワーク確認を実施。<br>
　※NAT（VMをインターネットに接続する仕組み）及び、Host-Only Adapter（仮想社内LAN）を構成<br>
Windows Server用に名称変更と、Host-Only Adapterのネットワーク設定を実施。<br>

固定IPを設定後、共有フォルダ及び、新規アカウントを作成し、VM同士でのファイル共有や、<br>
VMと実機でのファイル共有を実施し、アカウントの権限設定変更を学んだ。<br>
　※SMB共有設定（Server Message Block［ファイル共有プロトコル］）<br>
　　　⇒ネットワーク経由で、共有フォルダへアクセスできるかを制御する権限<br>

NTFS権限（New Technology File System［フォルダやファイルのアクセス制御］）<br>
　⇒フォルダやファイル自体に対するアクセス権限<br>
実機⇔VMは、VirtualBox共有フォルダ機能を利用<br>

静的IP設定<br>
　Host-Only側に固定IP<br>
優先DNSサーバーは、自分自身のIPに設定<br>

ADDS役割を追加し、ドメインコントローラーに昇格（ADの中枢）<br>
ユーザー作成等が可能になる<br>

# Windows11
一旦、ローカルアカウントで初期設定<br>
　・固定IP設定<br>
　・DNSをWindowsServerに設定<br>

ドメインまたはワークグループで、WindowsServer側のドメインに設定。<br>
サインアウト後、作成したアカウントとパスワードでサインイン<br>
その後、ターミナルでklistを実行し、Kerberosチケットを確認<br>

# 注意事項
DNSをWindows Serverに向ける ＝ Windows Serverに名前解決を依頼<br>
名前解決できてないと、接続できない。<br>
　⇒ADのDNSサーバー ＝ ドメインコントローラーのDNS<br>

# memo
NAT、Host-Only、ブリッジは基本、IPで見分ける<br>
それ以外の特徴：<br>
　NAT　デフォルトゲートウェイあり<br>
　Host-Only　デフォルトゲートウェイなし<br>
　ブリッジ　実機と同じLAN<br>

DHCP設定した後、UbuntuServerの場合、ip aでdynamicが出ているか。<br>
出てても、意図しないIPならcat /run/systemd/netif/leases/*で、<br>
どのアドレスか確認する<br>
    ADDRESS= XXX.XXX…　⇒自分自身に割り当てられたIPアドレス<br>
    SERVER_ADDRESS= XXX.XXX…　⇒DHCPサーバーのアドレス（どこからIP取得したか）<br>
    DOMAINNAME=　ドメイン名<br>
    DNS= XXX.XXX…　⇒どのDNSと接続し、名前解決しているか<br>

Windows11の場合、ipconfig /all<br>
Ethernet adapter Ethernet:<br>
   IPv4 Address. . : XXX.XXX…　⇒自分に割り当てられたIP<br>
   DHCP Enabled. . : Yes　　⇒DHCP取得<br>
   DHCP Server . . : XXX.XXX…　⇒どのDHCPから取得か<br>
   　※AD参加<br>
   Connection-specific DNS Suffix  . : ドメイン名<br>
   DNS Servers . . : XXX.XXX…　⇒どのDNSと接続し、名前解決しているか<br>

|略称 |意味 |
| -------- | --------------------- |
|AD |Active Directory |
|AD DS |Active Directory Domain Services |
|DC |Domain Controller |
|GPO |Group Policy Object|
|OU	|Organizational Unit |
|DHCP |Dynamic Host Configuration Protocol（IP配布）|

CSV例：
|カラム名|内容 ・用途|
| -------- | --------------------- |
| UserName | ログインID（例：taro.yamada） |
| Password | 初期パスワード  |
| FullName | 表示名（例：山田 太郎） |
|　今後　|  |
|OU　|配置先OU　|
|Department　|部署  |
|Title　|役職  |
|Mail　|メールアドレス  |

登録例：
| UserName | Password | FullName   |
| -------- | -------- | ---------- |
| test01   | P@ss1234 | Test User1 |
| test02   | P@ss1234 | Test User2 |

注意：<br>
Import-Csv は 1行目を「カラム名（ヘッダー）」として扱う。<br>

CSVファイル確認
```powershell
Import-Csv "フルパス"
```

一括登録
CSVファイル確認
```powershell
Import-Csv "フルパス" | ForEach-Object {
    New-ADUser `
        -Name $_.FullName `
        -SamAccountName $_.UserName `
        -UserPrincipalName "$($_.UserName)@ドメイン名" `
        -AccountPassword (ConvertTo-SecureString $_.Password -AsPlainText -Force) `
        -Enabled $true
}
```

対象ユーザー確認
```powershell
Import-Csv "フルパス" | ForEach-Object {
    Get-ADUser $_.UserName
}
```

一括無効化
```powershell
Import-Csv "フルパス" | ForEach-Object {
    Disable-ADAccount -Identity $_.UserName
}
```

無効化確認
```powershell
Import-Csv "フルパス" | ForEach-Object {
    Get-ADUser $_.UserName -Properties Enabled |
    Select Name, SamAccountName, Enabled
}
```
- EnabledがFalse
他
- 一覧でアカウントアイコンに⇓がつく
- プロパティで「アカウントが無効」にチェック

一括削除
```powershell
Import-Csv "フルパス" | ForEach-Object {
    Remove-ADUser -Identity $_.UserName -Confirm:$false
}
```
