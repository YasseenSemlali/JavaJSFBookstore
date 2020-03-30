-- create database bookstore;

use gb4w20test;
-- Yasseen
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS bookgenre;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS bookauthor;
DROP TABLE IF EXISTS publishers;
DROP TABLE IF EXISTS bookpublisher;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS book_files;
DROP TABLE IF EXISTS file_formats;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS bookorder;

DROP TABLE IF EXISTS ads;
DROP TABLE IF EXISTS taxes;
DROP TABLE IF EXISTS survey_questions;
DROP TABLE IF EXISTS survey_responses;
DROP TABLE IF EXISTS rss_feeds;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE books(
  isbn bigint NOT NULL,
  
  title VARCHAR(100) NOT NULL,
  date_of_publication DATE NOT NULL,
  pages int NOT NULL,
  synopsis VARCHAR(1000) NOT NULL,
  cover VARCHAR(100) NOT NULL,
  wholesale_price DECIMAL(15,2) NOT NULL,
  list_price DECIMAL(15,2) NOT NULL,
  sale_price DECIMAL(15,2) NOT NULL DEFAULT 0,
  timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  active BOOLEAN NOT NULL DEFAULT true,
  
  PRIMARY KEY (isbn), 

    constraint list_price_valid check (list_price > wholesale_price),
    constraint sale_price_valid check ((list_price - sale_price) > wholesale_price)
);

CREATE TABLE genres(
	genre_id bigint NOT NULL auto_increment,
    genre varchar(50) NOT NULL UNIQUE,
    
    primary key(genre_id)
);

CREATE TABLE bookgenre(
	genre_id bigint NOT NULL auto_increment,
    isbn bigint NOT NULL,
    
    FOREIGN KEY(genre_id) REFERENCES genres(genre_id),
    FOREIGN KEY(isbn) REFERENCES books(isbn)
);

CREATE TABLE authors(
	author_id bigint NOT NULL auto_increment,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NULL,
    
    primary key(author_id)
);

CREATE TABLE bookauthor(
	author_id bigint NOT NULL auto_increment,
    isbn bigint NOT NULL,
    
    FOREIGN KEY(author_id) REFERENCES authors(author_id),
    FOREIGN KEY(isbn) REFERENCES books(isbn)
);
		
CREATE TABLE publishers(
	publisher_id bigint NOT NULL auto_increment,
    name varchar(50) NOT NULL UNIQUE,
    
    primary key(publisher_id)
);
	
CREATE TABLE bookpublisher(
	publisher_id bigint NOT NULL auto_increment,
    isbn bigint NOT NULL,
    
    FOREIGN KEY(publisher_id) REFERENCES publishers(publisher_id),
    FOREIGN KEY(isbn) REFERENCES books(isbn)
);
    
CREATE TABLE users(
	user_id bigint NOT NULL auto_increment,
    title varchar(5) NOT NULL,
    first_name varchar(30) NOT NULL,
    last_name varchar(30) NOT NULL,
    company_name varchar(20) NULL,
    address_1 varchar(50) NOT NULL,
    address_2 varchar(50) NULL,
    city varchar(20) NOT NULL,
    province char(2) NOT NULL,
    country varchar(10) NOT NULL,
    postal_code varchar(10) NOT NULL,
    home_phone char(10) NOT NULL,
    cell_phone char(10) NOT NULL,
    email varchar(50) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    is_manager boolean NOT NULL default false,
    
    primary key(user_id)
);
	
CREATE TABLE reviews(
	review_id bigint NOT NULL auto_increment,
    isbn bigint NOT NULL,
    user_id bigint NOT NULL,
    rating numeric(1,0) NOT NULL,
    review varchar(1000) NOT NULL,
    approved_status boolean NOT NULL default false,
	timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    primary key(review_id),
    constraint rating_positive check (rating > 0),
    constraint rating_five_or_less check (rating < 6),
    FOREIGN KEY(user_id) REFERENCES users(user_id),
    FOREIGN KEY(isbn) REFERENCES books(isbn)
);
	
CREATE TABLE file_formats(
	file_format_id bigint NOT NULL auto_increment,
    format varchar(10) NOT NULL UNIQUE,
    
    primary key(file_format_id)
);
	
CREATE TABLE book_files(
	book_file_id bigint NOT NULL auto_increment,
    file_format_id bigint NOT NULL,
    isbn bigint NOT NULL,
    file_location varchar(200),
    
    primary key(book_file_id),
    FOREIGN KEY(isbn) REFERENCES books(isbn),
    FOREIGN KEY(file_format_id) REFERENCES file_formats(file_format_id));
    
