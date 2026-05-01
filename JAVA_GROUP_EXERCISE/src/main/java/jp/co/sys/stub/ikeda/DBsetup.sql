-- 構文の説明
-- DROP USER IF EXISTS 'user'@'localhost';
-- 　⇒userアカウントが、あれば消す
-- CREATE USER 'user'@'localhost' IDENTIFIED BY 'pass';
-- GRANT ALL PRIVILEGES ON `meetingroomB`.* TO 'user'@'localhost';
-- 　⇒ユーザー登録及び、そのデータベース内にあるすべてのテーブルへの権限付与
-- FLUSH PRIVILEGES;
-- 　⇒メモリ情報の再読み込み
-- （補足）権限確認
-- SHOW GRANTS FOR 'user'@'localhost';
-- ユーザー一覧
-- select user, host from mysql.user;
-- https://www.digitalocean.com/community/tutorials/how-to-create-a-new-user-and-grant-permissions-in-mysql-ja

DROP USER IF EXISTS 'user'@'localhost';
CREATE USER 'user'@'localhost' IDENTIFIED BY 'pass';
GRANT ALL PRIVILEGES ON `meetingroomb`.* TO 'user'@'localhost';
FLUSH PRIVILEGES;

DROP DATABASE IF EXISTS meetingroomb;
create database meetingroomb default character set utf8 collate utf8_general_ci;
USE meetingroomb;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS reservation;

CREATE TABLE user (
        id VARCHAR(7) PRIMARY KEY,
        password VARCHAR(255) NOT NULL,
        name VARCHAR(10) NOT NULL,
        address VARCHAR(30),
        isDeleted TINYINT(1) NOT NULL DEFAULT 0,
        isAdmin TINYINT(1) NOT NULL DEFAULT 0
);

CREATE TABLE room (
        id VARCHAR(4) PRIMARY KEY,
        name VARCHAR(20) NOT NULL
);

CREATE TABLE reservation (
        id INT PRIMARY KEY AUTO_INCREMENT,
        roomId VARCHAR(4) NOT NULL,
        date DATE NOT NULL,
        start TIME NOT NULL,
        end TIME NOT NULL,
        userId VARCHAR(7) NOT NULL,
        FOREIGN KEY(roomid) REFERENCES room(id),
        FOREIGN KEY(userid) REFERENCES user(id),
        UNIQUE(roomId, date, start)
);

INSERT INTO user VALUES('2500001','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','一般太郎','東京都','0','0');
INSERT INTO user VALUES('2500002','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','管理花子','大阪府','0','1');
INSERT INTO user VALUES('2500003','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','削除よしお','北海道','1','0');
INSERT INTO user VALUES('2500004','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','管理削除リチャード','USA','1','1');
INSERT INTO user VALUES('2500005','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','管理たけお','島根県','0','1');

INSERT INTO room VALUES('0201','大会議室');
INSERT INTO room VALUES('0301','３Ａ会議室');
INSERT INTO room VALUES('0302','３Ｂ会議室');

INSERT INTO reservation VALUES(NULL,'0201','2026-01-09','09:00:00','10:00:00','2500001');
INSERT INTO reservation VALUES(NULL,'0201','2026-01-09','11:00:00','12:00:00','2500002');
INSERT INTO reservation VALUES(NULL,'0201','2026-01-08','09:00:00','10:00:00','2500003');
INSERT INTO reservation VALUES(NULL,'0301','2026-01-08','12:00:00','13:00:00','2500004');
INSERT INTO reservation VALUES(NULL,'0301','2026-01-07','09:00:00','10:00:00','2500001');
INSERT INTO reservation VALUES(NULL,'0302','2026-01-07','13:00:00','14:00:00','2500002');
INSERT INTO reservation VALUES(NULL,'0201','2026-01-30','09:00:00','10:00:00','2500001');
INSERT INTO reservation VALUES(NULL,'0201','2026-01-30','11:00:00','12:00:00','2500002');
INSERT INTO reservation VALUES(NULL,'0201','2026-01-29','09:00:00','10:00:00','2500003');
INSERT INTO reservation VALUES(NULL,'0301','2026-01-29','12:00:00','13:00:00','2500004');
INSERT INTO reservation VALUES(NULL,'0301','2026-01-29','09:00:00','10:00:00','2500001');
INSERT INTO reservation VALUES(NULL,'0302','2026-01-29','13:00:00','14:00:00','2500002');