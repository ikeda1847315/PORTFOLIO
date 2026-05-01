<?php session_start(); ?>
<?php require '../assets/header.php'?>
<?php require '../db/connect.php' ?>

<?php

// 未ログインの場合はログイン画面に戻る
    if(!isset($_SESSION['user'])){
    $loginFlg = 'questionInp';
    header("Location:../index.php?loginFlg=" . urlencode($loginFlg));
    exit;
    }
    ?>

<!-- アイデア倉庫のリンク -->
    <a href="questions.php">アイデア倉庫</a>

<!-- ログアウトボタン・indexへ -->   

    <?php 
    if(isset($_SESSION['user'])){
    echo '<div class="header">';
    echo '<form action="../index.php" method="post">';
    echo '<input type="submit" name="logout" class="logout" value="ログアウト">';
    echo '</form>';
    echo '</div>';
    }
    ?>

    <h1>質問を投稿する</h1><br>
    <?php
// trim()=文字列の前後の余計な空白・改行を削除する関数
// ＝postで送られてきた内容から前後の余計なスペース消して$questionへ代入
    if($_SERVER["REQUEST_METHOD"] === "POST"){
    $question=trim($_POST["question"]);
    // $questionが空であるとき
    $_SESSION['q_text'] = $_POST["question"];

    if($question === ""){
        $err= "質問が入力されていません";
    }else{
        // try~catch：エラーが起きても処理を止めずにキャッチして対応する
        // questionテーブルのカラム列に、送られてきたvalueの値を代入後questions.phpへ
        try{
            if (isset ($pdo) && $pdo instanceof PDO) {
            $sql=$pdo->prepare("INSERT into question(userId,question,date, deleteFlg) VALUES (?, ?, NOW(), 0)");
            $sql->execute([
                $_SESSION["user"]["id"],
                $question
            ]); 
                header("Location:questions.php");
                unset($_SESSION['text']);
            exit(); 
            // Exception：何かしらのエラー（例外）が起きた時の箱
            //  $e　上記のエラー内容を受け取るための変数
            }
        }catch(Exception $e){
        
            $err= "登録に失敗しました。";
        }
    }
    }
    ?>
<!-- フォームC：登録 -->
<?php
        $text = $_SESSION['q_text'] ?? '';

        echo '<form action="questionInput.php" method="POST">';
        echo '<textarea name="question" maxlength="256" placeholder="コメントは最大256文字です。" onkeyup="ShowLength(this.value,\'inputlength2\');">' . htmlspecialchars($text, ENT_QUOTES, 'UTF-8') . '</textarea><br>';
        echo '<p id="inputlength2">0/256文字</p>';
        echo '<input type="submit" value="登録">';
        echo '<p class=err>', htmlspecialchars($err), '</p><br>';
        echo '</form>';
    ?>

<!-- フォームⅮ：戻る -->
    <form action="questions.php" method="GET">
    <input type="submit" value="戻る">
    </form>

<?php require '../assets/footer.php'; ?>