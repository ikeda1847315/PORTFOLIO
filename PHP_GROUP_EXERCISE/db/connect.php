<!-- なぜ変数にいれるのか -->
 <!-- 可読性の向上：パスワードやユーザーネームを直接書くと可読性が落ちる-->
  <!-- 再利用性：別の場所で使いまわすときに便利 -->
   <!-- 管理のしやすさ：データベース名やホストを変更する場合、変数だけ変えればいい -->
    <!--　→ほかの接続設定と統一して管理できる -->
  

<?php
$dsn = 'mysql:host=localhost;dbname=ideastock;charset=utf8';
$user = 'root';
$password = 'pass';
$err = '';
try {
// 例外が発生する可能性がある処理
// BDサーバーの停止、ホスト名やデータベース名の誤り
// ユーザー名・パスワードの誤り

// ☆☆失敗時に例外が投げられないと致命的なエラーで処理を停止してしまう
    $pdo = new PDO($dsn, $user, $password);
} catch (PDOException $e) {
//catchブロックは、tryブロック内で発生した例外を捕まえるためのもの
//  PDOException $e　は「PDO接続に関する例外」を表すオブジェクト

    $err = 'DB接続エラー: ' . $e->getMessage();
    $pdo = null;
    // $e->getMessageの中身はエラーの詳細メッセージを取得するメソッド
}


?>








