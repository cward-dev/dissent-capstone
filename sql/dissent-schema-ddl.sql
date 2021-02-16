drop database if exists dissent;
create database dissent;
use dissent;

create table user_login (
	user_login_id VARCHAR(255) PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL
);

CREATE TABLE `user` (
    user_id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    user_role VARCHAR(255) NOT NULL,
    photo_url VARCHAR(255),
    country VARCHAR(255),
    bio VARCHAR(255),
    user_login_id VARCHAR(255) NOT NULL,
    constraint fk_user_user_login_id
		foreign key (user_login_id)
        references user_login(user_login_id)
);