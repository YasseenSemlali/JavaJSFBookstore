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
ALTER TABLE genres AUTO_INCREMENT=3;

insert into publishers(publisher_id, name) VALUES(1, "William Morrow");
insert into publishers(publisher_id, name) VALUES(2, "Arthur A. Levine Books / Scholastic Inc.");
insert into publishers(publisher_id, name) VALUES(3, "Orbit");
insert into publishers(publisher_id, name) VALUES(4, "Del Rey");
insert into publishers(publisher_id, name) VALUES(5, "Tor Books");
insert into publishers(publisher_id, name) VALUES(6, "Hyperion");
ALTER TABLE publishers AUTO_INCREMENT=7;

insert into authors(author_id, first_name, last_name) VALUES(1, "Terry", "Pratchett");
insert into authors(author_id, first_name, last_name) VALUES(2, "Neil", "Gaiman");
insert into authors(author_id, first_name, last_name) VALUES(3, "J.K.", "Rowling");
insert into authors(author_id, first_name, last_name) VALUES(4, "Mary", "GrandPré");
insert into authors(author_id, first_name, last_name) VALUES(5, "Brent", "Weeks");
insert into authors(author_id, first_name, last_name) VALUES(6, "Scott", "Lynch");
insert into authors(author_id, first_name, last_name) VALUES(7, "Liu", "Cixin");
insert into authors(author_id, first_name, last_name) VALUES(8, "Douglas", "Adams");
insert into authors(author_id, first_name, last_name) VALUES(9, "Eoin", "Colfer");
ALTER TABLE authors AUTO_INCREMENT=10;

