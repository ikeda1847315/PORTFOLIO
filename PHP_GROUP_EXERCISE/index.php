<?php session_start(); ?>
<?php require './assets/header.php'?>
<?php require './db/connect.php' ?>


<!-- ここから条件 -->

<!-- ログアウト時のセッションを削除してログイン画面に戻す -->
    <?php 
    if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST["logout"])){
    unset($_SESSION["user"]); //セッションを削除する
    header('Location:./index.php'); //ログイン画面に戻す
    exit(); //リダイレクト後の処理を止める
    }
    ?>

<!-- アイデア倉庫のリンク -->
    
    <a href="pages/questions.php">アイデア倉庫</a><br>

<!--$_GET['loginFlg']で送られてきた値を変数へ代入  -->
    <?php   
    $loginFlg = $_GET['loginFlg'] ?? '';
    $id = $_GET['id'] ?? '';

// loginFlgの値によってエラーメッセージを切り替え表示
    switch ($loginFlg) {
        case 'questionInp':
        $err = '質問投稿にはログインが必要です';
        break;
        case 'answer':
        $err = '回答登録にはログインが必要です';
        break;
        default:
        // 特に何も表示しない場合は空のままでOK
        break;
    }
// 追加：ログイン失敗時のエラーをセッションから取得
    if (isset($_SESSION['login_error'])) {
    $err = $_SESSION['login_error'];
    unset($_SESSION['login_error']); // 一度表示したら削除
    }

// ログイン処理を開始
    unset($_SESSION['user']);
// DB判定ここから
    if (isset($pdo) ?? $pdo instanceof PDO) {
    if (isset($_POST['userId'])) {
    //userテーブルからログインIDとパスワードを検索する準備/実行
    $sql=$pdo->prepare('SELECT * from user WHERE loginId=? and password=?' );
    $sql->execute([$_POST['userId'], $_POST['userPw']]);

//セッションデータの登録
    foreach($sql as $row){
        $_SESSION['user']=[
        'id'=>$row['id'],
        'loginId'=>$row['loginId'],
        'password'=>$row['password'],
        'name'=>$row['name']]; 
        }
  
// ログイン成功時
    if(isset($_SESSION['user'])){
        switch ($_POST['loginFlg']) {
        case 'answer':
            $id = $_POST['id'];
            header("Location:pages/answer.php?id=" . urlencode($id));
            break;
        case 'questionInp':
            header('Location:pages/questionInput.php');
            break;
        default:
            header('Location:pages/questions.php');
            break;
        }
        exit;
//ログイン失敗時に値をもったままリダイレクト
   } else {     
        $_SESSION['login_error'] = 'IDまたはパスワードが違います';
        $err = 'IDまたはパスワードが違います';
        $loginFlg = $_POST['loginFlg'] ?? '';
        $id = $_POST['id'] ?? '';

        $u_id = $_POST['userId'] ?? '';
        $u_pw = $_POST['userPw'] ?? '';
        $_SESSION['text'] = [ 'u_id'=>$u_id, 'u_pw'=>$u_pw ];

        if ($loginFlg === 'answer' && !empty($id)) {
        header("Location:index.php?loginFlg=" . urlencode($loginFlg) . "&id=" . urlencode($id));
        exit;
        } elseif ($loginFlg === 'questionInp') {
        header("Location:index.php?loginFlg=" . urlencode($loginFlg));
        exit;
        } else {
        // loginFlgが不明な場合も安全に戻す
        header("Location:index.php");
        exit;
        }
    }
    }
    }
// DB判定ここまで
    ?>

<!-- HTML部分 -->

    <p class="err"><?= htmlspecialchars($err)?></p><br>
<!-- A:ログインフォーム -->
    <?php
        $u_id = $_SESSION['text']['u_id'] ?? '';
        $u_pw = $_SESSION['text']['u_pw'] ?? '';
        echo '<form action="index.php" method="POST">';
            echo 'ユーザーID<input type="text" name="userId" value="', $u_id, '"><br>';
            echo 'パスワード<input type="password" name="userPw" value="', $u_pw, '">';
            echo '<input  type="hidden" name="loginFlg" value="', htmlspecialchars($loginFlg), '">';
            echo '<input  type="hidden" name="id" value="', htmlspecialchars($id), '">';
            echo '<input type="submit" value="ログイン">';
        echo '</form>';
        unset($_SESSION['text']);
    ?>

<!-- 新規登録リンク -->
    <a href="pages/userAdd.php">新規登録</a>

    <?php require 'assets/footer.php' ?>