CREATE TABLE orders(
	order_id bigint NOT NULL auto_increment,
    user_id bigint NOT NULL,
    billing_address varchar(100) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    PRIMARY KEY(order_id),
    foreign key(user_id) REFERENCES users(user_id)
    );
    
CREATE TABLE bookorder(
	bookorder_id bigint NOT NULL auto_increment,
	order_id bigint NOT NULL,
    isbn bigint NOT NULL,
    amount_paid_pretax DECIMAL(15,2) NOT NULL,
    HST_TAX DECIMAL(8,6) DEFAULT NULL CHECK (HST_TAX > 0),
    GST_TAX DECIMAL(8,6) DEFAULT NULL CHECK (GST_TAX > 0),
    PST_TAX DECIMAL(8,6) DEFAULT NULL CHECK (PST_TAX > 0),
    enabled BOOLEAN default true,

    PRIMARY KEY(bookorder_id),
    foreign key(order_id) REFERENCES orders(order_id),
    foreign key(isbn) REFERENCES books(isbn),
    
	constraint bookorder_taxes_are_not_null check (HST_TAX IS NOT NULL OR GST_TAX IS NOT NULL),
    constraint bookorder_both_taxes_not_there check (HST_TAX IS NULL OR (GST_TAX IS NULL AND PST_TAX IS NULL))
	);

-- Jeff

CREATE TABLE ads(
	id bigint NOT NULL auto_increment PRIMARY KEY,
    file_location varchar(200),
    url varchar(2048) NOT NULL,
    enabled boolean DEFAULT true,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- PST can be null even if there is a GST
CREATE TABLE taxes(
    province char(2) NOT NULL PRIMARY KEY,
    HST_percentage decimal(8,6) CHECK (HST_percentage > 0),
    GST_percentage decimal(8,6) CHECK (GST_percentage > 0),
    PST_percentage decimal(8,6) CHECK (PST_percentage > 0),
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
	constraint taxes_are_not_null check (HST_percentage IS NOT NULL OR GST_percentage IS NOT NULL),
    constraint both_taxes_not_there check (HST_percentage IS NULL OR (GST_percentage IS NULL AND PST_percentage IS NULL))
);

CREATE TABLE survey_questions(
	id bigint NOT NULL auto_increment PRIMARY KEY,
    question varchar(1024),
    enabled boolean DEFAULT true,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE survey_responses(
	id bigint NOT NULL auto_increment PRIMARY KEY,
    survey_question_id bigint NOT NULL, 
    response varchar(1024),
    enabled boolean DEFAULT true,
    count int DEFAULT 0 CHECK (count > 0),
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (survey_question_id) REFERENCES survey_questions(id) ON DELETE CASCADE
);

CREATE TABLE rss_feeds(
	id bigint NOT NULL auto_increment PRIMARY KEY,
    url varchar(2048),
    enabled boolean DEFAULT true,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Seeding 
DELETE FROM bookorder;
DELETE FROM orders;
DELETE FROM bookgenre;
DELETE FROM bookauthor;
DELETE FROM bookpublisher;
DELETE FROM authors;
DELETE FROM genres;
DELETE FROM publishers;
DELETE FROM reviews;
DELETE FROM book_files;
DELETE FROM file_formats;
DELETE FROM books;
DELETE FROM ads;
DELETE FROM taxes;
DELETE FROM survey_questions;
DELETE FROM survey_responses;
DELETE FROM rss_feeds;
DELETE FROM users;

insert into genres(genre_id, genre) VALUES(1, "Fantasy");
insert into genres(genre_id, genre) VALUES(2, "Science Fiction");
insert into genres(genre_id, genre) VALUES(3, "Manga");

ALTER TABLE genres AUTO_INCREMENT=4;

insert into publishers(publisher_id, name) VALUES(1, "William Morrow");
insert into publishers(publisher_id, name) VALUES(2, "Tor Books");
insert into publishers(publisher_id, name) VALUES(3, "Scholastic Inc");
ALTER TABLE publishers AUTO_INCREMENT=4;

insert into authors(author_id, first_name, last_name) VALUES(1, "Terry", "Pratchett");
insert into authors(author_id, first_name, last_name) VALUES(2, "Neil", "Gaiman");
insert into authors(author_id, first_name, last_name) VALUES(3, "Brandon", "Sanderson");
ALTER TABLE authors AUTO_INCREMENT=4;

insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price, sale_price) VALUES(111111, 'Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch', STR_TO_DATE('November 28 2006', '%M %d %Y'), 491, '‘Armageddon only happens once, you know. They don’t let you go around again until you get it right.’People have been predicting the end of the world almost from its very beginning, so it’s only natural to be sceptical when a new date is set for Judgement Day. But what if, for once, the predictions are right, and the apocalypse really is due to arrive next Saturday, just after tea?You could spend the time left drowning your sorrows, giving away all your possessions in preparation for the rapture, or laughing it off as (hopefully) just another hoax. Or you could just try to do something about it.It’s a predicament that Aziraphale, a somewhat fussy angel, and Crowley, a fast-living demon now finds themselves in. They’ve been living amongst Earth’s mortals since The Beginning and, truth be told, have grown rather fond of the lifestyle and, in all honesty, are not actually looking forward to the coming Apocalypse.And then there’s the small matter that someone appears to have misplaced the A', 'Good_Omens_The_Nice_and_Accurate_Prophecies_of_Agnes_Nutter_Witch.jpg', 2, 17, 0);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price, sale_price) VALUES(222222, 'The Way of Kings', STR_TO_DATE('August 31 2010', '%M %d %Y'), 1007, 'I long for the days before the Last Desolation.The age before the Heralds abandoned us and the Knights Radiant turned against us. A time when there was still magic in the world and honor in the hearts of men.The world became ours, and yet we lost it. Victory proved to be the greatest test of all. Or was that victory illusory? Did our enemies come to recognize that the harder they fought, the fiercer our resistance? Fire and hammer will forge steel into a weapon, but if you abandon your sword, it eventually rusts away.There are four whom we watch. The first is the surgeon, forced to forsake healing to fight in the most brutal war of our time. The second is the assassin, a murderer who weeps as he kills. The third is the liar, a young woman who wears a scholar&apos;s mantle over the heart of a thief. The last is the prince, a warlord whose eyes have opened to the ancient past as his thirst for battle wanes.The world can change. Surgebinding and Shardwielding can return; the magics of anc', 'The_Way_of_Kings.jpg', 1, 17, 0);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price, sale_price) VALUES(333333, 'Words of Radiance', STR_TO_DATE('March 4 2014', '%M %d %Y'), 1087, 'From #1 New York Times bestselling author Brandon Sanderson, Words of Radiance, Book Two of the Stormlight Archive, continues the immersive fantasy epic that The Way of Kings began.Expected by his enemies to die the miserable death of a military slave, Kaladin survived to be given command of the royal bodyguards, a controversial first for a low-status "darkeyes." Now he must protect the king and Dalinar from every common peril as well as the distinctly uncommon threat of the Assassin, all while secretly struggling to master remarkable new powers that are somehow linked to his honorspren, Syl.The Assassin, Szeth, is active again, murdering rulers all over the world of Roshar, using his baffling powers to thwart every bodyguard and elude all pursuers. Among his prime targets is Highprince Dalinar, widely considered the power behind the Alethi throne. His leading role in the war would seem reason enough, but the Assassin\&apos;s master has much deeper motives.Brilliant but troubled Shalla', 'Words_of_Radiance.jpg', 3, 17, 12);

