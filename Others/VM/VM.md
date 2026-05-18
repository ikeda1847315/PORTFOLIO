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

静的IP設定
　Host-Only側に固定IP
優先DNSサーバーは、自分自身のIPに設定

ADDS役割を追加し、ドメインコントローラーに昇格（ADの中枢）
ユーザー作成

# Windows11
一旦、ローカルアカウントで初期設定
　・固定IP設定
　・DNSをWindowsServerに設定

ドメインまたはワークグループで、WindowsServer側のドメインに設定。
サインアウト後、作成したアカウントとパスワードでサインイン
その後、ターミナルでklistを実行し、Kerberosチケットを確認

# 注意事項
DNSをWindows Serverに向ける ＝ Windows Serverに名前解決を依頼
名前解決できてないと、接続できない。
　⇒ADのDNSサーバー ＝ ドメインコントローラーのDNS

# memo
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


一括削除
Import-Csv users.csv | ForEach-Object {
    Remove-ADUser -Identity $_.UserName -Confirm:$false
}
