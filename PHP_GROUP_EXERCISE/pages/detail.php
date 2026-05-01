<?php session_start();
require '../assets/header.php';
require '../db/connect.php';
?>


<!-- questionsから外部アクセス：id未所持はquestionsに戻す -->
    <?php
    if(!isset($_GET['id'])){
    header('Location: questions.php');
    exit();
    }
    ?>


<!-- アイデア倉庫のリンク -->
    <div class="header">
    <a href="questions.php" name="left-area">アイデア倉庫</a>

<!-- ログアウトボタン -->   
    <?php 
    echo '<div class="right-area">';
    echo '<form action="../index.php" method="post">';
    if(isset($_SESSION['user'])){
    echo '<input type="submit" value="ログアウト">';
    } else {
    echo '<input type="submit" value="ログイン">';
    }
    echo '</form>';
    echo '</div>';
    echo '</div>';

    if (isset($err)) {
    echo '<span class="err">', $err, '</span>';
    }

    //ログアウトボタンが押されたとき
    if (isset($_POST['logout'])) {
    // セッションを削除
    unset($_SESSION['user']); 
    
    // index.php に遷移
    header('Location: ../index.php');
    exit();
    }
    ?>

<!-- 質問表示 -->
    <?php
    // 質問一覧で選択された質問idをもらう
    $questionId = $_GET['id'];

    // questionテーブルとuserテーブルを合致して
    // q.id /userId /question /date, u.name (deleteflg0のもの)を取得
    // DB判定ここから
    if (isset($pdo) ?? $pdo instanceof PDO) {
    $stmt= $pdo->prepare('SELECT question.id, question.userId, question.question, question.date, question.deleteflg,
    user.name FROM question INNER JOIN user ON question.userId = user.id WHERE question.id=? AND question.deleteflg=0;');
    $stmt->execute([$questionId]);
    $question = $stmt->fetch(PDO::FETCH_ASSOC);
    
    // ☆修正ここ↓　
    // 送られた質問IDの有無、質問のdeleteflgを確認分岐
    if (isset($question['id']) && $question['deleteflg'] == 0) {
        
    // 取得した質問を表示
    echo '<p>質問</p>';
    echo '<div class="box">';
    echo '<p>' ,'<strong>', htmlspecialchars($question['name']) ,'</strong>', '</p>';
    echo '<p>' , htmlspecialchars($question['question']) , '</p>';
    echo '<p>' , date('Y/m/d H:i', strtotime($question['date'])) , '</p>';

    $questionId = $question['id'];
        
    echo '<form action="answer.php" method="get">';
    echo '<input type="hidden" name="id" value="',($question['id']) , '">';
    echo '<input type="submit" value="回答">';
    echo '</form>';
    echo '</div>';
        
    // 削除処理
    if(isset($_SESSION['user'])){
    
    // 削除ボタンが押されたら実行する
    if (isset($_POST['delete'])){
    // 削除対象の回答ID
    $deleteId = $_POST['delete_id'];
                
    // 自分の回答かチェックする
    $stmt = $pdo->prepare('SELECT userId FROM answer WHERE id = ?');
    $stmt->execute([$deleteId]);
    $answer = $stmt->fetch(PDO::FETCH_ASSOC);
                
    // $answerが存在していて且つ回答の投稿者IDがログイン中のユーザーか
    if($answer && $answer['userId'] == $_SESSION['user']['id']){
    // 上記がtrueであれば削除する（deleteflg=1）
    $stmt = $pdo->prepare('UPDATE answer SET deleteflg = 1 WHERE id = ?');
    $stmt->execute([$deleteId]);
    }
    //  削除後に同じページにリダイレクト（POSTデータをクリア）
    header('Location: detail.php?id='. $_GET['id']);
    exit();            
    }
    }
    ?>


<!-- 回答表示 -->
 
    <?php
    // questionテーブルから該当の質問idは取得済み 
    // userテーブルとanswerテーブルを結合して
    // a.id/ questionId/ answer/ date/ , u.name (deleteflg=0)を取得
    $stmt= $pdo->prepare('SELECT answer.userId, answer.id, answer.questionId, answer.answer, answer.date, user.name FROM answer INNER JOIN user ON answer.userId = user.id WHERE questionId = ? AND answer.deleteflg = 0 ORDER BY answer.date DESC');
    $stmt->execute([$questionId]);
    $answers = $stmt->fetchall(PDO::FETCH_ASSOC);

    // 質問に紐づく回答がすべて取得出来たら一覧として表示
    echo '<div class="container">';
    echo '<p>回答</p>';
    foreach ($answers as $answer) {
    echo '<div class="box answer">';
    echo '<p>', '<strong>', htmlspecialchars($answer['name']),'</strong>' ,'</p>';
    echo '<p>
    ', htmlspecialchars($answer['answer']), '</p>';
    echo '<p>', date('Y/m/d H:i',strtotime($answer['date'])), '</p>';

    // ----------------------------------------------------
    // 返信処理
    // ----------------------------------------------------
    
    // 返信削除処理
    if (isset($_POST['delete_reply']) && isset($_SESSION['user'])) {
    $deleteReplyId = intval($_POST['delete_reply_id']);

    // 自分の返信か確認
    $stmt = $pdo->prepare('SELECT userId FROM reply WHERE id = ?');
    $stmt->execute([$deleteReplyId]);
    $reply = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($reply && $reply['userId'] == $_SESSION['user']['id']) {
    // deleteflg = 1にする
    $stmt = $pdo->prepare('UPDATE reply SET deleteflg = 1 WHERE id = ?');
    $stmt->execute([$deleteReplyId]);
    }

    // 同じページにリダイレクト
    $id = intval($_GET['id'] ?? 0);
    header('Location: detail.php?id=' . $id);
    exit;
    }


    // 返信ボタンの処理内容
    if (isset($_POST['reply_submit']) && isset($_SESSION['user'])) {
    $replytext   = trim($_POST['reply_text'] ?? '');
    $replyanswer = intval($_POST['reply_answer_id'] ?? 0);

    if (!empty($replytext) && $replyanswer > 0) {
    $stmt = $pdo->prepare('INSERT INTO reply (answerId, userId, reply) VALUES (?, ?, ?)');
    $stmt->execute([$replyanswer, $_SESSION['user']['id'], $replytext]);
    }

    $id = intval($_GET['id'] ?? 0);
    header('Location: detail.php?id=' . $id);
    exit;
    }

    // 返信取得
    $stmtReply = $pdo->prepare('SELECT reply.id, reply.reply, reply.date, reply.userId, user.name
    FROM reply INNER JOIN user ON reply.userId = user.id              WHERE reply.answerId = ? AND reply.deleteflg = 0 ORDER BY reply.date ASC');
    
    $stmtReply->execute([$answer['id']]);
    $replies = $stmtReply->fetchAll(PDO::FETCH_ASSOC);

    // 返信一覧表示（返信がある場合のみ）
    echo '<div class="reply">';
    if (!empty($replies)) {
    echo '<p><strong>返信一覧:</strong></p>';
    foreach ($replies as $reply) {
    echo '<p>', htmlspecialchars($reply['name']), '</p>';
    echo '<p>', htmlspecialchars($reply['reply']), '</p>';
    echo '<p>', date('Y/m/d H:i', strtotime($reply['date'])), '</p>';

    // ------------------------------------------------------------
    // 返信処理ここまで
    // ------------------------------------------------------------
    
    // ログインしていて自分の返信であれば削除ボタンを表示
    if (isset($_SESSION['user']) && $reply['userId'] == $_SESSION['user']['id']) {
    echo '<form method="post">';
    echo '<input type="hidden" name="delete_reply_id" value="', $reply['id'], '">';
    echo '<input type="submit" name="delete_reply" value="削除" class="reply-delete">';
    echo '</form>';}
    }}
    echo '</div>';

    // ログインしていて自分の回答であれば削除ボタンを表示
    if (isset($_SESSION['user']) && $answer['userId'] == ($_SESSION['user']['id'])) {
    echo '<form method="post">';
    echo '<input type="hidden" name="delete_id" value="',($answer['id']), '">';
    echo '<input type="submit" name="delete" value="削除">';
    echo '</form>';   
    }


    // ログインしていて質問者のみ返信フォームを表示
    if (isset($_SESSION['user']) && $_SESSION['user']['id'] == $question['userId']){
    echo '<form method="post">';
    echo '<input type="hidden" name="reply_answer_id" value="',$answer['id'],'">';
    echo '<textarea name="reply_text" maxlength="256" placeholder="返信内容を入力" class="single" onkeyup="ShowLength(this.value,\'inputlength3\');"></textarea >';
    echo '<p id="inputlength3">0/256文字</p>';
    echo '<p><input type="submit" name="reply_submit" value="返信"></p>';
    echo '</form>';
    }

    echo '</div>';
    }

    echo '</div>';
    } else {
    // ☆修正ここ↓　falseの時表示
    echo '質問を選択してください';
    }
    }
    ?>

<!-- 戻るボタン -->
    <form action="questions.php" method="get">
    <input type="submit" value="戻る">
    </form>

    <?php require "../assets/footer.php"?>
