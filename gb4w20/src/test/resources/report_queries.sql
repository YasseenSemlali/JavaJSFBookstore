USE gb4w20;
-- Jasmar's queries

-- Total Sales
/*
Within the defined date range this report displays 
the total sales for all items
@author Jasmar Badion
*/
SELECT SUM(bo.amount_paid_pretax) AS total_sales 
FROM orders o
JOIN bookorder bo ON o.order_id = bo.order_id 
-- Enter desired timestamps here
WHERE o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01'; 

/*
Within the defined date range this report displays
a list of every item sold ordered by date in addition to the totals
@author Jasmar Badion
*/
SELECT bo.isbn, b.title, SUM(bo.amount_paid_pretax) AS total_sales 
FROM bookorder bo
JOIN orders o ON bo.order_id = o.order_id 
JOIN books b ON bo.isbn = b.isbn
-- Enter desired timestamps here
WHERE o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01'
GROUP BY bo.isbn, b.title 
ORDER BY o.timestamp DESC;

-- Sales by Client
/* 
For a chosen client and within the defined date range 
this report displays the total sales 
@author Jasmar Badion
*/
SELECT SUM(bo.amount_paid_pretax) AS total_sales 
FROM bookorder bo 
JOIN orders o ON bo.order_id = o.order_id 
JOIN users u ON o.user_id = u.user_id
-- Enter desired timestamps and client here
WHERE u.user_id = 1 AND o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-27 00:00:01' 
GROUP BY u.user_id;

/*
For a chosen client and within the defined date range 
this report displays a list of ordered by date of every item
purchased in addition of the totals
@author Jasmar Badion
*/
SELECT bo.isbn, b.title, SUM(bo.amount_paid_pretax) AS total_sales 
FROM bookorder bo 
JOIN orders o ON bo.order_id = o.order_id 
JOIN users u ON o.user_id = u.user_id
JOIN books b ON b.isbn = bo.isbn
-- Enter desired timestamps and client here
WHERE u.user_id = 1 AND o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01'
GROUP BY u.user_id, bo.isbn, b.title
ORDER BY o.timestamp DESC;

-- Sales by Author
/*
For a chosen author and within the defined date range 
this report displays the total sales for all items
@author Jasmar Badion
*/
SELECT SUM(bo.amount_paid_pretax) AS total_sales 
FROM bookorder bo 
JOIN orders o ON bo.order_id = o.order_id 
JOIN books b ON bo.isbn = b.isbn 
JOIN bookauthor ba ON b.isbn = ba.isbn 
JOIN authors a ON ba.author_id = a.author_id
-- Enter desired timestamps and author here
WHERE a.author_id = 4 AND o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01' 
GROUP BY a.author_id;

/*
For a chosen author and within the defined date range 
this report displays a list of every item sold in addition to the totals
@author Jasmar Badion
*/
SELECT bo.isbn, b.title, SUM(bo.amout_paid_pretax) AS total_sales 
FROM bookorder bo 
JOIN orders o ON bo.order_id = o.order_id 
JOIN books b ON bo.isbn = b.isbn 
JOIN bookauthor ba ON b.isbn = ba.isbn 
JOIN authors a ON ba.author_id = a.author_id
-- Enter desired timestamps and author here
WHERE a.author_id = 4 AND o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01'
GROUP BY a.author_id, bo.isbn, b.title;

-- Sales by Publisher
/*
For a chosen publisher and within the defined date range 
this report displays the total sales
@author Jasmar Badion
*/
SELECT SUM(bo.amount_paid_pretax) AS total_sales 
FROM bookorder bo 
JOIN orders o ON bo.order_id = o.order_id 
JOIN books b ON bo.isbn = b.isbn 
JOIN bookpublisher bp ON b.isbn = bp.isbn 
JOIN publishers p ON bp.publisher_id = p.publisher_id
-- Enter desired timestamps and publisher here
WHERE p.publisher_id = 1 AND o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01'
GROUP BY p.publisher_id;
/*
For a chosen publisher and within the defined date range 
this report displays a list ordered by date of every purchase 
in addition to the totals
@author Jasmar Badion
*/
SELECT bo.isbn, b.title, SUM(bo.amount_paid_pretax) AS total_sales 
FROM bookorder bo 
JOIN orders o ON bo.order_id = o.order_id 
JOIN books b ON bo.isbn = b.isbn 
JOIN bookpublisher bp ON b.isbn = bp.isbn 
JOIN publishers p ON bp.publisher_id = p.publisher_id
-- Enter desired timestamps and publisher here
WHERE p.publisher_id = 1 AND o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01'
GROUP BY p.publisher_id, bo.isbn, b.title
ORDER BY o.timestamp DESC;

