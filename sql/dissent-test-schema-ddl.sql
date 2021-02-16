DROP DATABASE IF EXISTS dissent_test;
CREATE DATABASE dissent_test;
USE dissent_test;

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
    topic_id VARCHAR(255) NOT NULL,
    source_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    article_url VARCHAR(255) NOT NULL,
    article_image_url VARCHAR(255),
    date_published DATETIME NOT NULL,
    date_posted DATETIME NOT NULL,
    CONSTRAINT fk_article_topic_id FOREIGN KEY (topic_id)
        REFERENCES topic (topic_id),
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

delimiter //
create procedure set_known_good_state()
begin

# Clean Tables
	delete from post_feedback_tag;
    delete from feedback_tag;
    delete from post;
    
    delete from article_topic;
    delete from article;
    delete from `source`;
    delete from topic;
    
    delete from `user`;
    delete from user_login;
    
# Populate Tables
    insert into user_login 
		(user_login_id, email, `password`) 
	values
		('103a7d9b-f72b-4469-b1a3-bdba2f6356b4', 'user@dissent.com', 'test0000');
    
    insert into `user`
		(user_id, user_login_id, username, user_role, photo_url, country, bio) 
	values
        (
			'dffec086-b1e9-455a-aab4-ff6c6611fef0', 
			'103a7d9b-f72b-4469-b1a3-bdba2f6356b4', 
            'dissenter101', 
            'user', 
            'https://cdn.eso.org/images/thumb700x/eso1907a.jpg', 
            'United States', 
            'The truth is out there.'
		);
        
	insert into topic 
		(topic_id, topic_name) 
	values
		('e920c55d-4b95-4b69-a8e3-04cc1eb8e2cb', 'Science');
    
    insert into `source`
		(source_id, source_name, website_url, `description`) 
    values
		(
			'd293ae18-63e0-49b7-87fd-9856bcf52884', 
			'European Southern Observatory', 
            'https://www.eso.org/', 
            'ESO, the European Southern Observatory, is the foremost intergovernmental astronomy organisation in Europe and the world\'s most productive astronomical observatory'
		);
        
	insert into article
		(article_id, topic_id, source_id, title, `description`, article_url, article_image_url, date_published, date_posted)
	values
		(
			'c32bec11-b9a0-434b-bda7-08b9cf2007e2', 
			'e920c55d-4b95-4b69-a8e3-04cc1eb8e2cb', 
            'd293ae18-63e0-49b7-87fd-9856bcf52884', 
            'First Image of a Black Hole', 
            `The shadow of a black hole seen here is the closest we can come to an image of the black hole itself`, 
            'https://www.eso.org/public/images/eso1907a/', 
            'https://cdn.eso.org/images/thumb700x/eso1907a.jpg',
            '04/10/2019', 
            '2/16/2021');
        
	insert into article_topic
		(article_id, topic_id) 
	values
		(
			'c32bec11-b9a0-434b-bda7-08b9cf2007e2',
            'e920c55d-4b95-4b69-a8e3-04cc1eb8e2cb'
        );
        
	insert into post
		(post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content)
	values
		(
			'a7db5cb6-446a-4c8e-836e-006d9ff239b5', 
            null, 
            'c32bec11-b9a0-434b-bda7-08b9cf2007e2', 
            'dffec086-b1e9-455a-aab4-ff6c6611fef0', 
            true, 
            '02/16/2021', 
            'I\'ll have to see this black-hole to believe it!'
		),
        (   
			'd7e12582-6f81-4f02-9e6e-18190f622264', 
            'a7db5cb6-446a-4c8e-836e-006d9ff239b5', 
            'c32bec11-b9a0-434b-bda7-08b9cf2007e2', 
            'dffec086-b1e9-455a-aab4-ff6c6611fef0', 
            false, 
            '02/16/2021', 
            'Wait --- Nevermind, because science.'
		);
        
	insert into feedback_tag 
		(feedback_tag_id, `name`)
    values
		('39cc81c9-8e94-4514-8037-1dd17d15e60d', `Sound`),
        ('1c86b6af-8366-4653-acd9-662bf55e34f4', `Fallicious`),
        ('1cefb24d-c406-4c9f-9374-2ab84ee01272', `Biased`);
	
	insert into post_feedback_tag 
		(post_id, feedback_tag_id, count)
    values
		('a7db5cb6-446a-4c8e-836e-006d9ff239b5', '1c86b6af-8366-4653-acd9-662bf55e34f4', 342), # Fallicious
        ('a7db5cb6-446a-4c8e-836e-006d9ff239b5', '1cefb24d-c406-4c9f-9374-2ab84ee01272', 125); # Biased

end //
-- 4. Change the statement terminator back to the original.
delimiter ;

