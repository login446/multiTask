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

