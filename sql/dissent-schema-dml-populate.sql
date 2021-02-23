USE dissent;

insert into `role` 
	(`name`) 
values
	('ADMIN'),
	('USER');

insert into `user`
	(user_id, email, password_hash, username, photo_url, country, bio) 
values
	(
		'dffec086-b1e9-455a-aab4-ff6c6611fef0', 
        'admin@dissent.com',
        'd1ssent',
		'dissenter101', 
		'https://www.birdnote.org/sites/default/files/Daffy_Duck-2-warner-bros-625.jpg', 
		'United States', 
		'The truth is out there.'
	),
	(
		'fhsajk21-czxm-fsa7-zzlq-sa8cxzkj11s9', 
        'dissenter102@gmail.com',
        'goodpassword',
		'dissenter102',
		'https://cdn.eso.org/images/thumb700x/eso1907a.jpg', 
		'United States', 
		'I don''t believe anything I read.'
	),
	(
		'11786jks-sjal-s7a9-msdh-sd2isdksaoia', 
		'dissenter103@gmail.com',
        'goodpassword',
        'dissenter103',
		'https://lumiere-a.akamaihd.net/v1/images/ct_mickeymouseandfriends_goofy_ddt-16970_5d1d64dc.jpeg?region=0,0,600,600&width=480', 
		'United States', 
		'What is a dissenter?'
	);
	
	insert into user_role 
	(user_id, role_id) 
values
	('dffec086-b1e9-455a-aab4-ff6c6611fef0', 1),
	('dffec086-b1e9-455a-aab4-ff6c6611fef0', 2),
    ('fhsajk21-czxm-fsa7-zzlq-sa8cxzkj11s9', 1),
    ('11786jks-sjal-s7a9-msdh-sd2isdksaoia', 1);
    
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
	),
	(
		'dshak1la-s72j-mshs-5d8a-n2hsdix8sjak', 
		'Space.com', 
		'https://www.space.com/', 
		'Space.com is the premier source of space exploration, innovation and astronomy news, chronicling (and celebrating) humanity''s ongoing expansion across the final frontier.'
	),
	(
		'dmasjanq-654k-am2j-sj1l-dkjdyanwghsf', 
		'BBC News', 
		'https://www.bbc.com/', 
		'BBC News is an operational business division of the British Broadcasting Corporation responsible for the gathering and broadcasting of news and current affairs.'
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
		'dfash1l1-fsnm-7xkl-s72j-pdismqn2ud71', 
		'dshak1la-s72j-mshs-5d8a-n2hsdix8sjak', 
		'It''s landing day on Mars! NASA''s Perseverance rover will touch down on the Red Planet today', 
		'Elizabeth Howell',
		'Perseverance''s landing caps a decade of preparation by Mars teams worldwide', 
		'https://www.space.com/perseverance-mars-rover-landing-day', 
		'https://cdn.mos.cms.futurecdn.net/TKvUVjJZQRA399tRbLHj55-970-80.png',
		'1818-04-10T00:00:00.000', 
		'1818-02-16T12:00:00.000'
	),
	(
		's7d98a02-sm21-s82k-dm2k-dn2ja82ks0sj', 
		'dmasjanq-654k-am2j-sj1l-dkjdyanwghsf', 
		'Texas weather: Residents told to boil tap water amid power blackouts', 
		'Unknown',
		'Nearly seven million people in the US state of Texas have been told to boil tap water before consuming it after a deadly winter storm caused power blackouts at treatment facilities.', 
		'https://www.bbc.com/news/world-us-canada-56109720', 
		'https://ichef.bbci.co.uk/news/976/cpsprodpb/14A93/production/_117072648_hi065773288.jpg',
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
		'dfash1l1-fsnm-7xkl-s72j-pdismqn2ud71',
		1
	),
	(
		's7d98a02-sm21-s82k-dm2k-dn2ja82ks0sj',
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
	(3, 'Biased', '#E71A43', 1);

insert into post_feedback_tag 
	(post_id, user_id, feedback_tag_id)
values
	('a7db5cb6-446a-4c8e-836e-006d9ff239b5', 'dffec086-b1e9-455a-aab4-ff6c6611fef0', 2), # Fallicious
	('a7db5cb6-446a-4c8e-836e-006d9ff239b5', 'dffec086-b1e9-455a-aab4-ff6c6611fef0', 3); # Biased
	
insert into article_feedback_tag 
	(article_id, user_id, feedback_tag_id)
values
	('c32bec11-b9a0-434b-bda7-08b9cf2007e2', 'dffec086-b1e9-455a-aab4-ff6c6611fef0', 1); # Sound
    
    
select * from article_topic;