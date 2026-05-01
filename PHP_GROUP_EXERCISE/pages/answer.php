<?php session_start();?>
<?php require '../assets/header.php'?>
<?php require '../db/connect.php'?>
<div class="header">
<a href="questions.php">アイデア倉庫</a>

<?php
// ログアウトボタン
	echo '<div class="right-area">';
	echo '<class="right-area" form action="../index.php" method="post">';
	echo '<input type="submit" value="ログアウト">';
	echo '</form>';
	echo '</div>';
	echo '</div>';

// DBの結合処理
	// 接続できないときのエラー
	if (isset($err)) {
		echo '<p class="err">', $err, '</p>';
	}

// データベースのテーブル結合及び、questionテーブルのidでの抽出
	// whileから、結合したDBデータから質問表示に必要な項目の出力
	if (isset($pdo) ?? $pdo instanceof PDO) {
	$getQuestion = $pdo->prepare("SELECT user.name ,question.question ,question.date FROM question INNER JOIN user ON question.userId = user.id WHERE question.id = :id;");
	$getQuestion->bindParam(':id', $_GET['id'], PDO::PARAM_INT);
	$getQuestion->execute();
	
	echo '<p>質問</p>';
	while ($row = $getQuestion->fetch(PDO::FETCH_ASSOC)) {
		echo '<div class="box">';
		echo htmlspecialchars($row['name'], ENT_QUOTES, 'UTF-8');
		echo '<br>';
		echo htmlspecialchars($row['question'], ENT_QUOTES, 'UTF-8');
		echo '<br>';
		$Postingtime = new DateTime($row['date']);
		echo $Postingtime->format('Y/m/d H:i');
		echo '<br>';
		echo '<br>';
		echo '</div>';
	}
	

// 回答フォーム送信
	$text = $_SESSION['a_text'] ?? '';

	echo '<form method="POST" action="answer.php">';
	echo '<input type="hidden" name="questionId" value="', htmlspecialchars($_GET['id']), '">';
	echo '<label>回答</label><br>';
	echo '<textarea name="comment" maxlength="256" placeholder="コメントは最大256文字です。" required onkeyup="ShowLength(this.value,\'inputlength1\');">' . htmlspecialchars($text, ENT_QUOTES, 'UTF-8') . '</textarea><br><br>';
	echo '<p id="inputlength1">0/256文字</p>';
	echo '<input type="submit" value="登録">';
	echo '</form>';

// 回答の登録処理
	// タイムゾーン及び、削除フラグ設定
	date_default_timezone_set('Asia/Tokyo');
	$deleteFlg = 0;

	// フォームがPOSTで送信された時、questionIdを取得
	if ($_SERVER["REQUEST_METHOD"] === "POST") {
		$comment = $_POST["comment"] ?? '';
		$sanitizedComment = $comment;
		$questionId = (int)$_POST["questionId"];
		$userId = $_SESSION['user']['id'];
		$_SESSION['a_text'] = $_POST["comment"];

		// コメント、質問ID、ユーザーIDのすべてが存在しているかを確認し、
		// どれかが欠けていたら処理を中断し、無効な投稿を防ぐ。
		if ($comment && $questionId && $userId) {
			try {
				// データベースへの登録処理
				$addAnswer = $pdo->prepare("INSERT INTO answer (questionId, userId, answer, date, deleteFlg) 
                VALUES (:questionId, :userId, :answer, NOW(), :deleteFlg)");
				$addAnswer->bindParam(':questionId', $questionId, PDO::PARAM_INT);
				$addAnswer->bindParam(':userId', $userId, PDO::PARAM_INT);
				$addAnswer->bindParam(':answer', $sanitizedComment, PDO::PARAM_STR);
				$addAnswer->bindParam(':deleteFlg', $deleteFlg, PDO::PARAM_INT);
				$addAnswer->execute();
				
				// 登録後に detail.php に遷移
				header("Location: detail.php?id=" . urlencode($questionId));
				unset($_SESSION['a_text']);
				exit ();
			} catch (PDOException) {
				// エラー処理
				$err = "入力した文字数を見直してください（最大256文字）";
			}
		} else {
			$err = "回答する質問が、有効かを確認してください";
		}
	}
	if (isset($err)) {
	echo '<p class="err">', $err, '</p>';
	}
	}

// 戻るボタン
	echo '<form action="./detail.php" method="post">';
	echo '<input type="submit" value="戻る">';
	echo '</form>';

// 以下の場合に、index.phpへ戻す処理
	// ・セッションがない
	// ・リクエストパラメータがない
	// ・リクエストパラメータが空白
	// ・存在しないリクエストパラメータを入力
	if(!isset($_SESSION['user'])){
		$loginFlg = 'answer';
		$receivedid = $_GET['id'];
		header("Location:../index.php?loginFlg=" . urlencode($loginFlg) . '&id=' . urlencode($receivedid));
		exit;
		}elseif(!isset($_GET['id']) || $_GET['id'] === '') {
		header("Location:questions.php");
		exit;
	}
	if (isset($pdo) ?? $pdo instanceof PDO) {
	$checkId = $pdo->prepare("SELECT COUNT(*) FROM question WHERE id = :id");
	$checkId->bindParam(':id', $_GET['id'], PDO::PARAM_INT);
	$checkId->execute();
	$idExists = $checkId->fetchColumn();

	// 存在しないIDならリダイレクト
	if ($idExists == 0) {
		header("Location:questions.php");
		exit;
	}
	}
?>
<?php require '../assets/footer.php'?>