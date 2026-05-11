[職業訓練校](https://intecs.ne.jp/education/training/summary/1)でのグループ演習を通じて、自身の担当部分の説明や、個人作成したHTMLの紹介です。

# Java
[Daoファイル関連](https://github.com/ikeda1847315/PORTFOLIO/tree/main/JAVA_GROUP_EXERCISE/src/main/java/jp/co/sys/dao)<br>
[Beanファイル関連](https://github.com/ikeda1847315/PORTFOLIO/tree/main/JAVA_GROUP_EXERCISE/src/main/java/jp/co/sys/bean)<br>
　※MeetingRoom.java以外の加筆修正<br>
[メニューのJSPファイル他](JAVA_GROUP_EXERCISE/src/main/webapp/jsp/menu.jsp)<br>
[データベースデータ](https://github.com/ikeda1847315/PORTFOLIO/blob/main/JAVA_GROUP_EXERCISE/sql/create_tables.sql)<br>
[ライブラリ関連（jarファイル準備）](https://github.com/ikeda1847315/PORTFOLIO/tree/main/JAVA_GROUP_EXERCISE/src/main/webapp/WEB-INF/lib)<br>
<details>
<summary>＜コメント＞</summary>
◆サブリーダーであった為、GitHubでのDiscussionsの有効化や、<br>
　Projectsを用い、issueを用いたWBSの計画カレンダー及び、<br>
　タスク管理を作成する事で、状況の可視化がしやすい状況を構築した。<br>
　また、Googleサービスを用いて、ドキュメント関連の同時編集や、<br>
　チャット等で情報共有等の環境も構築し、円滑なコミュニケーションが実現できた。<br>
◆基本設計のDaoファイルを作成後、追加要件のメソッドを作成した。<br>
　Daoファイルに合わせて、Beanファイルの設定をするのが難しかった。<br>
　また、DBデータ取得後に、行数を取得するメソッドの作成は、<br>
　ResultSet操作を有効にする為の構文作成を調べ作成した。<br>

　　⇒[当該メソッド](https://github.com/ikeda1847315/PORTFOLIO/blob/main/JAVA_GROUP_EXERCISE/src/main/java/jp/co/sys/dao/UserDao.java#L160-L174)<br>
◆生成AIの利用が禁止であったが、パスワードをSHA-256でのハッシュ化及び、<br>
　パスワード認証・パスワードをハッシュ化する際に、<br>
　Pepperの利用等、セキュリティを意識し作成した。<br>
◆XSS対策のエスケープ処理として、JSTLを利用する為のファイル準備及び、<br>
　検証作業後に各JSPファイルへの適応を実施した。<br>
◆Javadocを意識し、各説明文を作成した。
</details> 

# HTML
[GitHubPagesリンク](https://ikeda1847315.github.io/PORTFOLIO/HTML_INDIVIDUAL_EXERCISE/)<br>
[HTMLファイル一式](https://github.com/ikeda1847315/PORTFOLIO/tree/main/HTML_INDIVIDUAL_EXERCISE)<br>
<details>
<summary>＜コメント＞</summary>
生成AIを活用し、架空ゲーム「Carve Out」のLP、<br>
問い合わせフォーム＆サンクスページ、<br>
タスク管理アプリと、お天気検索アプリを作成。<br><br>
--所感--<br>
  タスク管理アプリと、問い合わせフォームは、短い時間で問題なく作成できた。<br>
  LocalStorageの利用や、メーラーの起動等、今後に活用できそうな部分が学べた。<br><br>
  お天気検索アプリは、個人APIの公開に不安を感じ、<br>
  提示された「Open Weather」から、個人登録のいらない「Open-Meteo」へ変更した。<br>
  その他、weatherCodesを値指定から、スイッチ方式に変更。<br>
  データ取得時間の初期表示が、UTC（世界標準時）であった為、<br>
  日本のタイムゾーンに変更した。<br>
  また、台風のコードが無かった為、風の強さをイメージできるようにし、<br>
  雨予報＋風の強さで、環境がイメージできるようにした。<br><br>
  架空ゲーム「Carve Out」のLPは、体感的なデザイン変更が多数あり、<br>
  時間がかかったが、納得のいくものが作成できた。<br>
  尚、相対パスのフォルダ名に大文字・小文字が混在し、アップロード後に修正を要した。<br><br>
  今後は、各ページをトレースし、何をどう使ったのかまで、把握する為にコメントを追加していく予定です。
</details> 

# PHP
[answer.phpファイル](https://github.com/ikeda1847315/PORTFOLIO/blob/main/PHP_GROUP_EXERCISE/pages/answer.php)<br>
[データベースデータ関連](https://github.com/ikeda1847315/PORTFOLIO/tree/main/PHP_GROUP_EXERCISE/db/detabase)<br>
<details>
<summary>＜コメント＞</summary>
初めてのグループ演習で、相手から受け取る通信、<br>
こちらから送信する通信の把握・設定が難しかった。<br>
また、生成AIを用いてコードを書いている為、<br>
トレースを実施後、コメントを用い、説明文で挙動を説明する事を心がけた。
</details> 

# GitHub Actions追加（2026/05/06）
Pushした際、HTMLの評価をLighthouseで実行する。<br>
game-lp.htmlのスコアが悪いため、今後はそちらをメインに改善予定。<br>
　※[PageSpeed Insights](https://pagespeed.web.dev/?hl=ja)では、<br>
 　　デスクトップの測定は問題ないが、携帯電話がフルスコアでない為、今後も回収予定。<br>

# 環境構築
１　Windows11に、WSLをインストールし、UbuntuでLinuxを操作できる環境を構築。<br>
　　合わせて、VSCodeで操作できるようにし、GitHubへの連携も設定。<br><br>
２　仮想マシン（ＶＭ）環境の構築として、Oracle VM VirtualBoxを<br>
　　インストールし、Windows Server用の仮想マシンを作成。<br>
　　　※Windows Server Evaluation をインストール<br>
　　初期状態保存のため、VirtualBox のスナップショットを作成後、<br>
　　仮想マシンに、環境を整える為、VirtualBox Guest Additions をインストールした。<br>
　　　※画面サイズ自動調整やクリップボード共有の有効化<br>
　　仮想社内LANのネットワーク構成を設定し、ipconfigやpingコマンドで、ネットワーク確認を実施。<br>
　　　※NAT（VMをインターネットに接続する仕組み）及び、Host-Only Adapter（仮想社内LAN）を構成<br>
　　Windows Server用に名称変更と、Host-Only Adapterのネットワーク設定を実施。<br>
　　固定IPを設定後、共有フォルダ及び、新規アカウントを作成し、VM同士でのファイル共有や、<br>
　　VMと実機でのファイル共有を実施し、アカウントの権限設定変更を学んだ。<br>
　　　※SMB共有設定（Server Message Block［ファイル共有プロトコル］）<br>
　　　　　⇒ネットワーク経由で、共有フォルダへアクセスできるかを制御する権限<br>
　　　　NTFS権限（New Technology File System［フォルダやファイルのアクセス制御］）<br>
　　　　　⇒フォルダやファイル自体に対するアクセス権限<br>
　　　　実機⇔VMは、VirtualBox共有フォルダ機能を利用<br>
