<?php session_start(); ?>
<?php require '../db/connect.php';?>
<?php
// 「ログイン確認」
    // ログインor非ログインユーザーを参照
    $id = $_SESSION['user']['id'] ?? 0;
    unset($_SESSION['q_text']);
    unset($_SESSION['a_text']);

// 削除処理時のリダイレクト処理を定義
    // 左側にメッセージはエラーの赤文字、右側の文字は処理完了の黒文字
    function redirectParams($errorMsg = null, $successMsg = null) {
        // セッションにメッセージを代入
        if ($errorMsg !== null) {
            $_SESSION['err'] .= $errorMsg;
        }
        if ($successMsg !== null) {
            $_SESSION['success_msg'] .= $successMsg;
        }

        // パラメータを保持してリダイレクト
        $redirectUrl = 'questions.php';
        $queryParams = [];

        if (isset($_POST['keyword']) || isset($_GET['keyword'])) {
            $queryParams['keyword'] = $_POST['keyword'] ?? $_GET['keyword'];
        }
        if (isset($_POST['sort']) || isset($_GET['sort'])) {
            $queryParams['sort'] = $_POST['sort'] ?? $_GET['sort'];
        }
        if (isset($_POST['poster']) || isset($_GET['poster'])) {
            $queryParams['poster'] = $_POST['poster'] ?? $_GET['poster'];
        }
        if (!empty($queryParams)) {
            $redirectUrl .= '?' . http_build_query($queryParams);
        }
        // リダイレクト
        header('Location: ' . $redirectUrl);
        exit;
    }

// 「DeleteFlg処理」
    // ログイン時POSTでDeleteFlgを受け取ると処理、SQLインジェクション対策
    // is_numericで一意に数値として判断（例: 1 OR 1=1 や 1; DROP TABLE users;はFALSE）
    if (isset($_POST['delete']) && is_numeric($_POST['delete']) && isset($_SESSION['user'])) {
        // CSRF対策（クロスサイトリクエストフォージェリ）でトークンの確認
        if (!isset($_POST['csrf_token']) || $_POST['csrf_token'] !== $_SESSION['csrf_token']) {
            // トークンを持たない非正規ユーザー
            redirectParams('不正なリクエストです');
        } else {
            try {
                // 正規ユーザーによるリクエスト
                // トランザクションで複数のクエリを一括実行
                $pdo->beginTransaction();
                
                // questionテーブルのdeleteFlg処理
                $sql = $pdo->prepare('UPDATE question SET deleteFlg=1 WHERE id=?;');
                $sql->execute([$_POST['delete']]);
                // answerテーブルのdeleteFlg処理
                $sql = $pdo->prepare('UPDATE answer SET deleteFlg=1 WHERE questionId=?;');
                $sql->execute([$_POST['delete']]);
                
                // DB変更を確定
                $pdo->commit();

                // 使用したトークンを削除して一時トークン化
                unset($_SESSION['csrf_token']);

                // リダイレクト、メッセージ表示
                redirectParams(null, '質問を削除しました');

            } catch (PDOException $de) {
                // クエリ処理エラー時はロールバック
                $pdo->rollBack();
                // リダイレクト、メッセージ表示
                redirectParams('削除処理中にエラーが発生しました: ' . $de->getMessage(), null);
            }
        }
    } else if (isset($_POST['delete'])) {
        // 非ログイン時処理
        // リダイレクト、メッセージ表示
        unset($_POST['poster']);
        redirectParams('削除にはログインが必要です');
    }

