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
        'bla',
        'NEW',
        'blablabal',
        '2016/11/03 17:23',
        1);

DROP TABLE IF EXISTS multitasktest.comments;
CREATE TABLE multitasktest.comments (
  comment_id       INT          NOT NULL AUTO_INCREMENT,
  task_id          INT          NOT NULL,
  date_of_creation DATETIME     NOT NULL,
  author_id        INT          NOT NULL,
  comment_text     VARCHAR(255) NOT NULL,
  PRIMARY KEY (comment_id)
);
