DROP DATABASE IF EXISTS dissent;
CREATE DATABASE dissent;
USE dissent;

# USER MANAGEMENT TABLES
CREATE TABLE user_login (
    user_login_id VARCHAR(255) PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL
);

CREATE TABLE `user` (
    user_id VARCHAR(255) PRIMARY KEY,
    user_login_id VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    user_role VARCHAR(255) NOT NULL,
    photo_url VARCHAR(255),
    country VARCHAR(255),
    bio VARCHAR(255),
    CONSTRAINT fk_user_user_login_id FOREIGN KEY (user_login_id)
        REFERENCES user_login (user_login_id)
);

# ARTICLE TABLES
CREATE TABLE topic (
    topic_id VARCHAR(255) PRIMARY KEY,
    topic_name VARCHAR(255) NOT NULL
);

CREATE TABLE `source` (
    source_id VARCHAR(255) PRIMARY KEY,
    source_name VARCHAR(255) NOT NULL,
    website_url VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL
);

CREATE TABLE article (
    article_id VARCHAR(255) PRIMARY KEY,
    source_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    article_url VARCHAR(255) NOT NULL,
    article_image_url VARCHAR(255),
    date_published DATETIME NOT NULL,
    date_posted DATETIME NOT NULL,
    CONSTRAINT fk_article_source_id FOREIGN KEY (source_id)
        REFERENCES `source` (source_id)
);

# ARTICLE-TOPIC BRIDGE TABLE
CREATE TABLE article_topic (
    article_id VARCHAR(255) NOT NULL,
    topic_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_article_topic PRIMARY KEY (article_id , topic_id),
    CONSTRAINT fk_article_topic_article_id FOREIGN KEY (article_id)
        REFERENCES article (article_id),
    CONSTRAINT fk_article_topic_topic_id FOREIGN KEY (topic_id)
        REFERENCES topic (topic_id)
);

# POST TABLES
CREATE TABLE feedback_tag (
    feedback_tag_id VARCHAR(255) PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL
);

# POST TABLE
CREATE TABLE post (
    post_id VARCHAR(255) PRIMARY KEY,
    parent_post_id VARCHAR(255),
    article_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    is_dissenting BOOL NOT NULL,
    date_posted DATETIME NOT NULL,
    content TEXT(40000) NOT NULL, # INDUSTRY MAX CHAR SIZE
    CONSTRAINT fk_post_parent_post_id FOREIGN KEY (parent_post_id)
		REFERENCES post (post_id),
    CONSTRAINT fk_post_article_id FOREIGN KEY (article_id)
        REFERENCES article (article_id),
    CONSTRAINT fk_post_user_id FOREIGN KEY (user_id)
        REFERENCES `user` (user_id)
);

# POST-feedback_tag BRIDGE TABLE
CREATE TABLE post_feedback_tag (
    post_id VARCHAR(255) NOT NULL,
    feedback_tag_id VARCHAR(255) NOT NULL,
    count INT UNSIGNED NOT NULL, # UNSIGNED => 0 to 4294967295 v.s. -2147483648 to 2147483647
        CONSTRAINT pk_post_feedback_tag PRIMARY KEY (post_id , feedback_tag_id),
    CONSTRAINT fk_post_feedback_tag_post_id FOREIGN KEY (post_id)
        REFERENCES post (post_id),
    CONSTRAINT fk_post_feedback_tag_feedback_tag_id FOREIGN KEY (feedback_tag_id)
        REFERENCES feedback_tag (feedback_tag_id)
);
