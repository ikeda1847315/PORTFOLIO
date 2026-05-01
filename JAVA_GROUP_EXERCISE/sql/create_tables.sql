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
INSERT INTO user VALUES('2500002','Pc1r9X5AiEsNcKSDcWRdqNCILstbKAVQriUmt+5D6Jo=','管理花子','大阪府','0','1');
INSERT INTO user VALUES('2500003','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','<h1>悪</h1>','<script>confirm("悪")</script>','0','0');
INSERT INTO user VALUES('2500004','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','管理削除リチャード','USA','1','1');
INSERT INTO user VALUES('2500005','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','管理たけお','島根県','0','1');
INSERT INTO user VALUES('2500006','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','削除されるくん','削除県','0','0');

INSERT INTO room VALUES('0201','大会議室');
INSERT INTO room VALUES('0301','３Ａ会議室');
INSERT INTO room VALUES('0302','<h1>悪意ある会議室</h1>');

INSERT INTO reservation VALUES(NULL,'0201','2026-01-26','09:00:00','10:00:00','2500001');
INSERT INTO reservation VALUES(NULL,'0301','2026-01-26','10:00:00','11:00:00','2500002');
INSERT INTO reservation VALUES(NULL,'0302','2026-01-26','11:00:00','12:00:00','2500003');
INSERT INTO reservation VALUES(NULL,'0201','2026-01-27','13:00:00','14:00:00','2500001');
INSERT INTO reservation VALUES(NULL,'0201','2026-01-27','14:00:00','15:00:00','2500002');
INSERT INTO reservation VALUES(NULL,'0201','2026-01-28','15:00:00','10:00:00','2500003');
INSERT INTO reservation VALUES(NULL,'0301','2026-01-27','16:00:00','17:00:00','2500004');
INSERT INTO reservation VALUES(NULL,'0301','2026-01-28','13:00:00','14:00:00','2500001');
INSERT INTO reservation VALUES(NULL,'0302','2026-01-27','13:00:00','14:00:00','2500002');
INSERT INTO reservation VALUES(NULL,'0201','2026-01-28','09:00:00','10:00:00','2500001');
INSERT INTO reservation VALUES(NULL,'0201','2026-01-28','11:00:00','12:00:00','2500002');
INSERT INTO reservation VALUES(NULL,'0201','2026-01-29','09:00:00','10:00:00','2500003');
INSERT INTO reservation VALUES(NULL,'0301','2026-01-29','12:00:00','13:00:00','2500004');
INSERT INTO reservation VALUES(NULL,'0301','2026-01-28','09:00:00','10:00:00','2500001');
INSERT INTO reservation VALUES(NULL,'0302','2026-01-28','14:00:00','15:00:00','2500002');