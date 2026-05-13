Webアプリ公開に伴う、WindowsServerへのインストール<br>
　共通：<br>
- MySQL 8.0.46<br>
- PostgreSQL 16.13

##　Java：<br>
実機での状況<br>
　　Eclipseと違い、WindowsServerプライベートIPアドレスや、<br>
　　ポートの指定でないと、アクセスできず、戸惑った<br>
<img width="800" height="300" alt="image" src="https://github.com/user-attachments/assets/c538cb5c-a6fb-4cb0-81cd-4b969e6ca5e3" /><br>
WindowsServerへのインストール<br>

- OpenJDK 17.0.19（Temurin（Eclipse Adoptium））<br>
- mysql-connector-java-8.0.30.jar<br>
- Tomcat9［Javaアプリケーションサーバー Javaのサポートは8〜17］<br>

## PHP：<br>
実機での状況<br>
　　WindowsServerプライベートIPアドレスを指定する以外、問題なくアクセスできた<br>
<img width="800" height="300" alt="image" src="https://github.com/user-attachments/assets/3b496693-8b56-4d0e-a70a-fa1c092aad9f" /><br>
WindowsServerへのインストール<br>

- PHP 8.5.6<br>
- Microsoft Visual C++ 2022 X64 Minimum Runtime - 14.51.36231<br>
- Apache/2.4.67 (Win64)［Webサーバー］<br>