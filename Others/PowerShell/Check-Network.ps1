# CSV読込
# $csvPath = Join-Path $PSScriptRoot "serverlist.csv"
# $targets = Import-Csv $csvPath

try {

    Add-Type -AssemblyName System.Windows.Forms

    $dialog = New-Object System.Windows.Forms.OpenFileDialog
    $dialog.Filter = "CSVファイル (*.csv)|*.csv"

    if ($dialog.ShowDialog() -ne "OK") {
        Write-Host "キャンセルされました"
        exit
    }

    $csvPath = $dialog.FileName

    $targets = Import-Csv `
        -Path $csvPath `
        -ErrorAction Stop

}
catch {

    Write-Host "CSV読込失敗"
    Write-Host $_.Exception.Message
    exit

}

# 疎通確認
$results = foreach ($target in $targets) {

    # Port列が空ならPing
    if ([string]::IsNullOrWhiteSpace($target.Port)) {

        $checkType = "Ping"

        $result = Test-Connection `
            -ComputerName $target.IP `
            -Count 1 `
            -Quiet

    }
    else {

        $checkType = "Port"

        $result = Test-NetConnection `
            -ComputerName $target.IP `
            -Port ([int]$target.Port) `
            -InformationLevel Quiet

    }

    [PSCustomObject]@{
        Server     = $target.ServerName
        IP         = $target.IP
        CheckType  = $checkType
        Port       = $target.Port
        Result     = $result
    }
}

# NG(False)を先頭に表示
$results |
    Sort-Object Result, Server |
    Format-Table -AutoSize

# CSV出力確認
$answer = Read-Host "CSV出力しますか？ (Y/N)"

if ($answer.ToUpper() -eq "Y") {
    # $resultPath = Join-Path $PSScriptRoot "result.csv"
    # 入力CSVと同じフォルダに出力し、結果ファイル名に日時を付ける
    $timestamp = Get-Date -Format "yyyy-MM-dd_HH-mm-ss"
    $resultPath = Join-Path `
        (Split-Path $csvPath) `
        "result_$timestamp.csv"
    try {

    $results |
        Export-Csv `
            -Path $resultPath `
            -NoTypeInformation `
            -Encoding UTF8 `
            -ErrorAction Stop
                #エラーならcatchに飛ばす処理
            
    Write-Host "CSV出力成功: $resultPath"
    }
catch {
    Write-Host "CSV出力失敗"
    Write-Host $_.Exception.Message
}}