CREATE TABLE IF NOT EXISTS user (
  id int NOT NULL AUTO_INCREMENT,
  verified tinyint DEFAULT '0',
  name text,
  email varchar(255) DEFAULT NULL,
  password varchar(255) NOT NULL,
  role varchar(255) DEFAULT NULL,
  bannerId varchar(255) DEFAULT NULL,
  token varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS facility (
  id int NOT NULL AUTO_INCREMENT,
  name text,
  description text,
  location varchar(255) DEFAULT NULL,
  occupancy int DEFAULT NULL,
  manager_id int DEFAULT NULL,
  time_slot time DEFAULT NULL,
  active tinyint DEFAULT '0',
  approval_required tinyint DEFAULT '0',
  PRIMARY KEY (id),
  KEY manager_id (manager_id),
  CONSTRAINT facility_ibfk_1 FOREIGN KEY (manager_id) REFERENCES user (id)
);

ALTER TABLE user
MODIFY password varchar(255);


CREATE TABLE IF NOT EXISTS timing (
  id int NOT NULL AUTO_INCREMENT,
  facility_id int NOT NULL,
  day int NOT NULL,
  start_time TIME NOT NULL,
  end_time time NOT NULL,
  is_blocking tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY(id),
  CONSTRAINT timing_fk_1 FOREIGN KEY (facility_id) REFERENCES facility (id)
);

ALTER TABLE facility
MODIFY time_slot int;