-- Top Sellers
/*
Within the defined date range this report displays the inventory in order of total sales, 
items that have never been sold are not included
@author Jasmar Badion
*/
SELECT bo.isbn, b.title, SUM(bo.amount_paid_pretax) AS total_sales 
FROM books b JOIN bookorder bo ON b.isbn = bo.isbn 
JOIN orders o ON bo.order_id = o.order_id 
-- Enter timestamp here
WHERE o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01'
GROUP BY bo.isbn, b.title 
ORDER BY total_sales DESC;


-- Jeff's queries
/*
    Top clients in the defined date range ordered of value of total sales.
    Clients who never made a purchase are not included. 
    @author: Jeffrey Boisvert
*/
SELECT ord.user_id, u.first_name, u.last_name, SUM(bo.amount_paid_pretax) AS total_sales
FROM orders ord
JOIN users u ON ord.user_id = u.user_id
JOIN bookorder bo ON bo.bookorder_id = ord.order_id  
-- Enter desired timestamps here
WHERE ord.timestamp BETWEEN '2020-01-27 00:00:01' AND '2020-01-31 00:00:01'
GROUP BY ord.user_id, u.first_name, u.last_name
ORDER BY total_sales DESC;

/*
    All the books that were never sold. 
    @author: Jeffrey Boisvert
*/
SELECT book.*
FROM books book
WHERE book.isbn NOT IN (
    SELECT DISTINCT sold_book.isbn 
    FROM books sold_book
    JOIN bookorder bo ON bo.isbn = sold_book.isbn
    JOIN orders ord ON ord.order_id = bo.order_id
    -- Enter desired timestamps here
    WHERE ord.timestamp BETWEEN '2020-01-27 00:00:01' AND '2020-01-31 00:00:01'
);

/*
    Current state of the inventory that shows
    every book in inventory with current wholesale, list and sale figures. 
    @author: Jeffrey Boisvert
*/
SELECT book.isbn, book.title, book.wholesale_price, book.list_price, sale_price
FROM books book
WHERE book.active != 0
ORDER BY book.title ASC;

/*
    The results of a given survey sorted by 
    the most popular responses (highest to lowest). 
    @author: Jeffrey Boisvert
*/
SELECT sq.question, sr.response, sr.count
FROM survey_questions sq
JOIN survey_responses sr ON sq.id = sr.survey_question_id
-- Replace with the id of the wanted question
WHERE sq.id = 1
AND sr.enabled != 0
ORDER BY sr.count DESC;

-- Jasmar's queries

-- Total Sales
/*
Within the defined date range this report displays 
the total sales for all items
@author Jasmar Badion
*/
SELECT SUM(bo.amount_paid_pretax) AS total_sales 
FROM orders o
JOIN bookorder bo ON o.order_id = bo.order_id 
-- Enter desired timestamps here
WHERE o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01'; 

/*
Within the defined date range this report displays
a list of every item sold ordered by date in addition to the totals
@author Jasmar Badion
*/
SELECT bo.isbn, b.title, SUM(bo.amount_paid_pretax) AS total_sales 
FROM bookorder bo
JOIN orders o ON bo.order_id = o.order_id 
JOIN books b ON bo.isbn = b.isbn
-- Enter desired timestamps here
WHERE o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01'
GROUP BY bo.isbn, b.title 
ORDER BY o.timestamp DESC;

-- Sales by Client
/* 
For a chosen client and within the defined date range 
this report displays the total sales 
@author Jasmar Badion
*/
SELECT SUM(bo.amount_paid_pretax) AS total_sales 
FROM bookorder bo 
JOIN orders o ON bo.order_id = o.order_id 
JOIN users u ON o.user_id = u.user_id
-- Enter desired timestamps and client here
WHERE u.user_id = 1 AND o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01' 
GROUP BY u.user_id;

/*
For a chosen client and within the defined date range 
this report displays a list of ordered by date of every item
purchased in addition of the totals
@author Jasmar Badion
*/
SELECT bo.isbn, b.title, SUM(bo.amount_paid_pretax) AS total_sales 
FROM bookorder bo 
JOIN orders o ON bo.order_id = o.order_id 
JOIN users u ON o.user_id = u.user_id
JOIN books b ON b.isbn = bo.isbn
-- Enter desired timestamps and client here
WHERE u.user_id = 1 AND o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01'
GROUP BY u.user_id, bo.isbn, b.title
ORDER BY o.timestamp DESC;

-- Sales by Author
/*
For a chosen author and within the defined date range 
this report displays the total sales for all items
@author Jasmar Badion
*/
SELECT SUM(bo.amount_paid_pretax) AS total_sales 
FROM bookorder bo 
JOIN orders o ON bo.order_id = o.order_id 
JOIN books b ON bo.isbn = b.isbn 
JOIN bookauthor ba ON b.isbn = ba.isbn 
JOIN authors a ON ba.author_id = a.author_id
-- Enter desired timestamps and author here
WHERE a.author_id = 4 AND o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01' 
GROUP BY a.author_id;

