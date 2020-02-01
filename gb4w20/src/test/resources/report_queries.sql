--Jasmar's queries

--Total Sales
/*Within the defined date range this report displays either the total sales for all items*/
SELECT SUM(bo.amount_paid_pretax) AS total_sales FROM bookstore.bookorder bo
JOIN bookstore.orders o ON bo.order_id = o.order_id 
--Enter desired timestamps here
WHERE o.timestamp BETWEEN ‘2020-01-27 00:00:01’ AND ‘2020-01-31 00:00:01’;

--Sales by Client
/* For a chosen client and within the defined date range this report displays the total sales */
SELECT SUM(bo.amout_paid_pretax) AS total_sales FROM bookstore.bookorder bo  JOIN 
bookstore.orders o ON bo.order_id = o.order_id JOIN bookstore.users u ON o.user_id = u.user_id
--Enter desired timestamps and client here
WHERE u.user_id = 1 AND o.timestamp BETWEEN ‘2020-01-27 00:00:01’ AND ‘2020-01-31 00:00:01’ GROUP BY u.user_id;

--Sales by Author
/*For a chosen author and within the defined date range this report displays the total sales for all items*/
SELECT SUM(bo.amout_paid_pretax) AS total_sales FROM bookstore.bookorder bo 
JOIN bookstore.orders o ON bo.order_id = o.order_id JOIN bookstore.books b
ON bo.isbn = b.isbn JOIN bookstore.bookauthor ba ON b.isbn = ba.isbn JOIN bookstore.authors a ON 
ba.author_id = a.author_id
--Enter desired timestamps and author here
WHERE a.author_id = 1 AND o.timestamp BETWEEN ‘2020-01-27 00:00:01’ AND ‘2020-01-31 00:00:01’ GROUP BY a.author_id;

--Sales by Publisher
/*For a chosen publisher and within the defined date range this report displays either the total sales*/
SELECT SUM(bo.amout_paid_pretax) AS total_sales FROM bookstore.bookorder bo 
JOIN bookstore.orders o ON bo.order_id = o.order_id JOIN bookstore.books b
ON bo.isbn = b.isbn JOIN bookstore.bookpublisher bp ON b.isbn = bp.isbn JOIN bookstore.publishers p ON 
bp.publisher_id = p.publisher_id
--Enter desired timestamps and author here
WHERE p.publisher_id = 1 AND o.timestamp BETWEEN ‘2020-01-27 00:00:01’ AND ‘2020-01-31 00:00:01’ GROUP BY p.publisher_id;

--Top Sellers
/*Within the defined date range this report displays the inventory in order of total sales, items that have never been sold are not included*/
SELECT b.title, SUM(bo.amount_paid_pretax) AS total_sales FROM bookstore.books b JOIN bookstore.bookorder bo ON 
b.isbn = bo.isbn JOIN bookstore.orders o ON bo.order_id = o.order_id 
--Enter timestamp here
WHERE o.timestamp BETWEEN ‘2020-01-27 00:00:01’ AND ‘2020-01-31 00:00:01’
GROUP BY bo.isbn ORDER BY total_sales DESC;


--Jeff's queries
/*
    Top clients in the defined date range ordered of value of total sales.
    Clients who never made a purchase are not included. 
    @author: Jeffrey Boisvert
*/
SELECT ord.user_id, u.first_name, u.last_name, SUM(bo.amount_paid_pretax) AS total_sales
FROM bookstore.orders ord
JOIN bookstore.users u ON ord.user_id = u.user_id
JOIN bookstore.bookorder bo ON bo.bookorder_id = ord.order_id  
-- Enter desired timestamps here
WHERE ord.timestamp BETWEEN '2020-01-27 00:00:01' AND '2020-01-31 00:00:01'
GROUP BY ord.user_id, u.first_name, u.last_name
ORDER BY total_sales DESC;

/*
    All the books that were never sold. 
    @author: Jeffrey Boisvert
*/
SELECT book.*
FROM bookstore.books book
WHERE book.isbn NOT IN (
    SELECT DISTINCT sold_book.isbn 
    FROM bookstore.books sold_book
    JOIN bookstore.bookorder bo ON bo.isbn = sold_book.isbn
    JOIN bookstore.orders ord ON ord.order_id = bo.order_id
    -- Enter desired timestamps here
    WHERE ord.timestamp BETWEEN '2020-01-27 00:00:01' AND '2020-01-31 00:00:01'
);

/*
    Current state of the inventory that shows
    every book in inventory with current wholesale, list and sale figures. 
    @author: Jeffrey Boisvert
*/
SELECT book.isbn, book.title, book.wholesale_price, book.list_price, sale_price
FROM bookstore.books book
WHERE book.active != 0
ORDER BY book.title ASC;

/*
    The results of a given survey sorted by 
    the most popular responses (highest to lowest). 
    @author: Jeffrey Boisvert
*/
SELECT sq.question, sr.response, sr.count
FROM bookstore.survey_questions sq
JOIN bookstore.survey_responses sr ON sq.id = sr.survey_question_id
-- Replace with the id of the wanted question
WHERE sq.id = 1
AND sr.enabled != 0
ORDER BY sr.count DESC;

