CREATE SCHEMA `bananamusicdb` ;

USE mysql;

CREATE USER 'banana'@'%' IDENTIFIED BY 'platano';

GRANT ALL PRIVILEGES ON bananamusicdb.* TO 'banana'@'%';
FLUSH PRIVILEGES;