/*
For a chosen author and within the defined date range 
this report displays a list of every item sold in addition to the totals
@author Jasmar Badion
*/
SELECT bo.isbn, b.title, SUM(bo.amout_paid_pretax) AS total_sales 
FROM bookorder bo 
JOIN orders o ON bo.order_id = o.order_id 
JOIN books b ON bo.isbn = b.isbn 
JOIN bookauthor ba ON b.isbn = ba.isbn 
JOIN authors a ON ba.author_id = a.author_id
-- Enter desired timestamps and author here
WHERE a.author_id = 4 AND o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01'
GROUP BY a.author_id, bo.isbn, b.title;

-- Sales by Publisher
/*
For a chosen publisher and within the defined date range 
this report displays the total sales
@author Jasmar Badion
*/
SELECT SUM(bo.amount_paid_pretax) AS total_sales 
FROM bookorder bo 
JOIN orders o ON bo.order_id = o.order_id 
JOIN books b ON bo.isbn = b.isbn 
JOIN bookpublisher bp ON b.isbn = bp.isbn 
JOIN publishers p ON bp.publisher_id = p.publisher_id
-- Enter desired timestamps and publisher here
WHERE p.publisher_id = 1 AND o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01'
GROUP BY p.publisher_id;
/*
For a chosen publisher and within the defined date range 
this report displays a list ordered by date of every purchase 
in addition to the totals
@author Jasmar Badion
*/
SELECT bo.isbn, b.title, SUM(bo.amount_paid_pretax) AS total_sales 
FROM bookorder bo 
JOIN orders o ON bo.order_id = o.order_id 
JOIN books b ON bo.isbn = b.isbn 
JOIN bookpublisher bp ON b.isbn = bp.isbn 
JOIN publishers p ON bp.publisher_id = p.publisher_id
-- Enter desired timestamps and publisher here
WHERE p.publisher_id = 1 AND o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01'
GROUP BY p.publisher_id, bo.isbn, b.title
ORDER BY o.timestamp DESC;

-- Top Sellers
/*
Within the defined date range this report displays the inventory in order of total sales, 
items that have never been sold are not included
@author Jasmar Badion
*/
SELECT bo.isbn, b.title, SUM(bo.amount_paid_pretax) AS total_sales 
FROM books b JOIN bookorder bo ON b.isbn = bo.isbn 
JOIN orders o ON bo.order_id = o.order_id 
-- Enter timestamp here
WHERE o.timestamp BETWEEN '2020-02-01 00:00:01' AND '2020-02-05 00:00:01'
GROUP BY bo.isbn, b.title 
ORDER BY total_sales DESC;


-- Jeff's queries
/*
    Top clients in the defined date range ordered of value of total sales.
    Clients who never made a purchase are not included. 
    @author: Jeffrey Boisvert
*/
SELECT ord.user_id, u.first_name, u.last_name, SUM(bo.amount_paid_pretax) AS total_sales
FROM orders ord
JOIN users u ON ord.user_id = u.user_id
JOIN bookorder bo ON bo.bookorder_id = ord.order_id  
-- Enter desired timestamps here
WHERE ord.timestamp BETWEEN '2020-01-27 00:00:01' AND '2020-01-31 00:00:01'
GROUP BY ord.user_id, u.first_name, u.last_name
ORDER BY total_sales DESC;

/*
    All the books that were never sold. 
    @author: Jeffrey Boisvert
*/
SELECT book.*
FROM books book
WHERE book.isbn NOT IN (
    SELECT DISTINCT sold_book.isbn 
    FROM books sold_book
    JOIN bookorder bo ON bo.isbn = sold_book.isbn
    JOIN orders ord ON ord.order_id = bo.order_id
    -- Enter desired timestamps here
    WHERE ord.timestamp BETWEEN '2020-01-27 00:00:01' AND '2020-01-31 00:00:01'
);

/*
    Current state of the inventory that shows
    every book in inventory with current wholesale, list and sale figures. 
    @author: Jeffrey Boisvert
*/
SELECT book.isbn, book.title, book.wholesale_price, book.list_price, sale_price
FROM books book
WHERE book.active != 0
ORDER BY book.title ASC;

/*
    The results of a given survey sorted by 
    the most popular responses (highest to lowest). 
    @author: Jeffrey Boisvert
*/
SELECT sq.question, sr.response, sr.count
FROM survey_questions sq
JOIN survey_responses sr ON sq.id = sr.survey_question_id
-- Replace with the id of the wanted question
WHERE sq.id = 1
AND sr.enabled != 0
ORDER BY sr.count DESC;

