DROP DATABASE IF EXISTS dissent_test;
CREATE DATABASE dissent_test;
USE dissent_test;

# USER MANAGEMENT TABLES
CREATE TABLE `role` (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE `user` (
    user_id VARCHAR(255) PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(2048) NOT NULL,
    username VARCHAR(255) NOT NULL,
    photo_url VARCHAR(255),
    country VARCHAR(255),
    bio VARCHAR(255),
    is_active BOOL NOT NULL DEFAULT TRUE
);

CREATE TABLE user_role (
    user_id VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (user_id , role_id),
    CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id)
        REFERENCES `user` (user_id),
    CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id)
        REFERENCES `role` (role_id)
);

# ARTICLE TABLES
CREATE TABLE topic (
    topic_id int PRIMARY KEY AUTO_INCREMENT,
    topic_name VARCHAR(255) NOT NULL,
    is_active BOOL NOT NULL DEFAULT true
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
	color_hex VARCHAR(7) NOT NULL,
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
		REFERENCES post (post_id) ON DELETE CASCADE,
    CONSTRAINT fk_post_article_id FOREIGN KEY (article_id)
        REFERENCES article (article_id) ON DELETE CASCADE,
    CONSTRAINT fk_post_user_id FOREIGN KEY (user_id)
        REFERENCES `user` (user_id) ON DELETE CASCADE
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


delimiter //
create procedure set_known_good_state()
begin

# Clean Tables
	delete from article_feedback_tag;
	delete from post_feedback_tag;
    
    delete from feedback_tag;
    ALTER TABLE feedback_tag AUTO_INCREMENT = 1;
    
    delete from post;
    
    delete from article_topic;
    delete from article;
    
    delete from `source`;
    
    delete from topic;
    ALTER TABLE topic AUTO_INCREMENT = 1;
    
    delete from user_role;
    delete from `user`;
    delete from `role`;
    ALTER TABLE `role` AUTO_INCREMENT = 1;
    
    
# Populate Tables
    insert into `role` 
		(`name`) 
	values
		('ADMIN'),
        ('USER');
    
    insert into `user`
		(user_id, username, email, password_hash, photo_url, country, bio) 
	values
        (
			'dffec086-b1e9-455a-aab4-ff6c6611fef0', 
            'dissenter101', 
            'milan@stoj.com', 
			'testPass', 
            'https://cdn.eso.org/images/thumb700x/eso1907a.jpg', 
            'United States', 
            'The truth is out there.'
		),
        (
			'A', 
            'Update-dissenter', 
            'UPDATE@stoj.com', 
			'testPass', 
            'https://cdn.eso.org/images/thumb700x/eso1907a.jpg', 
            'United States', 
            'The truth is out there.'
		),
        (
			'b', 
            'Delete-Dissenter101', 
            'Delete@stoj.com', 
			'testPass', 
            'https://cdn.eso.org/images/thumb700x/eso1907a.jpg', 
            'United States', 
            'The truth is out there.'
		);
        
	insert into user_role 
		(user_id, role_id) 
	values
		('dffec086-b1e9-455a-aab4-ff6c6611fef0', 1),
        ('dffec086-b1e9-455a-aab4-ff6c6611fef0', 2);
        
	insert into topic 
		(topic_id, topic_name, is_active) 
	values
		(1, 'Science', 1),
        (2, 'Philosophy', 1),
        (3, 'Economics', 1),
        (4, 'Politics', 1),
        (5, 'Healthcare', 0),
        (6, 'History', 0);
    
    insert into `source`
		(source_id, source_name, website_url, `description`) 
    values
		(
			'd293ae18-63e0-49b7-87fd-9856bcf52884', 
			'European Southern Observatory', 
            'https://www.eso.org/', 
            'ESO, the European Southern Observatory, is the foremost intergovernmental astronomy organisation in Europe and the world\'s most productive astronomical observatory.'
		),
		(
			'fsdafas8-fsad-fsd8-fsda-413h1hj1a90s', 
			'CNN', 
            'https://www.cnn.com/', 
            'Cable News Network is a multinational news-based pay television channel headquartered in Atlanta.'
		),
		(
			'fsd67a8s-a512-dfb2-saf6-fsadfas76dfa', 
			'Associated Press', 
            'https://www.apnews.com/', 
            'The Associated Press is an American non-profit news agency headquartered in New York City. Founded in 1846, it operates as a cooperative, unincorporated association. Its members are U.S. newspapers and broadcasters.'
		);
        
	insert into article
		(article_id, source_id, title, author, `description`, article_url, article_image_url, date_published, date_posted)
	values
		(
			'c32bec11-b9a0-434b-bda7-08b9cf2007e2', 
            'd293ae18-63e0-49b7-87fd-9856bcf52884', 
            'First Image of a Black Hole', 
            'Michael Douglas',
            'The shadow of a black hole seen here is the closest we can come to an image of the black hole itself', 
            'https://www.eso.org/public/images/eso1907a/', 
            'https://cdn.eso.org/images/thumb700x/eso1907a.jpg',
            '2019-04-10T00:00:00.000', 
            '2021-02-16T12:00:00.000'
		),
        	(
			'a', 
            'd293ae18-63e0-49b7-87fd-9856bcf52884', 
            'Test-Delete', 
            'Michael Douglas',
            'The shadow of a black hole seen here is the closest we can come to an image of the black hole itself', 
            'https://www.eso.org/public/images/eso1907a/', 
            'https://cdn.eso.org/images/thumb700x/eso1907a.jpg',
            '1818-04-10T00:00:00.000', 
            '1818-02-16T12:00:00.000'
		),
        	(
			'b', 
            'd293ae18-63e0-49b7-87fd-9856bcf52884', 
            'Test-Update', 
            'Michael Douglas',
            'The shadow of a black hole seen here is the closest we can come to an image of the black hole itself', 
            'https://www.eso.org/public/images/eso1907a/', 
            'https://cdn.eso.org/images/thumb700x/eso1907a.jpg',
            '2019-04-10T00:00:00.000', 
            '2021-02-16T12:00:00.000'
		);
        

	insert into article_topic
		(article_id, topic_id) 
	values
		(
			'c32bec11-b9a0-434b-bda7-08b9cf2007e2',
            1
        ),
        (
			'a',
            1
        ),
		(
			'b',
            3
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
            '2021-02-15T12:00:00.000', 
            'I\'ll have to see this black-hole to believe it!'
		),
        (   
			'd7e12582-6f81-4f02-9e6e-18190f622264', 
            'a7db5cb6-446a-4c8e-836e-006d9ff239b5', 
            'c32bec11-b9a0-434b-bda7-08b9cf2007e2', 
            'dffec086-b1e9-455a-aab4-ff6c6611fef0', 
            false, 
            '2021-02-16T12:00:00.000', 
            'Wait --- Nevermind, because science.'
		),
		(   
			'dfdsf67s-fd67-580f-f678-44120dsfa873', 
            'd7e12582-6f81-4f02-9e6e-18190f622264', 
            'c32bec11-b9a0-434b-bda7-08b9cf2007e2', 
            'dffec086-b1e9-455a-aab4-ff6c6611fef0', 
            false, 
            '2021-02-16T12:00:00.000', 
            'Science never lies!'
		);
        
	insert into feedback_tag 
		(feedback_tag_id, feedback_tag_name, color_hex, is_active)
	values
		(1, 'Sound', '#00ff00', 1),
		(2, 'Fallacious', '#ffa500', 1),
		(3, 'Biased', '#FF0000', 1),
		(4, 'Not Nice', '#800000', 0),
		(5, 'Too Nice', '#000000', 0);
	
	insert into post_feedback_tag 
		(post_id, user_id, feedback_tag_id)
    values
		('a7db5cb6-446a-4c8e-836e-006d9ff239b5', 'dffec086-b1e9-455a-aab4-ff6c6611fef0', 2), # Fallicious
        ('a7db5cb6-446a-4c8e-836e-006d9ff239b5', 'dffec086-b1e9-455a-aab4-ff6c6611fef0', 3); # Biased
        
	insert into article_feedback_tag 
		(article_id, user_id, feedback_tag_id)
    values
		('c32bec11-b9a0-434b-bda7-08b9cf2007e2', 'dffec086-b1e9-455a-aab4-ff6c6611fef0', 1), # Sound
        ('a','b',1);

end //
-- 4. Change the statement terminator back to the original.
delimiter ;

set SQL_SAFE_UPDATES = 0;
call set_known_good_state;