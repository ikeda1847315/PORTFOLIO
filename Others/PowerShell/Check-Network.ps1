# CSV読込
$csvPath = Join-Path $PSScriptRoot "serverlist.csv"
$targets = Import-Csv $csvPath

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
    $resultPath = Join-Path $PSScriptRoot "result.csv"
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