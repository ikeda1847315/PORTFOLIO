<?php session_start(); ?>
<?php require '../assets/header.php'?>
<?php require '../db/connect.php'?>
<?php
// 他ページから遷移したときにエラーが出ない対策
    if (isset($_POST['viewName'])) {
        $viewName = $_POST['viewName'];
        } else {
        $viewName = "";
    }
    if (isset($_POST['userId'])) {
        $userId = $_POST['userId'];
        } else {
        $userId = "";
    }
    if (isset($_POST['pass'])) {
        $pass = $_POST['pass'];
        } else {
        $pass = "";
    }

// POSTの値をsessionに一時格納 
    $user_name = $_POST['viewName'] ?? '';
    $user_id = $_POST['userId'] ?? '';
    $user_pw = $_POST['pass'] ?? '';

    $_SESSION['text'] = [ 
        'u_id'=>$user_id, 'u_pw'=>$user_pw, 'u_name'=>$user_name ];

// ログイン済の利用者が来た時
    if (isset($_SESSION['user'])) {
        $id = $_SESSION['user']['id'];
        $u_pw = $_SESSION['text']['u_pw'] ?? '';
        
        echo '<a href="questions.php"> アイデア倉庫</a>';
        echo '<form action="userAdd.php" method="post">';
        echo '<table>';
        echo '<tr><td>パスワード変更</td><td>';
        echo '<tr><td>パスワード</td><td>';
        echo '<input type=text name="pass" maxlength="20" placeholder="半角英数字6～20文字" value="', $u_pw, '">';
        echo '</td></tr>';
        echo '</table>';
        echo '<input type="submit" value="変更">';
        unset($_SESSION['text']);

    if (!preg_match('/^[a-zA-Z0-9]{6,20}$/', $pass) and $_SERVER['REQUEST_METHOD'] === 'POST') {
    $errors="パスワードは半角英数字6～20文字を入力してください。";
    }else{
        $errors="";
    }
    if (preg_match('/^[a-zA-Z0-9]{6,20}$/', $pass)) {
        $sql=$pdo->prepare(
        'SELECT * from user where password=?' );
        $sql->execute([$pass]);
        if (empty($sql->fetch())) {
        // パスワード変更処理
    $sql = $pdo->prepare('UPDATE user SET password = ? WHERE id = ?');
    $sql->execute([$pass, $id]);
            header("Location: questions.php");
        } else {
        // どちらか一方でも既に存在する場合
            $errors= 'そのパスワードはすでに使用されています。別のものを入力してください。';
            }
    }
    echo '<span class="err">', $errors.$err, '</span>';
    echo '</form>';
    echo '<form action="questions.php" method="get">';
    echo '<input type="submit" value="戻る">';
    echo '</form>';
//ログインしてないユーザー
    }else{
//エラーメッセージ
        $errors = [];
        if (!preg_match('/^.{1,10}$/u', $viewName)) {
            $errors[]= "表示名は1～10文字";
        }
        if (!preg_match('/^[a-zA-Z0-9]{8,10}$/', $userId)) {
            $errors[]= "ユーザーIDは半角英数字8～10文字";
            }
        if (!preg_match('/^[a-zA-Z0-9]{6,20}$/', $pass)) {
            $errors[]="パスワードは半角英数字6～20文字";
            }
        if (!empty($errors)) {
            $errors = implode("　", $errors)."　で入力してください";  // 結合
            }else{
            $sql=$pdo->prepare(
            'SELECT * from user where loginId=? or password=?' );
            $sql->execute([$userId,$pass]);
            if (empty($sql->fetchAll())) {
            // 新規登録処理
                $sql=$pdo->prepare('INSERT into user values(null,?,?,?)');
                $sql->execute([$userId,$pass,$viewName]);
                header("Location: ../index.php");
            } else {
            // どちらか一方でも既に存在する場合
                $errors= 'ログインIDまたはパスワードがすでに使用されています。別のものを入力してください。';
                }
        }
// <!-- formAここから -->
        echo '<a href="questions.php"> アイデア倉庫</a>';

// <!-- formBここから -->
        $u_name = $_SESSION['text']['u_name'] ?? '';
        $u_id = $_SESSION['text']['u_id'] ?? '';
        $u_pw = $_SESSION['text']['u_pw'] ?? '';

        echo '<form action="userAdd.php" method="post">';
            echo '<table>';
            echo '<tr><td>利用者登録</td><td>';
            echo '<tr><td>表示名</td><td>';
            echo '<input type=text name="viewName" maxlength="10" placeholder="1-10文字" value="', $u_name, '">';
            echo '</td></tr>';
            echo '<tr><td>ユーザーID</td><td>';
            echo '<input type=text name="userId" maxlength="10" placeholder="半角英数字8-10文字" value="', $u_id, '">';
            echo '</td></tr>';
            echo '<tr><td>パスワード</td><td>';
            echo '<input type=text name="pass" maxlength="20" placeholder="半角英数字6-20文字" value="', $u_pw, '">';
            echo '</table>';
            echo '<input type="submit" value="登録">';
            echo '<span class="err">', $err, '</span>';
            unset($_SESSION['text']);
            
            if ($_SERVER['REQUEST_METHOD'] === 'POST') {
                echo '<span class="err">', $errors, '</span>';
            }else{
                echo '';
            }
        echo '</form>';
// <!-- formCここから -->
        echo '<form action="../index.php" method="get">';
        echo '<input type="submit" value="戻る">';
        echo '</form>';
    }
?>
<?php require '../assets/footer.php'; ?>