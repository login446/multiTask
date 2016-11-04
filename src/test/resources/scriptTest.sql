DROP TABLE IF EXISTS multitasktest.users;
CREATE TABLE multitasktest.users (
  id               INT         NOT NULL AUTO_INCREMENT,
  name             VARCHAR(45) NOT NULL,
  date_of_creation DATETIME    NOT NULL,
  access_level     VARCHAR(45) NOT NULL,
  is_delete        TINYINT(1)  NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO multitasktest.users (
  name,
  date_of_creation,
  access_level,
  is_delete)
VALUES ('admin',
        CURRENT_DATE(),
        'ADMIN',
        0);
INSERT INTO multitasktest.users (
  name,
  date_of_creation,
  access_level,
  is_delete)
VALUES ('user',
        CURRENT_DATE(),
        'USER',
        0);
INSERT INTO multitasktest.users (
  name,
  date_of_creation,
  access_level,
  is_delete)
VALUES ('userDelete',
        CURRENT_DATE(),
        'USER',
        1);


DROP TABLE IF EXISTS multitasktest.tasks;
CREATE TABLE multitasktest.tasks (
  task_id          INT          NOT NULL AUTO_INCREMENT,
  author_id        INT          NOT NULL,
  date_of_creation DATETIME     NOT NULL,
  task_title       VARCHAR(45)  NOT NULL,
  status           VARCHAR(45)  NOT NULL,
  task_text        VARCHAR(255) NOT NULL,
  deadline         DATETIME     NOT NULL,
  executor_id      INT          NOT NULL,
  PRIMARY KEY (task_id)
);

INSERT INTO multitasktest.tasks (
  author_id,
  date_of_creation,
  task_title,
  status,
  task_text,
  deadline,
  executor_id)
VALUES (1,
        CURRENT_DATE(),
        'title1',
        'NEW',
        'text1',
        '2016/11/07 17:23',
        1);
INSERT INTO multitasktest.tasks (
  author_id,
  date_of_creation,
  task_title,
  status,
  task_text,
  deadline,
  executor_id)
VALUES (1,
        CURRENT_DATE(),
        'title2',
        'NEW',
        'text2',
        '2016/11/09 17:23',
        2);
INSERT INTO multitasktest.tasks (
  author_id,
  date_of_creation,
  task_title,
  status,
  task_text,
  deadline,
  executor_id)
VALUES (2,
        CURRENT_DATE(),
        'title3',
        'WORK',
        'text3',
        '2016/11/07 17:23',
        2);


DROP TABLE IF EXISTS multitasktest.comments;
CREATE TABLE multitasktest.comments (
  comment_id       INT          NOT NULL AUTO_INCREMENT,
  task_id          INT          NOT NULL,
  date_of_creation DATETIME     NOT NULL,
  author_id        INT          NOT NULL,
  comment_text     VARCHAR(255) NOT NULL,
  PRIMARY KEY (comment_id)
);

INSERT INTO multitasktest.comments (
  task_id,
  date_of_creation,
  author_id,
  comment_text)
VALUES (2,
        CURRENT_DATE(),
        1,
        'comment1');
INSERT INTO multitasktest.comments (
  task_id,
  date_of_creation,
  author_id,
  comment_text)
VALUES (3,
        CURRENT_DATE(),
        2,
        'comment2');
