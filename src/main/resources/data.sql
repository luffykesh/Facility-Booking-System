
-- https://stackoverflow.com/questions/913841/mysql-conditional-insert
INSERT INTO user (verified, name, email, password, role, bannerId)
SELECT true, 'System Admin', 'admin@dal.ca', 'password', 'ADMIN', 'B00007007'
WHERE (SELECT count(*) FROM user WHERE role='ADMIN') = 0;