// SQL処理
    // ベースのSQLを定義
    $baseSql = 'SELECT user.name, question, date, question.id, userId FROM user INNER JOIN question ON user.id=question.userId WHERE deleteFlg = 0';

    // ソート（最新順と過去順の条件分岐）
    if (isset($_GET['sort']) && $_GET['sort'] == 'old') {
        $orderBySql = ' ORDER BY question.id asc';
    } else {
        $orderBySql = ' ORDER BY question.id desc';
    }

    // 検索時と非検索時の条件分岐
    $keyWordParam = [];
    if (isset($_GET['keyword']) && $_GET['keyword'] !== ''){
        // mb_convert_kanaで全角スペースを半角スペースに統一、引数's'はSpaceの略
        $normalizedKeyword = mb_convert_kana($_GET['keyword'], 's');
        // explodeで半角スペースを区切り文字として判断して単語を分割配列化
        // array_filter()で余分な空の要素を排除
        $keywords = array_filter(explode(' ', $normalizedKeyword));

        // 検索されたkeywordの数だけ繰り返し処理
        foreach ($keywords as $words) {
            $searchWord = '%'.$words.'%';
            // 単語ごとにAND条件を追加する
            $baseSql .= ' AND (question.question LIKE ? OR user.name LIKE ?)';
            $keyWordParam[] = $searchWord;
            $keyWordParam[] = $searchWord;
        }
    }
    
    // 自他の質問を判別
    $posterIdParam = [];
    if (isset($_GET['poster']) && $_GET['poster'] == 'my') {
        if ($id > 0) {
        $baseSql .= ' AND (user.id = ?)';
        $posterIdParam[] = $id;
        }
    }

    // 最終的なパラム、配列を結合
    $finalParams = array_merge($keyWordParam, $posterIdParam);

    // 最終的なクエリ文
    $fainalSql = $baseSql . $orderBySql;

    // データ取得実行
    if (isset($pdo) && $pdo instanceof PDO) {
        $sql = $pdo->prepare($fainalSql);
        $sql->execute($finalParams);
        // fetchAll(PDO::FETCH_ASSOC)で全件連想配列で取得
        $results = $sql->fetchAll(PDO::FETCH_ASSOC);
    } else {
        $results = [];
    }

// 「ナビ表示」
    ?>
    <?php require '../assets/header.php'?>
    <?php
    echo '<div class="header">'; 

    // 左側：アイデア倉庫 + 検索フォーム
    echo '<div class="left-area">';
    echo '<div class="left-box">';
    echo '<p><a href="questions.php">アイデア倉庫</a></p>';

    // エラー・処理成功表示
    if (isset($err)) {
        echo '<p class="err">', $err, '</p>';
    }
    if (isset($_SESSION['err'])) {
        echo '<p class="err">', $_SESSION['err'], '</p>';
        unset($_SESSION['err']);
    }
    if (isset($_SESSION['success_msg'])) {
        echo '<p>', $_SESSION['success_msg'], '</p>';
        unset($_SESSION['success_msg']);
    }

    // 並び替え
        // ?? ''はnull合体演算子、定義されていない変数を対策
        $currentKeyWord = $_GET['keyword'] ?? '';
        $currentSort = $_GET['sort'] ?? '';
        $currentPoster = $_GET['poster'] ?? '';
        
        // ソート機能
        echo '<form action="questions.php" method="GET">';
            // XSS対策（クロスサイトスクリプティング）
            // htmlspecialcharsで特殊文字を通常文字に変換、ENT_QUOTESで'と"を変換
            echo '<input type="hidden" name="poster" value="', htmlspecialchars($currentPoster, ENT_QUOTES, 'UTF-8'),'">';
            echo '<input type="hidden" name="keyword" value="', htmlspecialchars($currentKeyWord, ENT_QUOTES, 'UTF-8'), '">';
        if (isset($_GET['sort']) && $_GET['sort'] == 'old') {
            echo '<input type="hidden" name="sort" value="new">';
            echo '<input type="submit" value="最新順">';
        } else {
            echo '<input type="hidden" name="sort" value="old">';
            echo '<input type="submit" value="過去順">';
        }
        echo '</form>';

        // 自他の質問を抽出
        if (isset($_SESSION['user'])) {
            echo '<form action="questions.php" method="GET">';
                echo '<input type="hidden" name="sort" value="', htmlspecialchars($currentSort, ENT_QUOTES, 'UTF-8'),'">';
                echo '<input type="hidden" name="keyword" value="', htmlspecialchars($currentKeyWord, ENT_QUOTES, 'UTF-8'), '">';
            if (isset($_GET['poster']) && $_GET['poster'] == 'my') {
                echo '<input type="hidden" name="poster" value="folks">';
                echo '<input type="submit" value="全員の質問">';
            } else {
                echo '<input type="hidden" name="poster" value="my">';
                echo '<input type="submit" value="自分の質問">';
            }
            echo '</form>';
        }

    // 検索フォーム
    echo '<form action="questions.php" method="GET">';
        echo '<input type="hidden" name="sort" value="', htmlspecialchars($currentSort, ENT_QUOTES, 'UTF-8'),'">';
        echo '<input type="hidden" name="poster" value="', htmlspecialchars($currentPoster, ENT_QUOTES, 'UTF-8'),'">';
        echo '<input type="text" name="keyword" value="', htmlspecialchars($currentKeyWord, ENT_QUOTES, 'UTF-8'), '">';
        echo '<input type="submit" value="検索">';
    echo '</form>';

    echo '</div>';
    echo '</div>';

    echo '<div class="right-area">';

    // パスワード変更
    if (isset($_SESSION['user'])) {
        echo '<form action="userAdd.php" method="get">';
        echo '<input type="submit" value="パスワード変更">';
        echo '</form>';
    }

    // 質問入力へ遷移
    echo '<form action="questionInput.php" method="get">';
    echo '<input type="submit" value="質問">';
    echo '</form>';

