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
    is_active BOOL NOT NULL DEFAULT true,
    CONSTRAINT fk_user_user_login_id FOREIGN KEY (user_login_id)
        REFERENCES user_login (user_login_id)
);

# ARTICLE TABLES
CREATE TABLE topic (
    topic_id int PRIMARY KEY AUTO_INCREMENT,
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
    author VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    article_url VARCHAR(255) NOT NULL,
    article_image_url VARCHAR(255),
    date_published DATETIME NOT NULL,
    date_posted DATETIME NOT NULL,
    is_active BOOL NOT NULL DEFAULT true,
    CONSTRAINT fk_article_source_id FOREIGN KEY (source_id)
        REFERENCES `source` (source_id)
);

# ARTICLE-TOPIC BRIDGE TABLE
CREATE TABLE article_topic (
    article_id VARCHAR(255) NOT NULL,
    topic_id INT NOT NULL,
    CONSTRAINT pk_article_topic PRIMARY KEY (article_id , topic_id),
    CONSTRAINT fk_article_topic_article_id FOREIGN KEY (article_id)
        REFERENCES article (article_id),
    CONSTRAINT fk_article_topic_topic_id FOREIGN KEY (topic_id)
        REFERENCES topic (topic_id)
);

# POST TABLES
CREATE TABLE feedback_tag (
    feedback_tag_id INT PRIMARY KEY AUTO_INCREMENT,
    feedback_tag_name VARCHAR(255) NOT NULL,
    is_active BOOL NOT NULL DEFAULT true
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
    is_active BOOL NOT NULL DEFAULT true,
    CONSTRAINT fk_post_parent_post_id FOREIGN KEY (parent_post_id)
		REFERENCES post (post_id),
    CONSTRAINT fk_post_article_id FOREIGN KEY (article_id)
        REFERENCES article (article_id),
    CONSTRAINT fk_post_user_id FOREIGN KEY (user_id)
        REFERENCES `user` (user_id)
);

# POST-FEEDBACK_TAG BRIDGE TABLE
CREATE TABLE post_feedback_tag (
    post_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    feedback_tag_id INT NOT NULL,
	CONSTRAINT pk_post_feedback_tag PRIMARY KEY (post_id, user_id, feedback_tag_id),
    CONSTRAINT fk_post_feedback_tag_post_id FOREIGN KEY (post_id)
        REFERENCES post (post_id),
    CONSTRAINT fk_post_feedback_tag_user_id FOREIGN KEY (user_id)
        REFERENCES `user` (user_id),
    CONSTRAINT fk_post_feedback_tag_feedback_tag_id FOREIGN KEY (feedback_tag_id)
        REFERENCES feedback_tag (feedback_tag_id)
);

# ARTICLE-FEEDBACK_TAG BRIDGE TABLE
CREATE TABLE article_feedback_tag (
    article_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    feedback_tag_id INT NOT NULL,
    CONSTRAINT pk_article_feedback_tag PRIMARY KEY (article_id , feedback_tag_id),
    CONSTRAINT fk_article_feedback_tag_article_id FOREIGN KEY (article_id)
        REFERENCES article (article_id),
    CONSTRAINT fk_article_feedback_tag_user_id FOREIGN KEY (user_id)
        REFERENCES `user` (user_id),
    CONSTRAINT fk_article_feedback_tag_feedback_tag_id FOREIGN KEY (feedback_tag_id)
        REFERENCES feedback_tag (feedback_tag_id)
);
