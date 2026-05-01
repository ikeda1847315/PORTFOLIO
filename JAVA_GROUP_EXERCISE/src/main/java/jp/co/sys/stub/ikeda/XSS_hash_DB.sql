DROP USER IF EXISTS 'user'@'localhost';
CREATE USER 'user'@'localhost' IDENTIFIED BY 'pass';
GRANT ALL PRIVILEGES ON `meetingroomB`.* TO 'user'@'localhost';
FLUSH PRIVILEGES;

DROP DATABASE IF EXISTS meetingroomB;
create database meetingroomB default character set utf8 collate utf8_general_ci;
USE meetingroomB;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS reservation;

CREATE TABLE user (
        id VARCHAR(7) PRIMARY KEY,
        password VARCHAR(255) NOT NULL,
        name VARCHAR(10),
        address VARCHAR(30),
        isDeleted TINYINT(1) not null DEFAULT 0,
        isAdmin TINYINT(1) not null DEFAULT 0
);

CREATE TABLE room (
        id VARCHAR(4) PRIMARY KEY,
        name VARCHAR(20)
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


INSERT INTO user VALUES('2500001','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','<h1>悪</h1>','<h1>悪いある県</h1>','0','1');
INSERT INTO user VALUES('2500444','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','<h1>悪</h1>','<script>confirm("悪")</script>','0','0');
INSERT INTO user VALUES('2500002','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','管理花子','大阪府','0','1');
INSERT INTO user VALUES('2500003','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','削除よしお','大阪府','1','0');
INSERT INTO user VALUES('2500004','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','管理削除リチャード','大阪府','1','1');
INSERT INTO user VALUES('2500666','ffREdNu4Y6wzVXYB+EwuRDHHsnwYZvocosDPIMeAAuw=','悪意','<script>confirm("悪")</script>','0','0');

INSERT INTO room VALUES('0201','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('0301','３Ａ会議室');
INSERT INTO room VALUES('0302','３Ｂ会議室');
INSERT INTO room VALUES('4444','<h1>悪意ある会議室</h1>');

INSERT INTO reservation VALUES(NULL,'0201','2026-01-01','09:00:00','10:00:00','2500001');
INSERT INTO reservation VALUES(NULL,'0201','2026-01-01','11:00:00','12:00:00','2500002');
INSERT INTO reservation VALUES(NULL,'0201','2026-01-05','09:00:00','10:00:00','2500003');
INSERT INTO reservation VALUES(NULL,'0301','2026-01-05','12:00:00','13:00:00','2500004');
INSERT INTO reservation VALUES(NULL,'0301','2026-01-05','09:00:00','10:00:00','2500001');
INSERT INTO reservation VALUES(NULL,'0302','2026-01-05','13:00:00','14:00:00','2500002');

INSERT INTO room VALUES('1101','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1102','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1103','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1104','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1105','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1106','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1107','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1108','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1109','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1110','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1111','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1112','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1113','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1114','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1115','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1116','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1117','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1118','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1119','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1120','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1121','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1122','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1123','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1124','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1125','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1126','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1127','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1128','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1129','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1130','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1131','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1132','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1133','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1134','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1135','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1136','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1137','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1138','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1139','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1140','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1141','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1142','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1143','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1144','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1145','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1146','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1147','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1148','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1149','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1150','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1151','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1152','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1153','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1154','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1155','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1156','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1157','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1158','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1159','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1160','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1161','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1162','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1163','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1164','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1165','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1166','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1167','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1168','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1169','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1170','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1171','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1172','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1173','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1174','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1175','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1176','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1177','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1178','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1179','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1180','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1181','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1182','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1183','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1184','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1185','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1186','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1187','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1188','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1189','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1190','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1191','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1192','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1193','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1194','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1195','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1196','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1197','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1198','<h1>悪意ある会議室</h1>');
INSERT INTO room VALUES('1199','<h1>悪意ある会議室</h1>');
