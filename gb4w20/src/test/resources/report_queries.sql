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