insert into bookgenre(genre_id, isbn) VALUES(1, 111111);
insert into bookgenre(genre_id, isbn) VALUES(1, 111111);
insert into bookgenre(genre_id, isbn) VALUES(2, 222222);
insert into bookgenre(genre_id, isbn) VALUES(2, 222222);
insert into bookgenre(genre_id, isbn) VALUES(3, 333333);

insert into bookauthor(author_id, isbn) VALUES(1, 111111);
insert into bookauthor(author_id, isbn) VALUES(2, 222222);
insert into bookauthor(author_id, isbn) VALUES(3, 333333);

insert into bookpublisher(publisher_id, isbn) VALUES(1, 9780000000000);
insert into bookpublisher(publisher_id, isbn) VALUES(2, 9780765326355);
insert into bookpublisher(publisher_id, isbn) VALUES(2, 9780765326362);
insert into bookpublisher(publisher_id, isbn) VALUES(2, 9780765326379);

INSERT INTO users(user_id,title,first_name,last_name,company_name,address_1,address_2,city,province,country,postal_code,home_phone,cell_phone,email, password, is_manager) VALUES( '1', 'Mr', 'John', 'Doe', 'Invera', '123 road avenue', 'second address', 'montreal', 'QC', 'Canada', 'h1h1h1', '0000000000', '1111111111', 'cst.send@gmail.com', 'dawsoncollege', false);
INSERT INTO users(user_id,title,first_name,last_name,company_name,address_1,address_2,city,province,country,postal_code,home_phone,cell_phone,email, password, is_manager) VALUES( '2', 'Mrs', 'Jane', 'Doe', 'Bookify', '456 avenue boulevard', '', 'toronto', 'TO', 'Canada', 'h2h2h2', '3333333333', '4444444444', 'cst.receive@gmail.com', 'collegedawson', true);
ALTER TABLE users AUTO_INCREMENT=3;

