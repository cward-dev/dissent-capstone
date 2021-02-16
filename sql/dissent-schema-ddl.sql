drop database if exists dissent;
create database dissent;
use dissent;

CREATE TABLE user_login (
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
    CONSTRAINT fk_user_user_login_id FOREIGN KEY (user_login_id)
        REFERENCES user_login (user_login_id)
);

CREATE TABLE topic (
    topic_id VARCHAR(255) PRIMARY KEY,
    topic_name VARCHAR(255) NOT NULL
);

CREATE TABLE `source` (
    source_id VARCHAR(255) PRIMARY KEY,
    source_name VARCHAR(255),
    website_url VARCHAR(255),
    `description` VARCHAR(255)
);

create table article (
	article_id VARCHAR(255) PRIMARY KEY,
    title varchar(255) not null,
    `description`
	article_url
	article_image_url
	date_published
	date_posted

    

);