// ログインへ遷移、及びlogoutの値を送信
    if (isset($_SESSION['user'])) {
        echo '<form action="../index.php" method="post">';
        echo '<input type="hidden" name="logout">';
        echo '<input type="submit" value="ログアウト" class="logout">';
        echo '</form>';
    } else {
        echo '<form action="../index.php" method="post">';
        echo '<input type="submit" value="ログイン" class="login">';
        echo '</form>';
    }
    echo '</div>'; 
    echo '</div>'; 

// 「内容表示」
    // 現在のソート状態
    // 表示する内容があるか確認
    if (!empty($results)) {
        // 現在のソート状態を確認し切替表示
        if (isset($_GET['poster']) && $_GET['poster'] == 'my') {
            echo '<p><strong>自分</strong>の質問</p>';
        } else {
            echo '<p><strong>全員</strong>の質問</p>';
        }
        if (isset($_GET['sort']) && $_GET['sort'] == 'old') {
            echo '<p>並びは<strong>過去順</strong>です</p>';
        } else {
            echo '<p>並びは<strong>最新順</strong>です</p>';
        }

        // ログインユーザーのみCSRFトークンをランダム生成し、セッションに保存
        if (isset($_SESSION['user']) && empty($_SESSION['csrf_token'])) {
            // ランダムなバイト列を生成し、16進数の文字列に変換する
            // random_bytes(32)で32バイト256ビットのバイト列を生成（例: 0xfd 0x3a 0x1b 0x...)
            // bin2hex()で16進数形式の文字列に変換、32バイトなら64文字の16進数文字列（例: "fd3a1b... (省略)..."）
            $_SESSION['csrf_token'] = bin2hex(random_bytes(32));
        }
        $token = $_SESSION['csrf_token'] ?? '' ;

        // ループ処理で表示 
        foreach ($results as $questions) {
            // 日付データのレイアウト変更のため
            $dbTime = $questions['date'];
            // dbTime変数へ日付を代入
            $date = new DateTime($dbTime);

            // 文字数制限処理
            $questionText = $questions['question'];
            // mb_strlenで文字数をカウント
            if (mb_strlen($questionText) > 50) {
                // mb_substrで文字数を指定（例 : mb_substr(文字列, 先頭, 末尾) ）
                $displayText = mb_substr($questionText, 0, 50) . '...';
            } else {
                $displayText = $questionText;
            }

            echo '<div class="box">';
            echo '<strong>', htmlspecialchars($questions['name']), '</strong><br>';
            echo htmlspecialchars($displayText, ENT_QUOTES, 'UTF-8'), '<br>';
            // 日付データのレイアウト変更
            echo $date->format('Y/m/d H:i');
            echo '<form action="detail.php" method="get">';
                echo '<input type="hidden" name="id" value="', $questions['id'], '">';
                echo '<input type="submit" value="詳細">';
            echo '</form>';
            
            // ログインしている質問者本人のみ表示、DaleteFlg送信
            if ($id == $questions['userId']) {
                echo '<form action="questions.php" method="post">';
                // ソート状態の保持のためにGETを一緒に送る
                echo '<input type="hidden" name="keyword" value="', htmlspecialchars($currentKeyWord, ENT_QUOTES, 'UTF-8'), '">';
                echo '<input type="hidden" name="sort" value="', htmlspecialchars($currentSort, ENT_QUOTES, 'UTF-8'),'">';
                echo '<input type="hidden" name="poster" value="', htmlspecialchars($currentPoster, ENT_QUOTES, 'UTF-8'),'">';
                // 生成していたトークンをidと共に送る
                echo '<input type="hidden" name="csrf_token" value="', htmlspecialchars($token, ENT_QUOTES, 'UTF-8'), '">';
                echo '<input type="hidden" name="delete" value="', $questions['id'] ,'">';
                echo '<input type="submit" value="削除";">';
                echo '</form>';
            }
            echo '</div>';
        }
    } else {
        echo '<p>質問が見つかりませんでした</p>';
    } 
?>
<?php require '../assets/footer.php'?>