drop database if exists ideastock;
create database ideastock default character set utf8 collate utf8_general_ci;
GRANT ALL PRIVILEGES ON ideastock.* TO 'root'@'localhost';
-- パスワード解除：ALTER USER 'root'@'localhost' IDENTIFIED BY '';

use ideastock;

create table user(
    id int auto_increment primary key,
    loginId varchar(10) not null UNIQUE,
    CHECK (CHAR_LENGTH(loginId) >= 8),
    password varchar(20) not null UNIQUE,
    CHECK (CHAR_LENGTH(password) >= 6),
    name varchar(10) not null
);

create table question(
    id int auto_increment primary key,
    userId int not null,
    question varchar(256) not null,
    date DATETIME not null,
    deleteFlg TINYINT(1) not null,
    FOREIGN KEY(userId) REFERENCES user(id)
);

create table answer(
    id bigint auto_increment primary key,
    questionId int not null,
    userId int not null,
    answer varchar(256) not null,
    date DATETIME not null,
    deleteFlg TINYINT(1) not null,
    FOREIGN KEY(questionId) REFERENCES question(id),
    FOREIGN KEY(userId) REFERENCES user(id)
);

CREATE TABLE reply (
    id INT AUTO_INCREMENT PRIMARY KEY,
    answerId BIGINT NOT NULL,
    userId INT NOT NULL,
    reply varchar(256) NOT NULL,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleteFlg TINYINT(1) DEFAULT 0,
    FOREIGN KEY (answerId) REFERENCES answer(id),
    FOREIGN KEY (userId) REFERENCES user(id)
) ;