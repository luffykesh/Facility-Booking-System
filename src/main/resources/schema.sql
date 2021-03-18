CREATE TABLE IF NOT EXISTS user (
  id int NOT NULL AUTO_INCREMENT,
  verified tinyint DEFAULT '0',
  name text,
  email varchar(255) DEFAULT NULL,
  password varchar(255) NOT NULL,
  role varchar(255) DEFAULT NULL,
  bannerId varchar(255) DEFAULT NULL,

  PRIMARY KEY (id)
);