insert into orders(order_id, user_id, billing_address) VALUES( 1, 1, '123 road avenue');
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780345476876, 15, null, 5, 6, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9781421505855, 18, null, 5, 9.975, true );

insert into orders(order_id, user_id, billing_address) VALUES( 2, 1, '123 road avenue');
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9780061007224, 14, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9780553804690, 11, null, 5, 6, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9780316485616, 20, 13, null, null, true );

insert into orders(order_id, user_id, billing_address) VALUES( 3, 1, '123 road avenue');
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 3, 9781427814036, 19, null, 5, 6, false );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 3, 9780000000009, 10, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 3, 9780765326355, 17, null, 5, 6, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 3, 9780765377067, 18, null, 5, 6, true );

insert into orders(order_id, user_id, billing_address) VALUES( 4, 2, '456 avenue boulevard');
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 31, 9780545010221, 15, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 31, 9780345379337, 13, null, 5, 6, false );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 31, 9781591164418, 12, null, 5, 6, false );
insert into orders(order_id, user_id, billing_address) VALUES( 5, 2, '456 avenue boulevard');
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 32, 9780553803709, 18, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 32, 9780143039983, 15, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 32, 9781416524304, 17, null, 5, 6, false );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 32, 9780316251334, 18, null, 5, 6, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 32, 9784757518087, 15, null, 5, 6, true );
ALTER TABLE file_formats AUTO_INCREMENT=6;

insert into file_formats(file_format_id, format) VALUES(1, "epub");
insert into file_formats(file_format_id, format) VALUES(2, "pdf");
insert into file_formats(file_format_id, format) VALUES(3, "mobi");
ALTER TABLE file_formats AUTO_INCREMENT=4;

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 111111, './Good_Omens_The_Nice_and_Accurate_Prophecies_of_Agnes_Nutter_Witch.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 111111, './Good_Omens_The_Nice_and_Accurate_Prophecies_of_Agnes_Nutter_Witch.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 111111, './Good_Omens_The_Nice_and_Accurate_Prophecies_of_Agnes_Nutter_Witch.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 222222, './The_Way_of_Kings.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 222222, './The_Way_of_Kings.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 333333, './The_Way_of_Kings.pdf');

insert into taxes(province, HST_percentage, GST_percentage, PST_percentage) VALUES( 'AB', null, 0.05, null );
insert into taxes(province, HST_percentage, GST_percentage, PST_percentage) VALUES( 'BC', null, 0.05, 0.07 );
insert into taxes(province, HST_percentage, GST_percentage, PST_percentage) VALUES( 'MA', null, 0.05, 7 );
insert into taxes(province, HST_percentage, GST_percentage, PST_percentage) VALUES( 'NB', 0.15, null, null );
insert into taxes(province, HST_percentage, GST_percentage, PST_percentage) VALUES( 'NL', 15, null, null );
insert into taxes(province, HST_percentage, GST_percentage, PST_percentage) VALUES( 'NT', null, 0.05, null );
insert into taxes(province, HST_percentage, GST_percentage, PST_percentage) VALUES( 'NS', 0.15, null, null );
insert into taxes(province, HST_percentage, GST_percentage, PST_percentage) VALUES( 'NU', null, 0.05, null );
insert into taxes(province, HST_percentage, GST_percentage, PST_percentage) VALUES( 'ON', 0.13, null, null );
insert into taxes(province, HST_percentage, GST_percentage, PST_percentage) VALUES( 'PE', 0.15, null, null );
insert into taxes(province, HST_percentage, GST_percentage, PST_percentage) VALUES( 'QC', null, 0.05, 0.09975 );
insert into taxes(province, HST_percentage, GST_percentage, PST_percentage) VALUES( 'SK', null, 0.05, 0.06 );
insert into taxes(province, HST_percentage, GST_percentage, PST_percentage) VALUES( 'YT', null, 0.05, null );


-- Ads 
INSERT INTO ads(file_location, url) VALUES('christmas_ad.png', 'https://google.ca');

-- Survey Questions
INSERT INTO survey_questions(id, question, enabled) VALUES (1,'What is your favorite colour?', true); 
ALTER TABLE survey_questions AUTO_INCREMENT=2;

-- Survey Responses 
INSERT INTO survey_responses(survey_question_id, response, count) VALUES (1, 'Blue', 5); 
INSERT INTO survey_responses(survey_question_id, response, count) VALUES (1, 'Green', 500);
INSERT INTO survey_responses(survey_question_id, response, count) VALUES (1, 'Yellow', 1);


