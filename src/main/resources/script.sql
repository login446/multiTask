DROP TABLE IF EXISTS multitask.users;
CREATE TABLE multitask.users (
  id               INT         NOT NULL AUTO_INCREMENT,
  name             VARCHAR(45) NOT NULL,
  date_of_creation DATETIME    NOT NULL,
  access_level     VARCHAR(45) NOT NULL,
  is_delete        TINYINT(1)  NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO multitask.users (
  name,
  date_of_creation,
  access_level,
  is_delete)
VALUES ('admin',
        CURRENT_DATE(),
        'ADMIN',
        0);

DROP TABLE IF EXISTS multitask.tasks;
CREATE TABLE multitask.tasks (
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

DROP TABLE IF EXISTS multitask.comments;
CREATE TABLE multitask.comments (
  comment_id       INT          NOT NULL AUTO_INCREMENT,
  task_id          INT          NOT NULL,
  date_of_creation DATETIME     NOT NULL,
  author_id        INT          NOT NULL,
  comment_text     VARCHAR(255) NOT NULL,
  PRIMARY KEY (comment_id)
);
