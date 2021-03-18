-- insert admin user if doesnt exist
INSERT INTO user (verified, name, email, password, role, bannerId)
SELECT true, 'System Admin', 'admin@dal.ca', 'password', 'ADMIN', 'B00007007' from dual
WHERE (SELECT count(*) FROM user WHERE role='ADMIN') = 0 ;