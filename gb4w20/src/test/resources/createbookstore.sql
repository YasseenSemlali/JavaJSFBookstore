-- create database bookstore;

use gb4w20;
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
    enabled BOOLEAN NOT NULL default true,
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


