静的IP設定
　Host-Only側に固定IP

CSV例：
| カラム名     | 内容                    |
| -------- | --------------------- |
| UserName | ログインID（例：taro.yamada） |
| Password | 初期パスワード               |
| FullName | 表示名（例：山田 太郎）          |


登録例：
| UserName | Password | FullName   |
| -------- | -------- | ---------- |
| test01   | P@ss1234 | Test User1 |
| test02   | P@ss1234 | Test User2 |


一括削除
Import-Csv users.csv | ForEach-Object {
    Remove-ADUser -Identity $_.UserName -Confirm:$false
}