-- insert admin user if doesnt exist
-- password = bcrypt('password')
INSERT INTO user (verified, name, email, password, role, bannerId)
SELECT true, 'System Admin', 'admin@dal.ca', '$2y$12$Ghq/Cp0wmg83qKpWq0ML7eeqz1ZuInpZ.KLl0g6lflUwpImh4YaSS', 'ADMIN', 'B00007007' from dual
WHERE (SELECT count(*) FROM user WHERE role='ADMIN') = 0 ;