insert into books(isbn, title, date_of_publication, pages, cover, wholesale_price, list_price, sale_price, active, synopsis) VALUES(9780000000000, 'Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch', STR_TO_DATE('November 28 2006', '%M %d %Y'), 491, 'Good_Omens_The_Nice_and_Accurate_Prophecies_of_Agnes_Nutter_Witch.jpg', 2, 17, 0, true, '''Armageddon only happens once, you know. They don''t let you go around again until you get it right.''People have been predicting the end of the world almost from its very beginning, so it''s only natural to be sceptical when a new date is set for Judgement Day. But what if, for once, the predictions are right, and the apocalypse really is due to arrive next Saturday, just after tea?You could spend the time left drowning your sorrows, giving away all your possessions in preparation for the rapture, or laughing it off as (hopefully) just another hoax. Or you could just try to do something about it.It''s a predicament that Aziraphale, a somewhat fussy angel, and Crowley, a fast-living demon now finds themselves in. They''ve been living amongst Earth''s mortals since The Beginning and, truth be told, have grown rather fond of the lifestyle and, in all honesty, are not actually looking forward to the coming Apocalypse.And then there''s the small matter that someone appears to have misplaced the A');
insert into books(isbn, title, date_of_publication, pages, cover, wholesale_price, list_price, sale_price, active, synopsis) VALUES(9780439064866, 'Harry Potter and the Chamber of Secrets', STR_TO_DATE('June 2 1999', '%M %d %Y'), 341, 'Harry_Potter_and_the_Chamber_of_Secrets.jpg', 3, 13, 1, true, 'The Dursleys were so mean and hideous that summer that all Harry Potter wanted was to get back to the Hogwarts School for Witchcraft and Wizardry. But just as he''s packing his bags, Harry receives a warning from a strange, impish creature named Dobby who says that if Harry Potter returns to Hogwarts, disaster will strikeAnd strike it does. For in Harry''s second year at Hogwarts, fresh torments and horrors arise, including an outrageously stuck-up new professor, Gilderoy Lockhart, a spirit named Moaning Myrtle who haunts the girls'' bathroom, and the unwanted attentions of Ron Weasley''s younger sister, Ginny.But each of these seem minor annoyances when the real trouble begins, and someone -- or something -- starts turning Hogwarts students to stone. Could it be Draco Malfoy, a more poisonous rival than ever? Could it possibly be Hagrid, whose mysterious past is finally told? Or could it be the one everyone at Hogwarts most suspects . . . Harry Potter himself?');
insert into books(isbn, title, date_of_publication, pages, cover, wholesale_price, list_price, sale_price, active, synopsis) VALUES(9780545010221, 'Harry Potter and the Deathly Hallows', STR_TO_DATE('July 21 2007', '%M %d %Y'), 759, 'Harry_Potter_and_the_Deathly_Hallows.jpg', 3, 15, 0, true, 'Harry Potter is leaving Privet Drive for the last time. But as he climbs into the sidecar of Hagrid''s motorbike and they take to the skies, he knows Lord Voldemort and the Death Eaters will not be far behind.The protective charm that has kept him safe until now is broken. But the Dark Lord is breathing fear into everything he loves. And he knows he can''t keep hiding.To stop Voldemort, Harry knows he must find the remaining Horcruxes and destroy them.He will have to face his enemy in one final battle.--jkrowling.com');
insert into books(isbn, title, date_of_publication, pages, cover, wholesale_price, list_price, sale_price, active, synopsis) VALUES(9780316251303, 'The Burning White', STR_TO_DATE('October 22 2019', '%M %d %Y'), 992, 'The_Burning_White.jpg', 1, 16, 7, true, 'The nail-biting conclusion to the Lightbringer series! Stripped of both magical and political power, the people he once ruled told he''s dead, and now imprisoned in his own magical dungeon, former Emperor Gavin Guile has no prospect of escape. But the world faces a calamity greater than the Seven Satrapies has ever seen... and only he can save it.As the armies of the White King defeat the Chromeria and old gods are born anew, the fate of worlds will come down to one question: Who is the Lightbringer?');
insert into books(isbn, title, date_of_publication, pages, cover, wholesale_price, list_price, sale_price, active, synopsis) VALUES(9780000000006, 'Red Seas Under Red Skies', STR_TO_DATE('July 31 2007', '%M %d %Y'), 578, 'Red_Seas_Under_Red_Skies.jpg', 1, 12, 0, true, 'After a brutal battle with the underworld that nearly destroyed him, Locke Lamora and his trusted sidekick, Jean, fled the island city of their birth and landed on the exotic shores of Tal Verrar to nurse their wounds. But even at this westernmost edge of civilization, they can''t rest for long---and they are soon back doing what they do best: stealing from the undeserving rich and pocketing the proceeds for themselves.This time, however, they have targeted the grandest prize of all: the Sinspire, the most exclusive and heavily guarded gambling house in the world. Its nine floors attract the wealthiest clientele - and to rise to the top, one must impress with good credit, amusing behavior...and excruciatingly impeccable play. For there is one cardinal rule, enforced by Requin, the house''s cold-blooded master: it is death to cheat at any game at the Sinspire.Brazenly undeterred, Locke and Jean have orchestrated an elaborate plan to lie, trick, and swindle their way up the nine floors...s');
insert into books(isbn, title, date_of_publication, pages, cover, wholesale_price, list_price, sale_price, active, synopsis) VALUES(9780765377067, 'The Three-Body Problem', STR_TO_DATE('November 11 2014', '%M %d %Y'), 399, 'The_ThreeBody_Problem.jpg', 3, 18, 3, true, 'The Three-Body Problem is the first chance for English-speaking readers to experience the Hugo Award-winning phenomenon from China''s most beloved science fiction author, Liu Cixin.Set against the backdrop of China''s Cultural Revolution, a secret military project sends signals into space to establish contact with aliens. An alien civilization on the brink of destruction captures the signal and plans to invade Earth. Meanwhile, on Earth, different camps start forming, planning to either welcome the superior beings and help them take over a world seen as corrupt, or to fight against the invasion. The result is a science fiction masterpiece of enormous scope and vision.');
insert into books(isbn, title, date_of_publication, pages, cover, wholesale_price, list_price, sale_price, active, synopsis) VALUES(9780000000010, 'The Hitchhiker''s Guide to the Galaxy', STR_TO_DATE('June 23 2007', '%M %d %Y'), 193, 'The_Hitchhikers_Guide_to_the_Galaxy.jpg', 3, 20, 0, true, 'Seconds before the Earth is demolished to make way for a galactic freeway, Arthur Dent is plucked off the planet by his friend Ford Prefect, a researcher for the revised edition of The Hitchhiker''s Guide to the Galaxy who, for the last fifteen years, has been posing as an out-of-work actor.Together this dynamic pair begin a journey through space aided by quotes from The Hitchhiker''s Guide ("A towel is about the most massively useful thing an interstellar hitchhiker can have") and a galaxy-full of fellow travelers: Zaphod Beeblebrox—the two-headed, three-armed ex-hippie and totally out-to-lunch president of the galaxy; Trillian, Zaphod''s girlfriend (formally Tricia McMillan), whom Arthur tried to pick up at a cocktail party once upon a time zone; Marvin, a paranoid, brilliant, and chronically depressed robot; Veet Voojagig, a former graduate student who is obsessed with the disappearance of all the ballpoint pens he bought over the years.');
insert into books(isbn, title, date_of_publication, pages, cover, wholesale_price, list_price, sale_price, active, synopsis) VALUES(9781401323585, 'And Another Thing...', STR_TO_DATE('October 12 2009', '%M %d %Y'), 275, 'And_Another_Thing.jpg', 3, 10, 3, true, 'AN ENGLISHMAN''S CONTINUING SEARCH THROUGH SPACE AND TIME FOR A DECENT CUP OF TEA...Arthur Dent''s accidental association with that wholly remarkable book The Hitchhiker''s Guide to the Galaxy has not been entirely without incident.Arthur has traveled the length, breadth, and depth of known, and unknown, space. He has stumbled forward and backward through time. He has been blown up, reassembled, cruelly imprisoned, horribly released, and colorfully insulted more than is strictly necessary. And of course Arthur Dent has comprehensively failed to grasp the meaning of life, the universe, and everything.Arthur has finally made it home to Earth, but that does not mean he has escaped his fate.Arthur''s chances of getting his hands on a decent cuppa have evaporated rapidly, along with all the world''s oceans. For no sooner has he touched down on the planet Earth than he finds out that it is about to be blown up...again.And Another Thing...is the rather unexpected, but very welcome, sixth installme');

insert into bookgenre(genre_id, isbn) VALUES(1, 9780000000000);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780439064866);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780545010221);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780316251303);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780000000006);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780765377067);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780000000010);
insert into bookgenre(genre_id, isbn) VALUES(2, 9781401323585);

insert into bookauthor(author_id, isbn) VALUES(1, 9780000000000);
insert into bookauthor(author_id, isbn) VALUES(2, 9780000000000);
insert into bookauthor(author_id, isbn) VALUES(3, 9780439064866);
insert into bookauthor(author_id, isbn) VALUES(4, 9780439064866);
insert into bookauthor(author_id, isbn) VALUES(3, 9780545010221);
insert into bookauthor(author_id, isbn) VALUES(1, 9780545010221);
insert into bookauthor(author_id, isbn) VALUES(5, 9780316251303);
insert into bookauthor(author_id, isbn) VALUES(6, 9780000000006);
insert into bookauthor(author_id, isbn) VALUES(7, 9780765377067);
insert into bookauthor(author_id, isbn) VALUES(8, 9780000000010);
insert into bookauthor(author_id, isbn) VALUES(9, 9781401323585);

insert into bookpublisher(publisher_id, isbn) VALUES(1, 9780000000000);
insert into bookpublisher(publisher_id, isbn) VALUES(2, 9780439064866);
insert into bookpublisher(publisher_id, isbn) VALUES(2, 9780545010221);
insert into bookpublisher(publisher_id, isbn) VALUES(3, 9780316251303);
insert into bookpublisher(publisher_id, isbn) VALUES(4, 9780000000006);
insert into bookpublisher(publisher_id, isbn) VALUES(5, 9780765377067);
insert into bookpublisher(publisher_id, isbn) VALUES(4, 9780000000010);
insert into bookpublisher(publisher_id, isbn) VALUES(6, 9781401323585);

INSERT INTO users(user_id,title,first_name,last_name,company_name,address_1,address_2,city,province,country,postal_code,home_phone,cell_phone,email, password, is_manager) VALUES( '1', 'Mr', 'John', 'Doe', 'Invera', '123 road avenue', 'second address', 'montreal', 'QC', 'Canada', 'h1h1h1', '0000000000', '1111111111', 'cst.send@gmail.com', 'dawsoncollege', false);
INSERT INTO users(user_id,title,first_name,last_name,company_name,address_1,address_2,city,province,country,postal_code,home_phone,cell_phone,email, password, is_manager) VALUES( '2', 'Mrs', 'Jane', 'Doe', 'Bookify', '456 avenue boulevard', '', 'toronto', 'TO', 'Canada', 'h2h2h2', '3333333333', '4444444444', 'cst.receive@gmail.com', 'collegedawson', true);
ALTER TABLE users AUTO_INCREMENT=3;

insert into orders(order_id, user_id, billing_address, timestamp) VALUES( 1, 1, '123 road avenue', '2020-01-31 19:39:52');
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780000000000, 17, null, 5, 9.975, false );
insert into orders(order_id, user_id, billing_address, timestamp) VALUES( 2, 1, '123 road avenue', '2020-02-22 13:34:57');
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9780765377067, 18, null, 5, 9.975, true );
insert into orders(order_id, user_id, billing_address, timestamp) VALUES( 3, 1, '123 road avenue', '2020-02-10 23:36:10');
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 3, 9780316251303, 16, 13, null, null, false );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 3, 9780545010221, 15, 13, null, null, true );
insert into orders(order_id, user_id, billing_address, timestamp) VALUES( 4, 1, '123 road avenue', '2020-02-18 03:19:34');
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 4, 9780439064866, 13, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 4, 9780000000006, 12, null, 5, 6, true );

insert into orders(order_id, user_id, billing_address, timestamp) VALUES( 5, 2, '456 avenue boulevard', '2020-02-18 14:56:52');
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 5, 9781401323585, 10, null, 5, 6, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 5, 9780545010221, 15, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 5, 9780000000000, 17, null, 5, 6, true );
insert into orders(order_id, user_id, billing_address, timestamp) VALUES( 6, 2, '456 avenue boulevard', '2020-02-18 11:35:52');
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 6, 9780316251303, 16, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 6, 9780765377067, 18, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 6, 9780000000006, 12, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 6, 9780439064866, 13, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 6, 9780000000010, 20, null, 5, 6, true );

insert into file_formats(file_format_id, format) VALUES(1, "epub");
insert into file_formats(file_format_id, format) VALUES(2, "pdf");
insert into file_formats(file_format_id, format) VALUES(3, "mobi");
ALTER TABLE file_formats AUTO_INCREMENT=4;

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780000000000, 'Good_Omens_The_Nice_and_Accurate_Prophecies_of_Agnes_Nutter_Witch.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780000000000, 'Good_Omens_The_Nice_and_Accurate_Prophecies_of_Agnes_Nutter_Witch.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780439064866, 'Harry_Potter_and_the_Chamber_of_Secrets.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780439064866, 'Harry_Potter_and_the_Chamber_of_Secrets.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780439064866, 'Harry_Potter_and_the_Chamber_of_Secrets.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780545010221, 'Harry_Potter_and_the_Deathly_Hallows.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780545010221, 'Harry_Potter_and_the_Deathly_Hallows.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780545010221, 'Harry_Potter_and_the_Deathly_Hallows.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780316251303, 'The_Burning_White.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780316251303, 'The_Burning_White.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780316251303, 'The_Burning_White.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000006, 'Red_Seas_Under_Red_Skies.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780765377067, 'The_ThreeBody_Problem.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780765377067, 'The_ThreeBody_Problem.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780765377067, 'The_ThreeBody_Problem.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780000000010, 'The_Hitchhikers_Guide_to_the_Galaxy.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000010, 'The_Hitchhikers_Guide_to_the_Galaxy.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780000000010, 'The_Hitchhikers_Guide_to_the_Galaxy.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781401323585, 'And_Another_Thing.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781401323585, 'And_Another_Thing.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781401323585, 'And_Another_Thing.epub');

insert into reviews(user_id, isbn, rating, review, approved_status) VALUES( 1, 9781401323585, 4, 'And Another Thing...: test review by John' , true );

insert into reviews(user_id, isbn, rating, review, approved_status) VALUES( 2, 9780000000000, 2, 'Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch: test review by Jane' , true );


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
INSERT INTO ads(file_location, url) VALUES('school_ad.png', 'https://www.dawsoncollege.qc.ca/');
INSERT INTO ads(file_location, url) VALUES('summer_ad.png', 'https://www.dawsoncollege.qc.ca/credit/summer/');


-- Survey Questions
INSERT INTO survey_questions(id, question, enabled) VALUES (1,'What is your favorite colour?', true); 
INSERT INTO survey_questions(id, question, enabled) VALUES (2,'Do you prefer Moose or Deer?', false); 
INSERT INTO survey_questions(id, question, enabled) VALUES (3,'Do you e-books or physical books?', false); 
ALTER TABLE survey_questions AUTO_INCREMENT=4;
-- Survey Responses 
INSERT INTO survey_responses(survey_question_id, response, count) VALUES (1, 'Blue', 5); 
INSERT INTO survey_responses(survey_question_id, response, count) VALUES (1, 'Green', 500);
INSERT INTO survey_responses(survey_question_id, response, count) VALUES (1, 'Yellow', 1);

INSERT INTO survey_responses(survey_question_id, response, count) VALUES (2, 'Moose', 15);
INSERT INTO survey_responses(survey_question_id, response, count) VALUES (2, 'Deer', 12);
INSERT INTO survey_responses(survey_question_id, response, count) VALUES (2, 'Neither', 999);

INSERT INTO survey_responses(survey_question_id, response, count) VALUES (3, 'E-books', 500);
INSERT INTO survey_responses(survey_question_id, response, count) VALUES (3, 'Physical Books', 500);
INSERT INTO survey_responses(survey_question_id, response, count) VALUES (3, 'Neither', 250);  
INSERT INTO survey_responses(survey_question_id, response, count) VALUES (3, 'I do not read', 1024);

INSERT INTO rss_feeds(url, enabled) values ('https://www.cbc.ca/cmlink/rss-topstories', true);
INSERT INTO rss_feeds(url, enabled) values ('http://www.ctvnews.ca/rss/ctvnews-ca-top-stories-public-rss-1.822009', true);
INSERT INTO rss_feeds(url, enabled) values ('https://globalnews.ca/feed/', true);
