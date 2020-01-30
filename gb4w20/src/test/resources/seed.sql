DELETE FROM bookorder;
DELETE FROM orders;
DELETE FROM bookgenre;
DELETE FROM bookauthor;
DELETE FROM authors;
DELETE FROM genres;
DELETE FROM reviews;
DELETE FROM book_files;
DELETE FROM file_formats;
DELETE FROM books;
DELETE FROM users;

insert into genres(genre_id, genre) VALUES(1, "Fantasy");
insert into genres(genre_id, genre) VALUES(2, "Science Fiction");
insert into genres(genre_id, genre) VALUES(3, "Manga");
insert into genres(genre_id, genre) VALUES(4, "Horror");
insert into genres(genre_id, genre) VALUES(5, "Mystery/Thriller");
ALTER TABLE genres AUTO_INCREMENT=6;

insert into authors(author_id, first_name, last_name) VALUES(1, "Terry", "Pratchett");
insert into authors(author_id, first_name, last_name) VALUES(2, "Neil", "Gaiman");
insert into authors(author_id, first_name, last_name) VALUES(3, "Brandon", "Sanderson");
insert into authors(author_id, first_name, last_name) VALUES(4, "J.K.", "Rowling");
insert into authors(author_id, first_name, last_name) VALUES(5, "Mary", "GrandPré");
insert into authors(author_id, first_name, last_name) VALUES(6, "Brent", "Weeks");
insert into authors(author_id, first_name, last_name) VALUES(7, "Patrick", "Rothfuss");
insert into authors(author_id, first_name, last_name) VALUES(8, "Samantha", "Shannon");
insert into authors(author_id, first_name, last_name) VALUES(9, "Scott", "Lynch");
insert into authors(author_id, first_name, last_name) VALUES(10, "Frank", "Herbert");
insert into authors(author_id, first_name, last_name) VALUES(11, "George", "Orwell");
insert into authors(author_id, first_name, last_name) VALUES(12, "Orson", "Scott Card");
insert into authors(author_id, first_name, last_name) VALUES(13, "Stefan", "Rudnicki");
insert into authors(author_id, first_name, last_name) VALUES(14, "Liu", "Cixin");
insert into authors(author_id, first_name, last_name) VALUES(15, "Aldous", "Huxley");
insert into authors(author_id, first_name, last_name) VALUES(16, "Ray", "Bradbury");
insert into authors(author_id, first_name, last_name) VALUES(17, "Douglas", "Adams");
insert into authors(author_id, first_name, last_name) VALUES(18, "Eoin", "Colfer");
insert into authors(author_id, first_name, last_name) VALUES(19, "Andy", "Weir");
insert into authors(author_id, first_name, last_name) VALUES(20, "Isaac", "Asimov");
insert into authors(author_id, first_name, last_name) VALUES(21, "Kurt", "Vonnegut Jr.");
insert into authors(author_id, first_name, last_name) VALUES(22, "Daniel", "Keyes");
insert into authors(author_id, first_name, last_name) VALUES(23, "Anthony", "Burgess");
insert into authors(author_id, first_name, last_name) VALUES(24, "Tsugumi", "Ohba");
insert into authors(author_id, first_name, last_name) VALUES(25, "Takeshi", "Obata");
insert into authors(author_id, first_name, last_name) VALUES(26, "Hiromu", "Arakawa");
insert into authors(author_id, first_name, last_name) VALUES(27, "Akira", "Watanabe");
insert into authors(author_id, first_name, last_name) VALUES(28, "Natsuki", "Takaya");
insert into authors(author_id, first_name, last_name) VALUES(29, "Alethea", "Nibley");
insert into authors(author_id, first_name, last_name) VALUES(30, "Bisco", "Hatori");
insert into authors(author_id, first_name, last_name) VALUES(31, "Matsuri", "Hino");
insert into authors(author_id, first_name, last_name) VALUES(32, "Tomo", "Kimura");
insert into authors(author_id, first_name, last_name) VALUES(33, "Tite", "Kubo");
insert into authors(author_id, first_name, last_name) VALUES(34, "Masashi", "Kishimoto");
insert into authors(author_id, first_name, last_name) VALUES(35, "Katy", "Bridges");
insert into authors(author_id, first_name, last_name) VALUES(36, "Yana", "Toboso");
insert into authors(author_id, first_name, last_name) VALUES(37, "Rumiko", "Takahashi");
insert into authors(author_id, first_name, last_name) VALUES(38, "Yoshiki", "Nakamura");
insert into authors(author_id, first_name, last_name) VALUES(39, "Naoko", "Takeuchi");
insert into authors(author_id, first_name, last_name) VALUES(40, "Eiichiro", "Oda");
insert into authors(author_id, first_name, last_name) VALUES(41, "Andy", "Nakatani");
insert into authors(author_id, first_name, last_name) VALUES(42, "Ai", "Yazawa");
insert into authors(author_id, first_name, last_name) VALUES(43, "Nobuhiro", "Watsuki");
insert into authors(author_id, first_name, last_name) VALUES(44, "Kenichiro", "Yagi");
insert into authors(author_id, first_name, last_name) VALUES(45, "CLAMP", null);
insert into authors(author_id, first_name, last_name) VALUES(46, "Anthony", "Gerard");
insert into authors(author_id, first_name, last_name) VALUES(47, "Maria", "Simpson");
insert into authors(author_id, first_name, last_name) VALUES(48, "William", "Flanagan");
insert into authors(author_id, first_name, last_name) VALUES(49, "Jun", "Mochizuki");
insert into authors(author_id, first_name, last_name) VALUES(50, "Hiro", "Mashima");
insert into authors(author_id, first_name, last_name) VALUES(51, "Karuho", "Shiina");
insert into authors(author_id, first_name, last_name) VALUES(52, "Kiyohiko", "Azuma");
insert into authors(author_id, first_name, last_name) VALUES(53, "あずま", "きよひこ");
insert into authors(author_id, first_name, last_name) VALUES(54, "Amy", "Forsyth");
insert into authors(author_id, first_name, last_name) VALUES(55, "Katsura", "Hoshino");
insert into authors(author_id, first_name, last_name) VALUES(56, "Mayumi", "Kobayashi");
insert into authors(author_id, first_name, last_name) VALUES(57, "Hajime", "Isayama");
insert into authors(author_id, first_name, last_name) VALUES(58, "Sheldon", "Drzka");
insert into authors(author_id, first_name, last_name) VALUES(59, "Hiro", "Fujiwara");
insert into authors(author_id, first_name, last_name) VALUES(60, "Stephen", "King");
insert into authors(author_id, first_name, last_name) VALUES(61, "Bram", "Stoker");
insert into authors(author_id, first_name, last_name) VALUES(62, "Nina", "Auerbach");
insert into authors(author_id, first_name, last_name) VALUES(63, "Shirley", "Jackson");
insert into authors(author_id, first_name, last_name) VALUES(64, "Laura", "  Miller");
insert into authors(author_id, first_name, last_name) VALUES(65, "Max", "Brooks");
insert into authors(author_id, first_name, last_name) VALUES(66, "Anne", "Rice");
insert into authors(author_id, first_name, last_name) VALUES(67, "Joe", "Hill");
insert into authors(author_id, first_name, last_name) VALUES(68, "Mark", "Z. Danielewski");
insert into authors(author_id, first_name, last_name) VALUES(69, "William", "Peter Blatty");
insert into authors(author_id, first_name, last_name) VALUES(70, "Jonathan", "Lethem");
insert into authors(author_id, first_name, last_name) VALUES(71, "Robert", "Louis Stevenson");
insert into authors(author_id, first_name, last_name) VALUES(72, "Vladimir", "Nabokov");
insert into authors(author_id, first_name, last_name) VALUES(73, "Alex", "Michaelides");
insert into authors(author_id, first_name, last_name) VALUES(74, "Oyinkan", "Braithwaite");
insert into authors(author_id, first_name, last_name) VALUES(75, "Ruth", "Ware");
insert into authors(author_id, first_name, last_name) VALUES(76, "Jane", "Harper");
insert into authors(author_id, first_name, last_name) VALUES(77, "Greer", "Hendricks");
insert into authors(author_id, first_name, last_name) VALUES(78, "Alex", "North");
insert into authors(author_id, first_name, last_name) VALUES(79, "Harlan", "Coben");
insert into authors(author_id, first_name, last_name) VALUES(80, "Riley", "Sager");
insert into authors(author_id, first_name, last_name) VALUES(81, "Angie", " Kim");
insert into authors(author_id, first_name, last_name) VALUES(82, "Sally", "Hepworth");
insert into authors(author_id, first_name, last_name) VALUES(83, "Louise", "Penny");
insert into authors(author_id, first_name, last_name) VALUES(84, "Lisa", "Jewell");
insert into authors(author_id, first_name, last_name) VALUES(85, "Adrian", "McKinty");
insert into authors(author_id, first_name, last_name) VALUES(86, "Julia", " Phillips");
insert into authors(author_id, first_name, last_name) VALUES(87, "Jean", "Kwok");
insert into authors(author_id, first_name, last_name) VALUES(88, "Catherine", "McKenzie");
insert into authors(author_id, first_name, last_name) VALUES(89, "Michael", "Connelly");
insert into authors(author_id, first_name, last_name) VALUES(90, "Karin", "Slaughter");
insert into authors(author_id, first_name, last_name) VALUES(91, "Samantha", " Downing");
insert into authors(author_id, first_name, last_name) VALUES(92, "Tosca", "Lee");
ALTER TABLE authors AUTO_INCREMENT=93;

insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000000, 'Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch', STR_TO_DATE('November 28 2006', '%M %d %Y'), 491, '‘Armageddon only happens once, you know. They don’t let you go around again until you get it right.’People have been predicting the end of the world almost from its very beginning, so it’s only natural to be sceptical when a new date is set for Judgement Day. But what if, for once, the predictions are right, and the apocalypse really is due to arrive next Saturday, just after tea?You could spend the time left drowning your sorrows, giving away all your possessions in preparation for the rapture, or laughing it off as (hopefully) just another hoax. Or you could just try to do something about it.It’s a predicament that Aziraphale, a somewhat fussy angel, and Crowley, a fast-living demon now finds themselves in. They’ve been living amongst Earth’s mortals since The Beginning and, truth be told, have grown rather fond of the lifestyle and, in all honesty, are not actually looking forward to the coming Apocalypse.And then there’s the small matter that someone appears to have misplaced the A', 'Good_Omens:_The_Nice_and_Accurate_Prophecies_of_Agnes_Nutter,_Witch.png', 2, 17);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780765326355, 'The Way of Kings', STR_TO_DATE('August 31 2010', '%M %d %Y'), 1007, 'I long for the days before the Last Desolation.The age before the Heralds abandoned us and the Knights Radiant turned against us. A time when there was still magic in the world and honor in the hearts of men.The world became ours, and yet we lost it. Victory proved to be the greatest test of all. Or was that victory illusory? Did our enemies come to recognize that the harder they fought, the fiercer our resistance? Fire and hammer will forge steel into a weapon, but if you abandon your sword, it eventually rusts away.There are four whom we watch. The first is the surgeon, forced to forsake healing to fight in the most brutal war of our time. The second is the assassin, a murderer who weeps as he kills. The third is the liar, a young woman who wears a scholar\'s mantle over the heart of a thief. The last is the prince, a warlord whose eyes have opened to the ancient past as his thirst for battle wanes.The world can change. Surgebinding and Shardwielding can return; the magics of ancient', 'The_Way_of_Kings.png', 1, 17);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780765326362, 'Words of Radiance', STR_TO_DATE('March 4 2014', '%M %d %Y'), 1087, 'From #1 New York Times bestselling author Brandon Sanderson, Words of Radiance, Book Two of the Stormlight Archive, continues the immersive fantasy epic that The Way of Kings began.Expected by his enemies to die the miserable death of a military slave, Kaladin survived to be given command of the royal bodyguards, a controversial first for a low-status "darkeyes." Now he must protect the king and Dalinar from every common peril as well as the distinctly uncommon threat of the Assassin, all while secretly struggling to master remarkable new powers that are somehow linked to his honorspren, Syl.The Assassin, Szeth, is active again, murdering rulers all over the world of Roshar, using his baffling powers to thwart every bodyguard and elude all pursuers. Among his prime targets is Highprince Dalinar, widely considered the power behind the Alethi throne. His leading role in the war would seem reason enough, but the Assassin\'s master has much deeper motives.Brilliant but troubled Shallan st', 'Words_of_Radiance.png', 3, 17);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780765326379, 'Oathbringer', STR_TO_DATE('November 14 2017', '%M %d %Y'), 1248, 'In Oathbringer, the third volume of the New York Times bestselling Stormlight Archive, humanity faces a new Desolation with the return of the Voidbringers, a foe with numbers as great as their thirst for vengeance.Dalinar Kholin\'s Alethi armies won a fleeting victory at a terrible cost: The enemy Parshendi summoned the violent Everstorm, which now sweeps the world with destruction, and in its passing awakens the once peaceful and subservient parshmen to the horror of their millennia-long enslavement by humans. While on a desperate flight to warn his family of the threat, Kaladin Stormblessed must come to grips with the fact that the newly kindled anger of the parshmen may be wholly justified.Nestled in the mountains high above the storms, in the tower city of Urithiru, Shallan Davar investigates the wonders of the ancient stronghold of the Knights Radiant and unearths dark secrets lurking in its depths. And Dalinar realizes that his holy mission to unite his homeland of Alethkar was t', 'Oathbringer.png', 2, 10);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000001, 'Harry Potter and the Sorcerer\'s Stone', STR_TO_DATE('November 1 2003', '%M %d %Y'), 309, 'Alternate cover edition of ISBN 9780439554930Harry Potter\'s life is miserable. His parents are dead and he\'s stuck with his heartless relatives, who force him to live in a tiny closet under the stairs. But his fortune changes when he receives a letter that tells him the truth about himself: he\'s a wizard. A mysterious visitor rescues him from his relatives and takes him to his new home, Hogwarts School of Witchcraft and Wizardry.After a lifetime of bottling up his magical powers, Harry finally feels like a normal kid. But even within the Wizarding community, he is special. He is the boy who lived: the only person to have ever survived a killing curse inflicted by the evil Lord Voldemort, who launched a brutal takeover of the Wizarding world, only to vanish after failing to kill Harry.Though Harry\'s first year at Hogwarts is the best of his life, not everything is perfect. There is a dangerous secret object hidden within the castle walls, and Harry believes it\'s his responsibility ', 'Harry_Potter_and_the_Sorcerer\'s_Stone.png', 1, 14);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780439064866, 'Harry Potter and the Chamber of Secrets', STR_TO_DATE('June 2 1999', '%M %d %Y'), 341, 'The Dursleys were so mean and hideous that summer that all Harry Potter wanted was to get back to the Hogwarts School for Witchcraft and Wizardry. But just as he\'s packing his bags, Harry receives a warning from a strange, impish creature named Dobby who says that if Harry Potter returns to Hogwarts, disaster will strikeAnd strike it does. For in Harry\'s second year at Hogwarts, fresh torments and horrors arise, including an outrageously stuck-up new professor, Gilderoy Lockhart, a spirit named Moaning Myrtle who haunts the girls\' bathroom, and the unwanted attentions of Ron Weasley\'s younger sister, Ginny.But each of these seem minor annoyances when the real trouble begins, and someone -- or something -- starts turning Hogwarts students to stone. Could it be Draco Malfoy, a more poisonous rival than ever? Could it possibly be Hagrid, whose mysterious past is finally told? Or could it be the one everyone at Hogwarts most suspects . . . Harry Potter himself?', 'Harry_Potter_and_the_Chamber_of_Secrets.png', 3, 13);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780439655484, 'Harry Potter and the Prisoner of Azkaban', STR_TO_DATE('May 1 2004', '%M %d %Y'), 435, 'Harry Potter\'s third year at Hogwarts is full of new dangers. A convicted murderer, Sirius Black, has broken out of Azkaban prison, and it seems he\'s after Harry. Now Hogwarts is being patrolled by the dementors, the Azkaban guards who are hunting Sirius. But Harry can\'t imagine that Sirius or, for that matter, the evil Lord Voldemort could be more frightening than the dementors themselves, who have the terrible power to fill anyone they come across with aching loneliness and despair. Meanwhile, life continues as usual at Hogwarts. A top-of-the-line broom takes Harry\'s success at Quidditch, the sport of the Wizarding world, to new heights. A cute fourth-year student catches his eye. And he becomes close with the new Defense of the Dark Arts teacher, who was a childhood friend of his father. Yet despite the relative safety of life at Hogwarts and the best efforts of the dementors, the threat of Sirius Black grows ever closer. But if Harry has learned anything from his education in w', 'Harry_Potter_and_the_Prisoner_of_Azkaban.png', 3, 15);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000002, 'Harry Potter and the Goblet of Fire', STR_TO_DATE('September 28 2002', '%M %d %Y'), 734, 'Harry Potter is midway through his training as a wizard and his coming of age. Harry wants to get away from the pernicious Dursleys and go to the International Quidditch Cup. He wants to find out about the mysterious event that\'s supposed to take place at Hogwarts this year, an event involving two other rival schools of magic, and a competition that hasn\'t happened for a hundred years. He wants to be a normal, fourteen-year-old wizard. But unfortunately for Harry Potter, he\'s not normal - even by wizarding standards. And in his case, different can be deadly. --back cover', 'Harry_Potter_and_the_Goblet_of_Fire.png', 1, 20);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780439358071, 'Harry Potter and the Order of the Phoenix', STR_TO_DATE('September 1 2004', '%M %d %Y'), 870, 'There is a door at the end of a silent corridor. And it’s haunting Harry Pottter’s dreams. Why else would he be waking in the middle of the night, screaming in terror?Harry has a lot on his mind for this, his fifth year at Hogwarts: a Defense Against the Dark Arts teacher with a personality like poisoned honey; a big surprise on the Gryffindor Quidditch team; and the looming terror of the Ordinary Wizarding Level exams. But all these things pale next to the growing threat of He-Who-Must-Not-Be-Named---a threat that neither the magical government nor the authorities at Hogwarts can stop.As the grasp of darkness tightens, Harry must discover the true depth and strength of his friends, the importance of boundless loyalty, and the shocking price of unbearable sacrifice.His fate depends on them all.', 'Harry_Potter_and_the_Order_of_the_Phoenix.png', 3, 16);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780439785969, 'Harry Potter and the Half-Blood Prince', STR_TO_DATE('September 16 2006', '%M %d %Y'), 652, 'When Harry Potter and the Half-Blood Prince opens, the war against Voldemort has begun. The Wizarding world has split down the middle, and as the casualties mount, the effects even spill over onto the Muggles. Dumbledore is away from Hogwarts for long periods, and the Order of the Phoenix has suffered grievous losses. And yet, as in all wars, life goes on.Harry, Ron, and Hermione, having passed their O.W.L. level exams, start on their specialist N.E.W.T. courses. Sixth-year students learn to Apparate, losing a few eyebrows in the process. Teenagers flirt and fight and fall in love. Harry becomes captain of the Gryffindor Quidditch team, while Draco Malfoy pursues his own dark ends. And classes are as fascinating and confounding as ever, as Harry receives some extraordinary help in Potions from the mysterious Half-Blood Prince.Most importantly, Dumbledore and Harry work together to uncover the full and complex story of a boy once named Tom Riddle—the boy who became Lord Voldemort. Like ', 'Harry_Potter_and_the_Half-Blood_Prince.png', 1, 12);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780545010221, 'Harry Potter and the Deathly Hallows', STR_TO_DATE('July 21 2007', '%M %d %Y'), 759, 'Harry Potter is leaving Privet Drive for the last time. But as he climbs into the sidecar of Hagrid’s motorbike and they take to the skies, he knows Lord Voldemort and the Death Eaters will not be far behind.The protective charm that has kept him safe until now is broken. But the Dark Lord is breathing fear into everything he loves. And he knows he can’t keep hiding.To stop Voldemort, Harry knows he must find the remaining Horcruxes and destroy them.He will have to face his enemy in one final battle.--jkrowling.com', 'Harry_Potter_and_the_Deathly_Hallows.png', 3, 15);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780316075558, 'The Black Prism', STR_TO_DATE('August 25 2010', '%M %d %Y'), 629, 'Guile is the Prism, the most powerful man in the world. He is high priest and emperor, a man whose power, wit, and charm are all that preserves a tenuous peace. Yet Prisms never last, and Guile knows exactly how long he has left to live.When Guile discovers he has a son, born in a far kingdom after the war that put him in power, he must decide how much he\'s willing to pay to protect a secret that could tear his world apart.', 'The_Black_Prism.png', 2, 15);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780316079914, 'The Blinding Knife', STR_TO_DATE('September 11 2012', '%M %d %Y'), 671, 'Gavin Guile is dying.He’d thought he had five years left—now he has less than one. With fifty thousand refugees, a bastard son, and an ex-fiancée who may have learned his darkest secret, Gavin has problems on every side. All magic in the world is running wild and threatens to destroy the Seven Satrapies.Worst of all, the old gods are being reborn, and their army of color wights is unstoppable. The only salvation may be the brother whose freedom and life Gavin stole sixteen years ago.', 'The_Blinding_Knife.png', 3, 12);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000003, 'The Broken Eye', STR_TO_DATE('August 26 2014', '%M %d %Y'), 846, 'As the old gods awaken, the Chromeria is in a race to find its lost Prism, the only man who may be able to stop catastrophe, Gavin Guile. But Gavin\'s enslaved on a galley, and when he finally escapes, he finds himself in less than friendly hands. Without the ability to draft which has defined him . . .Meanwhile, the Color Prince\'s army continues its inexorable advance, having swallowed two of the seven satrapies, they now invade the Blood Forest. Andross Guile, thinking his son Gavin lost, tasks his two grandsons with stopping the advance. Kip and his psychopathic half-brother Zymun will compete for the ultimate prize: who will become the next Prism.', 'The_Broken_Eye.png', 2, 10);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780316251334, 'The Blood Mirror', STR_TO_DATE('October 25 2016', '%M %d %Y'), 704, 'Stripped of both magical and political power, the people he once ruled told he\'s dead, and now imprisoned in his own magical dungeon, former Emperor Gavin Guile has no prospect of escape. But the world faces a calamity greater than the Seven Satrapies has ever seen... and only he can save it.As the armies of the White King defeat the Chromeria and old gods are born anew, the fate of worlds will come down to one question: Who is the Lightbringer?LightbringerThe Black PrismThe Blinding KnifeThe Broken EyeThe Blood Mirror', 'The_Blood_Mirror.png', 2, 18);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780316251303, 'The Burning White', STR_TO_DATE('October 22 2019', '%M %d %Y'), 992, 'The nail-biting conclusion to the Lightbringer series! Stripped of both magical and political power, the people he once ruled told he\'s dead, and now imprisoned in his own magical dungeon, former Emperor Gavin Guile has no prospect of escape. But the world faces a calamity greater than the Seven Satrapies has ever seen... and only he can save it.As the armies of the White King defeat the Chromeria and old gods are born anew, the fate of worlds will come down to one question: Who is the Lightbringer?', 'The_Burning_White.png', 1, 16);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780756404079, 'The Name of the Wind', STR_TO_DATE('April 2007', '%M %d %Y'), 662, 'Told in Kvothe\'s own voice, this is the tale of the magically gifted young man who grows to be the most notorious wizard his world has ever seen. The intimate narrative of his childhood in a troupe of traveling players, his years spent as a near-feral orphan in a crime-ridden city, his daringly brazen yet successful bid to enter a legendary school of magic, and his life as a fugitive after the murder of a king form a gripping coming-of-age story unrivaled in recent literature. A high-action story written with a poet\'s hand, The Name of the Wind is a masterpiece that will transport readers into the body and mind of a wizard.', 'The_Name_of_the_Wind.png', 1, 18);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780756404734, 'The Wise Man\'s Fear', STR_TO_DATE('March 1 2011', '%M %d %Y'), 994, '“There are three things all wise men fear: the sea in storm, a night with no moon, and the anger of a gentle man.”My name is Kvothe.I have stolen princesses back from sleeping barrow kings. I burned down the town of Trebon. I have spent the night with Felurian and left with both my sanity and my life. I was expelled from the University at a younger age than most people are allowed in. I tread paths by moonlight that others fear to speak of during day. I have talked to Gods, loved women, and written songs that make the minstrels weep.You may have heard of me.So begins the tale of a hero told from his own point of view — a story unequaled in fantasy literature. Now in The Wise Man\'s Fear, an escalating rivalry with a powerful member of the nobility forces Kvothe to leave the University and seek his fortune abroad. Adrift, penniless, and alone, he travels to Vintas, where he quickly becomes entangled in the politics of courtly society. While attempting to curry favor with a powerful nobl', 'The_Wise_Man\'s_Fear.png', 3, 19);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000004, 'The Priory of the Orange Tree', STR_TO_DATE('February 26 2019', '%M %d %Y'), 848, 'A world divided. A queendom without an heir. An ancient enemy awakens.The House of Berethnet has ruled Inys for a thousand years. Still unwed, Queen Sabran the Ninth must conceive a daughter to protect her realm from destruction – but assassins are getting closer to her door. Ead Duryan is an outsider at court. Though she has risen to the position of lady-in-waiting, she is loyal to a hidden society of mages. Ead keeps a watchful eye on Sabran, secretly protecting her with forbidden magic.Across the dark sea, Tané has trained to be a dragonrider since she was a child, but is forced to make a choice that could see her life unravel.Meanwhile, the divided East and West refuse to parley, and forces of chaos are rising from their sleep.', 'The_Priory_of_the_Orange_Tree.png', 1, 13);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000005, 'The Lies of Locke Lamora', STR_TO_DATE('June 27 2006', '%M %d %Y'), 752, 'An orphan’s life is harsh—and often short—in the mysterious island city of Camorr. But young Locke Lamora dodges death and slavery, becoming a thief under the tutelage of a gifted con artist. As leader of the band of light-fingered brothers known as the Gentleman Bastards, Locke is soon infamous, fooling even the underworld’s most feared ruler. But in the shadows lurks someone still more ambitious and deadly. Faced with a bloody coup that threatens to destroy everyone and everything that holds meaning in his mercenary life, Locke vows to beat the enemy at his own brutal game—or die trying.', 'The_Lies_of_Locke_Lamora.png', 3, 13);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000006, 'Red Seas Under Red Skies', STR_TO_DATE('July 31 2007', '%M %d %Y'), 578, 'After a brutal battle with the underworld that nearly destroyed him, Locke Lamora and his trusted sidekick, Jean, fled the island city of their birth and landed on the exotic shores of Tal Verrar to nurse their wounds. But even at this westernmost edge of civilization, they can\'t rest for long---and they are soon back doing what they do best: stealing from the undeserving rich and pocketing the proceeds for themselves.This time, however, they have targeted the grandest prize of all: the Sinspire, the most exclusive and heavily guarded gambling house in the world. Its nine floors attract the wealthiest clientele - and to rise to the top, one must impress with good credit, amusing behavior...and excruciatingly impeccable play. For there is one cardinal rule, enforced by Requin, the house\'s cold-blooded master: it is death to cheat at any game at the Sinspire.Brazenly undeterred, Locke and Jean have orchestrated an elaborate plan to lie, trick, and swindle their way up the nine floors..', 'Red_Seas_Under_Red_Skies.png', 1, 12);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780553804690, 'The Republic of Thieves', STR_TO_DATE('October 8 2013', '%M %d %Y'), 650, 'With what should have been the greatest heist of their career gone spectacularly sour, Locke and his trusted partner, Jean, have barely escaped with their lives. Or at least Jean has. But Locke is slowly succumbing to a deadly poison that no alchemist or physiker can cure. Yet just as the end is near, a mysterious Bondsmage offers Locke an opportunity that will either save him or finish him off once and for all.Magi political elections are imminent, and the factions are in need of a pawn. If Locke agrees to play the role, sorcery will be used to purge the venom from his body - though the process will be so excruciating he may well wish for death. Locke is opposed, but two factors cause his will to crumble: Jean\'s imploring - and the Bondsmage\'s mention of a woman from Locke\'s past: Sabetha. She is the love of his life, his equal in skill and wit, and now, his greatest rival. Locke was smitten with Sabetha from his first glimpse of her as a young fellow orphan and thief-in-training. ', 'The_Republic_of_Thieves.png', 1, 11);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780593099322, 'Dune', STR_TO_DATE('October 1 2019', '%M %d %Y'), 688, 'A deluxe hardcover edition of Frank Herbert\'s epic masterpiece--a triumph of the imagination and one of the bestselling science fiction novels of all time. This deluxe hardcover edition of Dune includes: - An iconic new cover- Stained edges and fully illustrated endpapers- A beautifully designed poster on the interior of the jacket- A redesigned world map of Dune- An updated Introduction by Brian HerbertSet on the desert planet Arrakis, Dune is the story of the boy Paul Atreides, heir to a noble family tasked with ruling an inhospitable world where the only thing of value is the "spice" melange, a drug capable of extending life and enhancing consciousness. Coveted across the known universe, melange is a prize worth killing for...When House Atreides is betrayed, the destruction of Paul\'s family will set the boy on a journey toward a destiny greater than he could ever have imagined. And as he evolves into the mysterious man known as Muad\'Dib, he will bring to fruition humankind\'s', 'Dune.png', 2, 11);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000007, '1984', STR_TO_DATE('September 3 2013', '%M %d %Y'), 237, 'Among the seminal texts of the 20th century, Nineteen Eighty-Four is a rare work that grows more haunting as its futuristic purgatory becomes more real. Published in 1949, the book offers political satirist George Orwell\'s nightmarish vision of a totalitarian, bureaucratic world and one poor stiff\'s attempt to find individuality. The brilliance of the novel is Orwell\'s prescience of modern life—the ubiquity of television, the distortion of the language—and his ability to construct such a thorough version of hell. Required reading for students since it was published, it ranks among the most terrifying novels ever written.', '1984.png', 2, 18);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780812550702, 'Ender\'s Game', STR_TO_DATE('September 30 2004', '%M %d %Y'), 324, 'Andrew "Ender" Wiggin thinks he is playing computer simulated war games; he is, in fact, engaged in something far more desperate. The result of genetic experimentation, Ender may be the military genius Earth desperately needs in a war against an alien enemy seeking to destroy all human life. The only way to find out is to throw Ender into ever harsher training, to chip away and find the diamond inside, or destroy him utterly. Ender Wiggin is six years old when it begins. He will grow up fast.But Ender is not the only result of the experiment. The war with the Buggers has been raging for a hundred years, and the quest for the perfect general has been underway almost as long. Ender\'s two older siblings, Peter and Valentine, are every bit as unusual as he is, but in very different ways. While Peter was too uncontrollably violent, Valentine very nearly lacks the capability for violence altogether. Neither was found suitable for the military\'s purpose. But they are driven by their jealo', 'Ender\'s_Game.png', 1, 16);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780765377067, 'The Three-Body Problem', STR_TO_DATE('November 11 2014', '%M %d %Y'), 399, 'The Three-Body Problem is the first chance for English-speaking readers to experience the Hugo Award-winning phenomenon from China\'s most beloved science fiction author, Liu Cixin.Set against the backdrop of China\'s Cultural Revolution, a secret military project sends signals into space to establish contact with aliens. An alien civilization on the brink of destruction captures the signal and plans to invade Earth. Meanwhile, on Earth, different camps start forming, planning to either welcome the superior beings and help them take over a world seen as corrupt, or to fight against the invasion. The result is a science fiction masterpiece of enormous scope and vision.', 'The_Three-Body_Problem.png', 3, 18);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000008, 'The Dark Forest', STR_TO_DATE('August 11 2015', '%M %d %Y'), 512, 'This is the second novel in "Remembrance of Earth’s Past", the near-future trilogy written by the China\'s multiple-award-winning science fiction author, Cixin Liu. In The Dark Forest, Earth is reeling from the revelation of a coming alien invasion — four centuries in the future. The aliens\' human collaborators have been defeated but the presence of the sophons, the subatomic particles that allow Trisolaris instant access to all human information, means that Earth\'s defense plans are exposed to the enemy. Only the human mind remains a secret. This is the motivation for the Wallfacer Project, a daring plan that grants four men enormous resources to design secret strategies hidden through deceit and misdirection from Earth and Trisolaris alike. Three of the Wallfacers are influential statesmen and scientists but the fourth is a total unknown. Luo Ji, an unambitious Chinese astronomer and sociologist, is baffled by his new status. All he knows is that he\'s the one Wallfacer that Tr', 'The_Dark_Forest.png', 1, 16);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780765377104, 'Death\'s End', STR_TO_DATE('September 20 2016', '%M %d %Y'), 604, 'Half a century after the Doomsday Battle, the uneasy balance of Dark Forest Deterrence keeps the Trisolaran invaders at bay. Earth enjoys unprecedented prosperity due to the infusion of Trisolaran knowledge. With human science advancing daily and the Trisolarans adopting Earth culture, it seems that the two civilizations will soon be able to co-exist peacefully as equals without the terrible threat of mutually assured annihilation. But the peace has also made humanity complacent.Cheng Xin, an aerospace engineer from the early twenty-first century, awakens from hibernation in this new age. She brings with her knowledge of a long-forgotten program dating from the beginning of the Trisolar Crisis, and her very presence may upset the delicate balance between two worlds. Will humanity reach for the stars or die in its cradle?', 'Death\'s_End.png', 1, 19);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780060929879, 'Brave New World', STR_TO_DATE('September 1 1998', '%M %d %Y'), 288, 'Brave New World is a dystopian novel by English author Aldous Huxley, written in 1931 and published in 1932. Largely set in a futuristic World State, inhabited by genetically modified citizens and an intelligence-based social hierarchy, the novel anticipates huge scientific advancements in reproductive technology, sleep-learning, psychological manipulation and classical conditioning that are combined to make a dystopian society which is challenged by only a single individual: the story\'s protagonist.', 'Brave_New_World.png', 2, 16);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000009, 'Fahrenheit 451', STR_TO_DATE('November 29 2011', '%M %d %Y'), 194, 'Guy Montag is a fireman. In his world, where television rules and literature is on the brink of extinction, firemen start fires rather than put them out. His job is to destroy the most illegal of commodities, the printed book, along with the houses in which they are hidden.Montag never questions the destruction and ruin his actions produce, returning each day to his bland life and wife, Mildred, who spends all day with her television \'family\'. But then he meets an eccentric young neighbor, Clarisse, who introduces him to a past where people did not live in fear and to a present where one sees the world through the ideas in books instead of the mindless chatter of television.When Mildred attempts suicide and Clarisse suddenly disappears, Montag begins to question everything he has ever known.', 'Fahrenheit_451.png', 3, 10);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000010, 'The Hitchhiker\'s Guide to the Galaxy', STR_TO_DATE('June 23 2007', '%M %d %Y'), 193, 'Seconds before the Earth is demolished to make way for a galactic freeway, Arthur Dent is plucked off the planet by his friend Ford Prefect, a researcher for the revised edition of The Hitchhiker\'s Guide to the Galaxy who, for the last fifteen years, has been posing as an out-of-work actor.Together this dynamic pair begin a journey through space aided by quotes from The Hitchhiker\'s Guide ("A towel is about the most massively useful thing an interstellar hitchhiker can have") and a galaxy-full of fellow travelers: Zaphod Beeblebrox—the two-headed, three-armed ex-hippie and totally out-to-lunch president of the galaxy; Trillian, Zaphod\'s girlfriend (formally Tricia McMillan), whom Arthur tried to pick up at a cocktail party once upon a time zone; Marvin, a paranoid, brilliant, and chronically depressed robot; Veet Voojagig, a former graduate student who is obsessed with the disappearance of all the ballpoint pens he bought over the years.', 'The_Hitchhiker\'s_Guide_to_the_Galaxy.png', 3, 20);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000011, 'The Restaurant at the End of the Universe', STR_TO_DATE('April 26 2005', '%M %d %Y'), 250, 'Alternate Cover Edition ISBN 0345418921 (ISBN13: 9780345418920)Facing annihilation at the hands of the warlike Vogons is a curious time to have a cosmically displaced Arthur Dent and his curious comrades in arms as they hurtle through space powered by pure improbability - and desperately in search of a place to eat. Among Arthur\'s motley shipmates are Ford Prefect, a long-time friend and contributor to the The Hitch Hiker\'s Guide to the Galaxy; Zaphod Beeblebrox, the three-armed, two-headed ex-president of the galaxy; Tricia McMilan, a fellow Earth refuge who\'s gone native (her name is Trillian now); and Marvin, who suffers nothing and no one gladly.Source: douglasadams.com', 'The_Restaurant_at_the_End_of_the_Universe.png', 1, 18);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780345418906, 'Life, the Universe and Everything', STR_TO_DATE('April 26 2005', '%M %d %Y'), 224, 'The unhappy inhabitants of planet Krikkit are sick of looking at the night sky above their heads–so they plan to destroy it. The universe, that is. Now only five individuals stand between the killer robots of Krikkit and their goal of total annihilation.They are Arthur Dent, a mild-mannered space and time traveler who tries to learn how to fly by throwing himself at the ground and missing; Ford Prefect, his best friend, who decides to go insane to see if he likes it; Slartibartfast, the indomitable vice president of the Campaign for Real Time, who travels in a ship powered by irrational behavior; Zaphod Beeblebrox, the two-headed, three-armed ex-president of the galazy; and Trillian, the sexy space cadet who is torn between a persistent Thunder God and a very depressed Beeblebrox.How will it all end? Will it end? Only this stalwart crew knows as they try to avert “universal” Armageddon and save life as we know it–and don’t know it!', 'Life,_the_Universe_and_Everything.png', 1, 14);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000012, 'So Long, and Thanks for All the Fish', STR_TO_DATE('December 25 2008', '%M %d %Y'), 225, 'Back on Earth with nothing more to show for his long, strange trip through time and space than a ratty towel and a plastic shopping bag, Arthur Dent is ready to believe that the past eight years were all just a figment of his stressed-out imagination. But a gift-wrapped fishbowl with a cryptic inscription, the mysterious disappearance of Earth\'s dolphins, and the discovery of his battered copy of The Hitchhiker\'s Guide to the Galaxy all conspire to give Arthur the sneaking suspicion that something otherworldly is indeed going on. . . .God only knows what it all means. And fortunately, He left behind a Final Message of explanation. But since it\'s light-years away from Earth, on a star surrounded by souvenir booths, finding out what it is will mean hitching a ride to the far reaches of space aboard a UFO with a giant robot. But what else is new?', 'So_Long,_and_Thanks_for_All_the_Fish.png', 1, 19);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780345379337, 'Mostly Harmless', STR_TO_DATE('October 19 1993', '%M %d %Y'), 288, 'It’s easy to get disheartened when your planet has been blown up, the woman you love has vanished due to a misunderstanding about space/time, the spaceship you are on crashes on a remote and Bob-fearing planet, and all you have to fall back on are a few simple sandwich-making skills. However, instead of being disheartened, Arthur Dent makes the terrible mistake of starting to enjoy life a bit–and immediately all hell breaks loose.Hell takes a number of forms: there’s the standard Ford Prefect version, in the shape of an all-new edition of The Hitchhiker’s Guide to the Galaxy, and a totally unexpected manifestation in the form of a teenage girl who startles Arthur Dent by being his daughter when he didn’t even know he had one.Can Arthur save the Earth from total multidimensional obliteration? Can he save the Guide from a hostile alien takeover? Can he save his daughter, Random, from herself? Of course not. He never works out exactly what is going on. Will you?', 'Mostly_Harmless.png', 3, 13);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781401323585, 'And Another Thing...', STR_TO_DATE('October 12 2009', '%M %d %Y'), 275, 'AN ENGLISHMAN\'S CONTINUING SEARCH THROUGH SPACE AND TIME FOR A DECENT CUP OF TEA...Arthur Dent\'s accidental association with that wholly remarkable book The Hitchhiker\'s Guide to the Galaxy has not been entirely without incident.Arthur has traveled the length, breadth, and depth of known, and unknown, space. He has stumbled forward and backward through time. He has been blown up, reassembled, cruelly imprisoned, horribly released, and colorfully insulted more than is strictly necessary. And of course Arthur Dent has comprehensively failed to grasp the meaning of life, the universe, and everything.Arthur has finally made it home to Earth, but that does not mean he has escaped his fate.Arthur\'s chances of getting his hands on a decent cuppa have evaporated rapidly, along with all the world\'s oceans. For no sooner has he touched down on the planet Earth than he finds out that it is about to be blown up...again.And Another Thing...is the rather unexpected, but very welcome, sixth inst', 'And_Another_Thing....png', 3, 10);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780804139021, 'The Martian', STR_TO_DATE('February 11 2014', '%M %d %Y'), 369, 'Six days ago, astronaut Mark Watney became one of the first people to walk on Mars. Now, he’s sure he’ll be the first person to die there.After a dust storm nearly kills him and forces his crew to evacuate while thinking him dead, Mark finds himself stranded and completely alone with no way to even signal Earth that he’s alive—and even if he could get word out, his supplies would be gone long before a rescue could arrive. Chances are, though, he won’t have time to starve to death. The damaged machinery, unforgiving environment, or plain-old “human error” are much more likely to kill him first. But Mark isn’t ready to give up yet. Drawing on his ingenuity, his engineering skills — and a relentless, dogged refusal to quit — he steadfastly confronts one seemingly insurmountable obstacle after the next. Will his resourcefulness be enough to overcome the impossible odds against him?', 'The_Martian.png', 1, 14);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780553803709, 'I, Robot', STR_TO_DATE('June 1 2004', '%M %d %Y'), 224, 'The three laws of Robotics:1) A robot may not injure a human being or, through inaction, allow a human being to come to harm.2) A robot must obey orders given to it by human beings except where such orders would conflict with the First Law.3) A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.With these three, simple directives, Isaac Asimov changed our perception of robots forever when he formulated the laws governing their behavior. In I, Robot, Asimov chronicles the development of the robot through a series of interlinked stories: from its primitive origins in the present to its ultimate perfection in the not-so-distant future--a future in which humanity itself may be rendered obsolete.Here are stories of robots gone mad, of mind-read robots, and robots with a sense of humor. Of robot politicians, and robots who secretly run the world--all told with the dramatic blend of science fact and science fiction that has become As', 'I,_Robot.png', 2, 18);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780385333849, 'Slaughterhouse-Five', STR_TO_DATE('January 12 1999', '%M %d %Y'), 275, 'Selected by the Modern Library as one of the 100 best novels of all time, Slaughterhouse-Five, an American classic, is one of the world\'s great antiwar books. Centering on the infamous firebombing of Dresden, Billy Pilgrim\'s odyssey through time reflects the mythic journey of our own fractured lives as we search for meaning in what we fear most.', 'Slaughterhouse-Five.png', 3, 20);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000013, 'Flowers for Algernon', STR_TO_DATE('March 1 1966', '%M %d %Y'), 216, 'The story of a mentally disabled man whose experimental quest for intelligence mirrors that of Algernon, an extraordinary lab mouse. In diary entries, Charlie tells how a brain operation increases his IQ and changes his life. As the experimental procedure takes effect, Charlie\'s intelligence expands until it surpasses that of the doctors who engineered his metamorphosis. The experiment seems to be a scientific breakthrough of paramount importance--until Algernon begins his sudden, unexpected deterioration. Will the same happen to Charlie?', 'Flowers_for_Algernon.png', 3, 10);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780393312836, 'A Clockwork Orange', STR_TO_DATE('May 21 1986', '%M %d %Y'), 213, 'A vicious fifteen-year-old droog is the central character of this 1963 classic. In Anthony Burgess\'s nightmare vision of the future, where criminals take over after dark, the story is told by the central character, Alex, who talks in a brutal invented slang that brilliantly renders his and his friends\' social pathology. A Clockwork Orange is a frightening fable about good and evil, and the meaning of human freedom. And when the state undertakes to reform Alex to "redeem" him, the novel asks, "At what cost?"This edition includes the controversial last chapter not published in the first edition and Burgess\'s introduction "A Clockwork Orange Resucked."', 'A_Clockwork_Orange.png', 3, 18);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781421501680, 'Death Note, Vol. 1: Boredom', STR_TO_DATE('October 10 2005', '%M %d %Y'), 195, 'Light Yagami is an ace student with great prospects - and he\'s bored out of his mind. But all that changes when he finds the Death Note, a notebook dropped by a rogue Shinigami, a death god. Any human whose name is written in the notebook dies, and now Light has vowed to use the power of the Death Note to rid the world of evil. But when criminals begin dropping dead, the authorities send the legendary detective L to track down the killer. With L hot on his heels, will Light lose sight of his noble goal... or his life?BoredomLight tests the boundaries of the Death Note\'s powers as L and the police begin to close in. Luckily, Light\'s father is the head of the Japanese National Police Agency and leaves vital information about the case lying around the house. With access to his father\'s files, Light can keep one step ahead of the authorities. But who is the strange man following him, and how can Light guard against enemies whose names he doesn\'t know?', 'Death_Note,_Vol._1:_Boredom.png', 3, 13);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781591169208, 'Fullmetal Alchemist, Vol. 1', STR_TO_DATE('May 3 2005', '%M %d %Y'), 192, 'Alchemy: the mystical power to alter the natural world; something between magic, art and science. When two brothers, Edward and Alphonse Elric, dabbled in this power to grant their dearest wish, one of them lost an arm and a leg...and the other became nothing but a soul locked into a body of living steel. Now Edward is an agent of the government, a slave of the military-alchemical complex, using his unique powers to obey orders...even to kill. Except his powers aren\'t unique. The world has been ravaged by the abuse of alchemy. And in pursuit of the ultimate alchemical treasure, the Philosopher\'s Stone, their enemies are even more ruthless than they are...', 'Fullmetal_Alchemist,_Vol._1.png', 1, 16);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781591826033, 'Fruits Basket, Vol. 1', STR_TO_DATE('February 10 2004', '%M %d %Y'), 216, 'A family with an ancient curse...And the girl who will change their lives forever...Tohru Honda was an orphan with no place to go until the mysterious Sohma family offered her a place to call home. Now her ordinary high school life is turned upside down as she\'s introduced to the Sohma\'s world of magical curses and family secrets.', 'Fruits_Basket,_Vol._1.png', 2, 16);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781591169154, 'Ouran High School Host Club, Vol. 1', STR_TO_DATE('July 5 2005', '%M %d %Y'), 178, 'One day, Haruhi, a scholarship student at exclusive Ouran High School, breaks an $80,000 vase that belongs to the \'Host Club\', a mysterious campus group consisting of six super-rich (and gorgeous) guys. To pay back the damages, she is forced to work for the club, and it\'s there that she discovers just how wealthy the boys are and how different they are from everybody else.', 'Ouran_High_School_Host_Club,_Vol._1.png', 3, 19);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781421508221, 'Vampire Knight, Vol. 1', STR_TO_DATE('January 9 2007', '%M %d %Y'), 192, 'Yuki Cross has no memory of her past prior to the moment she was saved from a vampire attack ten years ago. She was adopted by the headmaster of Cross Academy, and now works alongside Zero to guard the Academy\'s secret. Cross Adademy is attended by two groups of students: the Day Class and the Night Class. At twilight, when the students of the Day Class return to their dorm, they cross paths with the Night Class on their way to school. Yuki Cross and Zero Kiryu are the Guardians of the school, protecting the Day Class from the Academy\'s dark secret: the Night Class is full of vampires!', 'Vampire_Knight,_Vol._1.png', 1, 12);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781591164418, 'Bleach, Volume 01', STR_TO_DATE('May 19 2004', '%M %d %Y'), 200, 'Hot-tempered 15-year-old Ichigo Kurosaki, the hero of the popular fantasy-adventure \n\nBleach\n\n, has the unsettling ability to see spirits who are unable to rest in peace. His sixth sense leads him to Rukia, a Soul Reaper who destroys Hollows (soul-devouring monsters) and ensures the deceased find repose with the Soul Society. When she\'s injured in battle, Rukia transfers her sword and much of her power to Ichigo, whose spiritual energy makes him a formidable substitute Soul Reaper. But the orange-haired teenager isn\'t sure he wants the job: too many risks and moral dilemmas.', 'Bleach,_Volume_01.png', 1, 12);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781569319000, 'Naruto, Vol. 01: Uzumaki Naruto', STR_TO_DATE('February 2014', '%M %d %Y'), 187, 'Naruto is a ninja-in-training with an incorrigible knack for mischief. His wild antics amuse his teammates, but Naruto is completely serious about one thing: becoming the world\'s greatest ninja! UZUMAKI NARUTO Twelve years ago the Village Hidden in the Leaves was attacked by a fearsome threat. A nine-tailed fox spirit claimed the life of the village leader, the Hokage, and many others. Today, the village is at peace, and a troublemaking kid named Naruto is struggling to graduate from Ninja Academy. His goal may be to become Hokage, but his true destiny will be much more complicated. The adventure begins now!', 'Naruto,_Vol._01:_Uzumaki_Naruto.png', 1, 19);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780316080842, 'Black Butler, Vol. 1', STR_TO_DATE('January 26 2010', '%M %d %Y'), 192, 'In the Victorian ages of London The Earl of the Phantomhive house, Ciel Phantomhive, needs to get his revenge on those who had humiliated him and destroyed what he loved. Not being able to do it alone he sells his soul to a demon he names Sebastian Michaelis. Now working as his butler, Sebastian must help the Earl Phantomhive in this suspenseful, exciting, thriller manga.', 'Black_Butler,_Vol._1.png', 2, 20);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781569319475, 'InuYasha: Turning Back Time', STR_TO_DATE('April 2 2003', '%M %d %Y'), 192, 'Transported back to Japan\'s feudal era, high school student Kagome accidentally releases the feral half-demon dog boy Inu-Yasha from his imprisonment for stealing the Jewel of Four Souls.', 'InuYasha:_Turning_Back_Time.png', 1, 19);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781421505855, 'Skip Beat!, Vol. 01', STR_TO_DATE('July 5 2006', '%M %d %Y'), 184, 'Kyoko always thought that Sho, whose family took her in when she was small, was her prince charming. However, when Sho heads for Tokyo to make it big as a musician, Kyoko goes with him and has to quit high school to support his dream. But soon, being in the big city makes Kyoko realize that she has show business ambitions of her own!', 'Skip_Beat!,_Vol._01.png', 3, 18);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781892213013, 'Sailor Moon, Vol. 1', STR_TO_DATE('January 1 2003', '%M %d %Y'), 200, 'Librarian\'s note: There is an Alternate Cover Edition for this edition of this book here.One of the most-beloved of all Japanese manga titles, Naoko Takeuchi\'s Sailor Moon has enthralled millions of readers worldwide since its debut in book form in early 1992. When Usagi Tsukino adopts a stray cat, she gets more than she bargains for The talking cat, Luna, informs Usagi that she is actually Sailor Moon, a magical princess from the future and protector of the Solar System. With the help of her new friends, the Sailor Scouts, and the mysterious Tuxedo Mask, Sailor Moon embarks on a quest to save us all from the evil powers of the Negaverse.', 'Sailor_Moon,_Vol._1.png', 2, 12);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781569319017, 'One Piece, Volume 1: Romance Dawn', STR_TO_DATE('June 23 2003', '%M %d %Y'), 210, 'A new shonen sensation in Japan, this series features Monkey D. Luffy, whose main ambition is to become a pirate. Eating the Gum-Gum Fruit gives him strange powers but also invokes the fruit\'s curse: anybody who consumes it can never learn to swim. Nevertheless, Monkey and his crewmate Roronoa Zoro, master of the three-sword fighting style, sail the Seven Seas of swashbuckling adventure in search of the elusive treasure "One Piece."', 'One_Piece,_Volume_1:_Romance_Dawn.png', 3, 14);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781421501086, 'Nana, Vol. 1', STR_TO_DATE('December 6 2005', '%M %d %Y'), 192, 'Nana Komatsu is a young woman who\'s endured an unending string of boyfriend problems. Moving to Tokyo, she\'s hoping to take control of her life and put all those messy misadventures behind her. She\'s looking for love and she\'s hoping to find it in the big city.Nana Osaki, on the other hand, is cool, confident and focused. She swaggers into town and proceeds to kick down the doors to Tokyo\'s underground punk scene. She\'s got a dream and won\'t give up until she becomes Japan\'s No. 1 rock\'n\'roll superstar.This is the story of two 20-year-old women who share the same name. Even though they come from completely different backgrounds, they somehow meet and become best friends. The world of Nana is a world exploding with sex, music, fashion, gossip and all-night parties.', 'Nana,_Vol._1.png', 1, 17);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781591162209, 'Rurouni Kenshin, Volume 01', STR_TO_DATE('October 22 2003', '%M %d %Y'), 201, 'Packed with action, romance and historical intrigue, \nRurouni Kenshin\n is one of the most beloved and popular manga series worldwide. Set against the backdrop of the Meiji Restoration, it tells the saga of Himura Kenshin, once an assassin of ferocious power, now a humble rurouni, a wandering swordsman fighting to protect the honor of those in need.\xa0A hundred and fifty years ago in Kyoto, amid the flames of revolution, there arose a warrior, an assassin of such ferocious power he was given the title Hitokiri: Manslayer. With his bloodstained blade, Hitokiri Battosai helped close the turbulent Bakumatsu period and end the reign of the shoguns, slashing open the way toward the progressive Meiji Era.\xa0 Then he vanished, and with the flow of years became legend. In the 11th year of Meiji, in the middle of Tokyo, the tale begins. Himura Kenshin, a humble rurouni, or wandering swordsman, comes to the aid of Kamiya Kaoru, a young woman struggling to defend her father\'s school of swords', 'Rurouni_Kenshin,_Volume_01.png', 1, 14);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780345470577, 'Tsubasa: RESERVoir CHRoNiCLE, Vol. 1', STR_TO_DATE('April 27 2004', '%M %d %Y'), 197, 'SAKURA AND SYAORAN RETURN!But they\'re not the people you know. Sakura is the princess of Clow - and possessor of a mysterious, misunderstood power that promises to change the world. Syaoran is her childhood friend and leader of the archaeological dig that took his father\'s life. They reside in an alternate reality...where whatever you least expect can happen - and does. When Sakura ventures to the dig site to declare her love for Syaoran, a puzzling symbol is uncovered - which triggers a remarkable quest. Now Syaoran embarks upon a desperate journey through other worlds - all in the name of saving Sakura.', 'Tsubasa:_RESERVoir_CHRoNiCLE,_Vol._1.png', 2, 17);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781591828785, 'Cardcaptor Sakura, Vol. 1', STR_TO_DATE('July 6 2004', '%M %d %Y'), 200, 'From Clamp comes the thrilling adventures of Sakura, a fourth-grader who accidentally finds an enchanted book called The Clow. The book once contained a set of magical Clow Cards, but they all escaped while the guardian of the book fell asleep. Now, Sakura is thrust into a mystical journey to capture all the cards before they wreak chaos and destruction!', 'Cardcaptor_Sakura,_Vol._1.png', 1, 11);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780345470584, 'xxxHolic, Vol. 1', STR_TO_DATE('April 27 2004', '%M %d %Y'), 178, 'Watanuki Kimihiro is haunted by visions of ghosts and spirits. Seemingly by chance, he encounters a mysterious witch named Yuuko, who claims she can help. In desperation, he accepts, but realizes that he\'s just been tricked into working for Yuuko in order to pay off the cost of her services. Soon he\'s employed in her little shop--a job which turns out to be nothing like his previous work experience Most of Yuuko\'s customers live in Japan, but Yuko and Watanuki are about to have some unusual visitors named Sakura and Syaoran from a land called Clow. . . . XXXHolic volume one "crosses over with "Tsubasa volume one "Don\'t miss it "Includes special extras after the story Includes chapters 1-8', 'xxxHolic,_Vol._1.png', 1, 14);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9784757518087, 'Pandora Hearts 1巻', STR_TO_DATE('November 27 2006', '%M %d %Y'), 171, '四大公爵家ベザリウス家の次期当主・オズ＝ベザリウスは、15歳の成人の儀の最中に身に覚えのない罪によって、闇の監獄・アヴィスへと堕とされる。アヴィスの中に閉じ込められた彼はそこで「血染めの黒うさぎ（ビーラビット）」と呼ばれるチェインのアリスと出会い、彼女と契約を交わすことでアヴィスから脱出することに成功するが…。', 'Pandora_Hearts_1巻.png', 2, 15);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780345501332, 'Fairy Tail, Vol. 01', STR_TO_DATE('March 25 2008', '%M %d %Y'), 198, 'THE WICKED SIDE OF WIZARDRYCelestial wizard Lucy wants to join the Fairy Tail, a club for the most powerful wizards. But instead, her ambitions land her in the clutches of a gang of unsavory pirates led by a devious magician. Her only hope is Natsu, a strange boy she happens to meet on her travels. Natsu\'s not your typical hero, he gets motion sickness, eats like a pig, and his best friend is a talking cat. With friends like this, is Lucy better off with her enemies?', 'Fairy_Tail,_Vol._01.png', 1, 19);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781421527550, 'Kimi ni Todoke: From Me to You, Vol. 1', STR_TO_DATE('August 4 2009', '%M %d %Y'), 205, 'Sawako Kuronuma is the perfect heroine...for a horror movie. With striking similarities to a haunting movie character--jet-black hair, sinister smile and silent demeanor--she\'s mistakenly called Sadako by those around her. But behind her scary façade is a very misunderstood teenager. Too shy to fit in, all she wants to do is make some friends. But when the most popular boy in class befriends her, she\'s sure to make more than just that--she\'s about to make some enemies too!', 'Kimi_ni_Todoke:_From_Me_to_You,_Vol._1.png', 3, 18);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781413903171, 'Yotsuba&!, Vol. 1', STR_TO_DATE('June 21 2005', '%M %d %Y'), 224, 'Presents the story of the new kid in town - little Yotsuba, a green-haired and wide-eyed girl who doesn\'t have a clue... about anything! With no knowledge of the world around her, and an unnatural fear of air conditioners, Yotsuba has her new neighbors\' heads spinning. This book is written by Kyohiko Azuma, creator of Azumanga Daioh.', 'Yotsuba&!,_Vol._1.png', 2, 18);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781421506234, 'D.Gray-man, Vol. 1', STR_TO_DATE('May 2 2006', '%M %d %Y'), 196, 'Set in a fictional end of the 19th century England, it\'s the story of Allen Walker, a 15-year-old boy who roams the Earth in search of Innocence. Washed away to unknown parts of the world after The Great Flood, Innocence is the mysterious substance used to create weapons that obliterate demons known as akuma.A born exorcist, Walker\'s primary anti-akuma weapon is the cross that\'s embossed on his red and disfigured left hand, which contains Innocence. But not only does Walker destroy akuma, he sees the akuma hiding inside a person\'s soul! Together with his fellow exorcists fighting under the command of the Black Order, Walker leads the battle against the Millennium Earl, the evil being out to destroy mankind.', 'D.Gray-man,_Vol._1.png', 3, 16);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781612620244, 'Attack on Titan, Vol. 1', STR_TO_DATE('June 19 2012', '%M %d %Y'), 193, 'In this post-apocalyptic sci-fi story, humanity has been devastated by the bizarre, giant humanoids known as the Titans. Little is known about where they came from or why they are bent on consuming mankind. Seemingly unintelligent, they have roamed the world for years, killing everyone they see. For the past century, what\'s left of man has hidden in a giant, three-walled city. People believe their 100-meter-high walls will protect them from the Titans, but the sudden appearance of an immense Titan is about to change everything.Chapters list:1. To You, 2000 Years From Now2. That Day3. Night Of The Disbanding Ceremony4. First Battle', 'Attack_on_Titan,_Vol._1.png', 3, 18);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781427814036, 'Maid-sama! Vol. 01', STR_TO_DATE('April 1 2009', '%M %d %Y'), 208, 'Misaki Ayuzawa is the President of the Student Council at Seika High School, formerly an all-boys school. Unfortunately, most of the students are still male and stuck in their slovenly habits, so man-hating Misaki really socks it to them in an attempt to make the school presentable to attract more female students. But what will she do when the sexiest boy in school finds out that after school, Misaki works in a maid cafe.First published in Japan in 2006 by Hakusensha Inc., Tokyo.', 'Maid-sama!_Vol._01.png', 2, 19);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780450040184, 'The Shining', STR_TO_DATE('July 1 1980', '%M %d %Y'), 659, 'Jack Torrance\'s new job at the Overlook Hotel is the perfect chance for a fresh start. As the off-season caretaker at the atmospheric old hotel, he\'ll have plenty of time to spend reconnecting with his family and working on his writing. But as the harsh winter weather sets in, the idyllic location feels ever more remote...and more sinister. And the only one to notice the strange and terrible forces gathering around the Overlook is Danny Torrance, a uniquely gifted five-year-old.', 'The_Shining.png', 3, 10);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780450411434, 'It', STR_TO_DATE('October 1 1987', '%M %d %Y'), 1116, 'Welcome to Derry, Maine ...It’s a small city, a place as hauntingly familiar as your own hometown. Only in Derry the haunting is real ...They were seven teenagers when they first stumbled upon the horror. Now they are grown-up men and women who have gone out into the big world to gain success and happiness. But none of them can withstand the force that has drawn them back to Derry to face the nightmare without an end, and the evil without a name.', 'It.png', 3, 13);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780393970128, 'Dracula', STR_TO_DATE('May 12 1986', '%M %d %Y'), 488, 'You can find an alternative cover edition for this ISBN here and here.A rich selection of background and source materials is provided in three areas: Contexts includes probable inspirations for Dracula in the earlier works of James Malcolm Rymer and Emily Gerard. Also included are a discussion of Stoker\'s working notes for the novel and "Dracula\'s Guest," the original opening chapter to Dracula. Reviews and Reactions reprints five early reviews of the novel. "Dramatic and Film Variations" focuses on theater and film adaptations of Dracula, two indications of the novel\'s unwavering appeal. David J. Skal, Gregory A. Waller, and Nina Auerbach offer their varied perspectives. Checklists of both dramatic and film adaptations are included.Criticism collects seven theoretical interpretations of Dracula by Phyllis A. Roth, Carol A. Senf, Franco Moretti, Christopher Craft, Bram Dijkstra, Stephen D. Arata, and Talia Schaffer.A Chronology and a Selected Bibliography are included.', 'Dracula.png', 2, 10);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781416524304, 'Carrie', STR_TO_DATE('November 1 2005', '%M %d %Y'), 253, 'A modern classic, Carrie introduced a distinctive new voice in American fiction -- Stephen King. The story of misunderstood high school girl Carrie White, her extraordinary telekinetic powers, and her violent rampage of revenge, remains one of the most barrier-breaking and shocking novels of all time.Make a date with terror and live the nightmare that is...Carrie--back cover', 'Carrie.png', 1, 17);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780450031069, '\'Salem\'s Lot', STR_TO_DATE('June 2 1991', '%M %d %Y'), 483, 'Thousands of miles away from the small township of \'Salem\'s Lot, two terrified people, a man and a boy, still share the secrets of those clapboard houses and tree-lined streets. They must return to \'Salem\'s Lot for a final confrontation with the unspeakable evil that lives on in the town.', '\'Salem\'s_Lot.png', 1, 12);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780385199575, 'The Stand', STR_TO_DATE('May 1 1990', '%M %d %Y'), 1153, 'This is the way the world ends: with a nanosecond of computer error in a Defense Department laboratory and a million casual contacts that form the links in a chain letter of death. And here is the bleak new world of the day after: a world stripped of its institutions and emptied of 99 percent of its people. A world in which a handful of panicky survivors choose sides -- or are chosen.', 'The_Stand.png', 2, 12);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780450417399, 'Misery', STR_TO_DATE('November 1 1988', '%M %d %Y'), 370, 'Alternate cover editions here and here.Paul Sheldon. He\'s a bestselling novelist who has finally met his biggest fan. Her name is Annie Wilkes and she is more than a rabid reader - she is Paul\'s nurse, tending his shattered body after an automobile accident. But she is also his captor, keeping him prisoner in her isolated house.', 'Misery.png', 3, 18);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780143039983, 'The Haunting of Hill House', STR_TO_DATE('November 28 2006', '%M %d %Y'), 182, 'First published in 1959, Shirley Jackson\'s The Haunting of Hill House has been hailed as a perfect work of unnerving terror. It is the story of four seekers who arrive at a notoriously unfriendly pile called Hill House: Dr. Montague, an occult scholar looking for solid evidence of a "haunting"; Theodora, the lighthearted assistant; Eleanor, a friendless, fragile young woman well acquainted with poltergeists; and Luke, the future heir of Hill House. At first, their stay seems destined to be merely a spooky encounter with inexplicable phenomena. But Hill House is gathering its powers—and soon it will choose one of them to make its own.', 'The_Haunting_of_Hill_House.png', 2, 15);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780307346605, 'World War Z: An Oral History of the Zombie War', STR_TO_DATE('September 12 2006', '%M %d %Y'), 342, 'The Zombie War came unthinkably close to eradicating humanity. Max Brooks, driven by the urgency of preserving the acid-etched first-hand experiences of the survivors from those apocalyptic years, traveled across the United States of America and throughout the world, from decimated cities that once teemed with upwards of thirty million souls to the most remote and inhospitable areas of the planet. He recorded the testimony of men, women, and sometimes children who came face-to-face with the living, or at least the undead, hell of that dreadful time. World War Z is the result. Never before have we had access to a document that so powerfully conveys the depth of fear and horror, and also the ineradicable spirit of resistance, that gripped human society through the plague years.Ranging from the now infamous village of New Dachang in the United Federation of China, where the epidemiological trail began with the twelve-year-old Patient Zero, to the unnamed northern forests where untold numb', 'World_War_Z:_An_Oral_History_of_the_Zombie_War.png', 2, 16);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780345476876, 'Interview with the Vampire', STR_TO_DATE('August 31 2004', '%M %d %Y'), 342, 'This is the story of Louis, as told in his own words, of his journey through mortal and immortal life. Louis recounts how he became a vampire at the hands of the radiant and sinister Lestat and how he became indoctrinated, unwillingly, into the vampire way of life. His story ebbs and flows through the streets of New Orleans, defining crucial moments such as his discovery of the exquisite lost young child Claudia, wanting not to hurt but to comfort her with the last breaths of humanity he has inside. Yet, he makes Claudia a vampire, trapping her womanly passion, will, and intelligence inside the body of a small child. Louis and Claudia form a seemingly unbreakable alliance and even "settle down" for a while in the opulent French Quarter. Louis remembers Claudia\'s struggle to understand herself and the hatred they both have for Lestat that sends them halfway across the world to seek others of their kind. Louis and Claudia are desperate to find somewhere they belong, to find others who ', 'Interview_with_the_Vampire.png', 2, 15);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781476727653, 'Doctor Sleep', STR_TO_DATE('September 24 2013', '%M %d %Y'), 531, 'Stephen King returns to the characters and territory of one of his most popular novels ever, The Shining, in this instantly riveting novel about the now middle-aged Dan Torrance (the boy protagonist of The Shining) and the very special 12-year-old girl he must save from a tribe of murderous paranormals.On highways across America, a tribe of people called The True Knot travel in search of sustenance. They look harmless - mostly old, lots of polyester, and married to their RVs. But as Dan Torrance knows, and spunky 12-year-old Abra Stone learns, The True Knot are quasi-immortal, living off the "steam" that children with the "shining" produce when they are slowly tortured to death.Haunted by the inhabitants of the Overlook Hotel where he spent one horrific childhood year, Dan has been drifting for decades, desperate to shed his father\'s legacy of despair, alcoholism, and violence. Finally, he settles in a New Hampshire town, an AA community that sustains him, and a job at a nursing home', 'Doctor_Sleep.png', 1, 10);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780307348241, 'Cujo', STR_TO_DATE('June 6 2006', '%M %d %Y'), 432, 'Outside a peaceful town in central Maine, a monster is waiting. Cujo is a two-hundred-pound Saint Bernard, the best friend Brett Camber has ever had. One day Cujo chases a rabbit into a bolt-hole—a cave inhabited by sick bats. What happens to Cujo, how he becomes a horrifying vortex inexorably drawing in all the people around him makes for one of the most heart-stopping novels Stephen King has written.', 'Cujo.png', 3, 12);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780062200570, 'NOS4A2', STR_TO_DATE('April 30 2013', '%M %d %Y'), 692, 'NOS4A2 is a spine-tingling novel of supernatural suspense from master of horror Joe Hill, the New York Times bestselling author of Heart-Shaped Box and Horns.Victoria McQueen has a secret gift for finding things: a misplaced bracelet, a missing photograph, answers to unanswerable questions. On her Raleigh Tuff Burner bike, she makes her way to a rickety covered bridge that, within moments, takes her wherever she needs to go, whether it’s across Massachusetts or across the country.Charles Talent Manx has a way with children. He likes to take them for rides in his 1938 Rolls-Royce Wraith with the NOS4A2 vanity plate. With his old car, he can slip right out of the everyday world, and onto the hidden roads that transport them to an astonishing – and terrifying – playground of amusements he calls “Christmasland.” Then, one day, Vic goes looking for trouble—and finds Manx. That was a lifetime ago. Now Vic, the only kid to ever escape Manx’s unmitigated evil, is all grown up and desperate to ', 'NOS4A2.png', 1, 19);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780385603102, 'House of Leaves', STR_TO_DATE('March 7 2000', '%M %d %Y'), 705, 'Years ago, when House of Leaves was first being passed around, it was nothing more than a badly bundled heap of paper, parts of which would occasionally surface on the Internet. No one could have anticipated the small but devoted following this terrifying story would soon command. Starting with an odd assortment of marginalized youth—musicians, tattoo artists, programmers, strippers, environmentalists, and adrenaline junkies—the book eventually made its way into the hands of older generations, who not only found themselves in those strangely arranged pages but also discovered a way back into the lives of their estranged children.Now, for the first time, this astonishing novel is made available in book form, complete with the original colored words, vertical footnotes, and newly added second and third appendices.The story remains unchanged, focusing on a young family that moves into a small home on Ash Tree Lane where they discover something is terribly wrong: their house is bigger on t', 'House_of_Leaves.png', 1, 12);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780061147937, 'Heart-Shaped Box', STR_TO_DATE('February 13 2007', '%M %d %Y'), 376, 'Aging, self-absorbed rock star Judas Coyne has a thing for the macabre -- his collection includes sketches from infamous serial killer John Wayne Gacy, a trepanned skull from the 16th century, a used hangman\'s noose, Aleister Crowley\'s childhood chessboard, etc. -- so when his assistant tells him about a ghost for sale on an online auction site, he immediately puts in a bid and purchases it. The black, heart-shaped box that Coyne receives in the mail not only contains the suit of a dead man but also his vengeance-obsessed spirit. The ghost, it turns out, is the stepfather of a young groupie who committed suicide after the 54-year-old Coyne callously used her up and threw her away. Now, determined to kill Coyne and anyone who aids him, the merciless ghost of Craddock McDermott begins his assault on the rocker\'s sanity.', 'Heart-Shaped_Box.png', 2, 20);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9782253147695, 'Christine', STR_TO_DATE('June 13 2001', '%M %d %Y'), 411, 'Master storyteller Stephen King presents the classic #1 national bestseller of the ultimate vehicle of terror!“This is the story of a lover’s triangle…It was bad from the start. And it got worse in a hurry.” It’s love at first sight for high school student Arnie Cunningham when he and his best friend Dennis Guilder spot the dilapidated 1958 red-and-white Plymouth Fury for sale—dubbed “Christine” by its original cantankerous owner—rusting away on a front lawn of their suburban Pennsylvania neighborhood. Dennis knows that Arnie’s never had much luck in the looks or popularity department, or really taken an interest in owning a car . . . but Christine quickly changes all that. Arnie suddenly has the newfound confidence to stick up for himself, going as far as dating the most beautiful girl at Libertyville High—transfer student Leigh Cabot—even as a mysteriously restored Christine systematically and terrifyingly consumes every aspect of Arnie’s life. Dennis and Leigh soon realize that they', 'Christine.png', 2, 16);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780450574580, 'Needful Things', STR_TO_DATE('February 17 1992', '%M %d %Y'), 790, 'Leland Gaunt opens a new shop in Castle Rock called Needful Things. Anyone who enters his store finds the object of his or her lifelong dreams and desires: a prized baseball card, a healing amulet. In addition to a token payment, Gaunt requests that each person perform a little "deed," usually a seemingly innocent prank played on someone else from town. These practical jokes cascade out of control and soon the entire town is doing battle with itself. Only Sheriff Alan Pangborn suspects that Gaunt is behind the population\'s increasingly violent behavior.', 'Needful_Things.png', 1, 19);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780061007224, 'The Exorcist', STR_TO_DATE('February 1 1994', '%M %d %Y'), 385, 'Originally published in 1971, The Exorcist is now a major television series on FOX. It remains one of the most controversial novels ever written and went on to become a literary phenomenon: It spent fifty-seven weeks on the New York Times bestseller list, seventeen consecutively at number one. Inspired by a true story of a child’s demonic possession in the 1940s, William Peter Blatty created an iconic novel that focuses on Regan, the eleven-year-old daughter of a movie actress residing in Washington, D.C. A small group of overwhelmed yet determined individuals must rescue Regan from her unspeakable fate, and the drama that ensues is gripping and unfailingly terrifying.Two years after its publication, The Exorcist was, of course, turned into a wildly popular motion picture, garnering ten Academy Award nominations. On opening day of the film, lines of the novel’s fans stretched around city blocks. In Chicago, frustrated moviegoers used a battering ram to gain entry through the double sid', 'The_Exorcist.png', 2, 14);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780143039976, 'We Have Always Lived in the Castle', STR_TO_DATE('October 31 2006', '%M %d %Y'), 146, 'My name is Mary Katherine Blackwood. I am eighteen years old, and I live with my sister Constance. I have often thought that with any luck at all I could have been born a werewolf, because the two middle fingers on both my hands are the same length, but I have had to be content with what I had. I dislike washing myself, and dogs, and noise, I like my sister Constance, and Richard Plantagenet, and Amanita phalloides, the death-cap mushroom. Everyone else in my family is dead...', 'We_Have_Always_Lived_in_the_Castle.png', 2, 14);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780451528957, 'The Strange Case of Dr. Jekyll and Mr. Hyde', STR_TO_DATE('September 2 2003', '%M %d %Y'), 144, 'Strange Case of Dr Jekyll and Mr Hyde is the original title of a novella written by the famous Scottish author Robert Louis Stevenson that was first published in 1886. The work is commonly known today as The Strange Case of Dr Jekyll and Mr Hyde, Dr Jekyll and Mr Hyde, or simply Jekyll & Hyde. It is about a London lawyer named John Gabriel Utterson who investigates strange occurrences between his old friend, Dr Henry Jekyll, and the evil Edward Hyde.', 'The_Strange_Case_of_Dr._Jekyll_and_Mr._Hyde.png', 2, 11);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781250301697, 'The Silent Patient', STR_TO_DATE('February 5 2019', '%M %d %Y'), 323, 'Alicia Berenson’s life is seemingly perfect. A famous painter married to an in-demand fashion photographer, she lives in a grand house with big windows overlooking a park in one of London’s most desirable areas. One evening her husband Gabriel returns home late from a fashion shoot, and Alicia shoots him five times in the face, and then never speaks another word.Alicia’s refusal to talk, or give any kind of explanation, turns a domestic tragedy into something far grander, a mystery that captures the public imagination and casts Alicia into notoriety. The price of her art skyrockets, and she, the silent patient, is hidden away from the tabloids and spotlight at the Grove, a secure forensic unit in North London.Theo Faber is a criminal psychotherapist who has waited a long time for the opportunity to work with Alicia. His determination to get her to talk and unravel the mystery of why she shot her husband takes him down a twisting path into his own motivations—a search for the truth that', 'The_Silent_Patient.png', 1, 12);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780385544238, 'My Sister, the Serial Killer', STR_TO_DATE('November 20 2018', '%M %d %Y'), 226, 'My Sister, the Serial Killer is a blackly comic novel about how blood is thicker - and more difficult to get out of the carpet - than water...When Korede\'s dinner is interrupted one night by a distress call from her sister, Ayoola, she knows what\'s expected of her: bleach, rubber gloves, nerves of steel and a strong stomach. This\'ll be the third boyfriend Ayoola\'s dispatched in, quote, self-defence and the third mess that her lethal little sibling has left Korede to clear away. She should probably go to the police for the good of the menfolk of Nigeria, but she loves her sister and, as they say, family always comes first. Until, that is, Ayoola starts dating the doctor where Korede works as a nurse. Korede\'s long been in love with him, and isn\'t prepared to see him wind up with a knife in his back: but to save one would mean sacrificing the other...', 'My_Sister,_the_Serial_Killer.png', 1, 15);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781501188770, 'The Turn of the Key', STR_TO_DATE('August 6 2019', '%M %d %Y'), 337, 'When she stumbles across the ad, she’s looking for something else completely. But it seems like too good an opportunity to miss—a live-in nannying post, with a staggeringly generous salary. And when Rowan Caine arrives at Heatherbrae House, she is smitten—by the luxurious “smart” home fitted out with all modern conveniences, by the beautiful Scottish Highlands, and by this picture-perfect family. What she doesn’t know is that she’s stepping into a nightmare—one that will end with a child dead and herself in prison awaiting trial for murder. Writing to her lawyer from prison, she struggles to explain the unravelling events that led to her incarceration. It wasn’t just the constant surveillance from the cameras installed around the house, or the malfunctioning technology that woke the household with booming music, or turned the lights off at the worst possible time. It wasn’t just the girls, who turned out to be a far cry from the immaculately behaved model children she met at her interv', 'The_Turn_of_the_Key.png', 1, 12);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781250105684, 'The Lost Man', STR_TO_DATE('February 5 2019', '%M %d %Y'), 340, 'Two brothers meet at the border of their vast cattle properties under the unrelenting sun of outback Queensland, in this stunning new standalone novel from New York Times bestseller Jane HarperThey are at the stockman’s grave, a landmark so old, no one can remember who is buried there. But today, the scant shadow it casts was the last hope for their middle brother, Cameron. The Bright family’s quiet existence is thrown into grief and anguish. Something had been troubling Cameron. Did he lose hope and walk to his death? Because if he didn’t, the isolation of the outback leaves few suspects…Dark, suspenseful, and deeply atmospheric, The Lost Man is the highly anticipated next book from the bestselling and award-winning Jane Harper, author of The Dry and Force of Nature.', 'The_Lost_Man.png', 1, 18);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781250133731, 'An Anonymous Girl', STR_TO_DATE('January 8 2019', '%M %d %Y'), 375, 'Seeking women ages 18–32 to participate in a study on ethics and morality. Generous compensation. Anonymity guaranteed.When Jessica Farris signs up for a psychology study conducted by the mysterious Dr. Shields, she thinks all she’ll have to do is answer a few questions, collect her money, and leave.Question #1: Could you tell a lie without feeling guilt?But as the questions grow more and more intense and invasive and the sessions become outings where Jess is told what to wear and how to act, she begins to feel as though Dr. Shields may know what she’s thinking… and what she’s hiding.Question #2: Have you ever deeply hurt someone you care about?As Jess’s paranoia grows, it becomes clear that she can no longer trust what in her life is real, and what is one of Dr. Shields’ manipulative experiments. Caught in a web of deceit and jealousy, Jess quickly learns that some obsessions can be deadly.Question #3: Should a punishment always fit the crime?An electrifying new novel about doubt, pas', 'An_Anonymous_Girl.png', 2, 14);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781250317995, 'The Whisper Man', STR_TO_DATE('August 20 2019', '%M %d %Y'), 355, 'In this dark, suspenseful thriller, Alex North weaves a multi-generational tale of a father and son caught in the crosshairs of an investigation to catch a serial killer preying on a small town.After the sudden death of his wife, Tom Kennedy believes a fresh start will help him and his young son Jake heal. A new beginning, a new house, a new town. Featherbank.But the town has a dark past. Twenty years ago, a serial killer abducted and murdered five residents. Until Frank Carter was finally caught, he was nicknamed "The Whisper Man," for he would lure his victims out by whispering at their windows at night.Just as Tom and Jake settle into their new home, a young boy vanishes. His disappearance bears an unnerving resemblance to Frank Carter\'s crimes, reigniting old rumors that he preyed with an accomplice. Now, detectives Amanda Beck and Pete Willis must find the boy before it is too late, even if that means Pete has to revisit his great foe in prison: The Whisper Man.And then Jake beg', 'The_Whisper_Man.png', 3, 12);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781538748466, 'Run Away', STR_TO_DATE('March 19 2019', '%M %d %Y'), 371, 'A perfect family is shattered in RUN AWAY, the new thriller from the master of domestic suspense, Harlan Coben.You\'ve lost your daughter.She\'s addicted to drugs and to an abusive boyfriend. And she\'s made it clear that she doesn\'t want to be found.Then, by chance, you see her playing guitar in Central Park. But she\'s not the girl you remember. This woman is living on the edge, frightened, and clearly in trouble.You don\'t stop to think. You approach her, beg her to come home.She runs. And you do the only thing a parent can do: you follow her into a dark and dangerous world you never knew existed. Before you know it, both your family and your life are on the line. And in order to protect your daughter from the evils of that world, you must face them head on.', 'Run_Away.png', 1, 11);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781524745141, 'Lock Every Door', STR_TO_DATE('July 2 2019', '%M %d %Y'), 371, 'No visitors. No nights spent away from the apartment. No disturbing the other residents, all of whom are rich or famous or both. These are the only rules for Jules Larsen\'s new job as an apartment sitter at the Bartholomew, one of Manhattan\'s most high-profile and mysterious buildings. Recently heartbroken and just plain broke, Jules is taken in by the splendor of her surroundings and accepts the terms, ready to leave her past life behind.As she gets to know the residents and staff of the Bartholomew, Jules finds herself drawn to fellow apartment sitter Ingrid, who comfortingly, disturbingly reminds her of the sister she lost eight years ago. When Ingrid confides that the Bartholomew is not what it seems and the dark history hidden beneath its gleaming facade is starting to frighten her, Jules brushes it off as a harmless ghost story—until the next day, when Ingrid disappears.Searching for the truth about Ingrid\'s disappearance, Jules digs deeper into the Bartholomew\'s dark past an', 'Lock_Every_Door.png', 1, 15);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780374156022, 'Miracle Creek', STR_TO_DATE('April 16 2019', '%M %d %Y'), 355, 'How far will you go to protect your family? Will you keep their secrets? Ignore their lies?In a small town in Virginia, a group of people know each other because they’re part of a special treatment center, a hyperbaric chamber that may cure a range of conditions from infertility to autism. But then the chamber explodes, two people die, and it’s clear the explosion wasn’t an accident.A showdown unfolds as the story moves across characters who are all maybe keeping secrets, hiding betrayals. Was it the careless mother of a patient? Was it the owners, hoping to cash in on a big insurance payment and send their daughter to college? Could it have been a protester, trying to prove the treatment isn’t safe?', 'Miracle_Creek.png', 1, 13);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781250120922, 'The Mother-in-Law', STR_TO_DATE('April 23 2019', '%M %d %Y'), 340, 'Someone once told me that you have two families in your life - the one you are born into and the one you choose. Yes, you may get to choose your partner, but you don\'t choose your mother-in-law. The cackling mercenaries of fate determine it all.From the moment Lucy met Diana, she was kept at arm\'s length. Diana is exquisitely polite, but Lucy knows, even after marrying Oliver, that they\'ll never have the closeness she\'d been hoping for.But who could fault Diana? She was a pillar of the community, an advocate for social justice, the matriarch of a loving family. Lucy had wanted so much to please her new mother-in-law.That was ten years ago. Now, Diana has been found dead, leaving a suicide note. But the autopsy reveals evidence of suffocation. And everyone in the family is hiding something...From the bestselling author of \nThe Family Next Door\n comes a new page-turner about that trickiest of relationships.', 'The_Mother-in-Law.png', 1, 14);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781250066213, 'A Better Man', STR_TO_DATE('August 27 2019', '%M %d %Y'), 437, 'Catastrophic spring flooding, blistering attacks in the media, and a mysterious disappearance greet Chief Inspector Armand Gamache as he returns to the Surete du Quebec in the latest novel by #1 New York Times bestselling author Louise Penny. It\'s Gamache\'s first day back as head of the homicide department, a job he temporarily shares with his previous second-in-command, Jean-Guy Beauvoir. Flood waters are rising across the province. In the middle of the turmoil a father approaches Gamache, pleading for help in finding his daughter.As crisis piles upon crisis, Gamache tries to hold off the encroaching chaos, and realizes the search for Vivienne Godin should be abandoned. But with a daughter of his own, he finds himself developing a profound, and perhaps unwise, empathy for her distraught father.Increasingly hounded by the question, how would you feel..., he resumes the search.As the rivers rise, and the social media onslaught against Gamache becomes crueler, a body is discovered. A', 'A_Better_Man.png', 1, 12);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781501190100, 'The Family Upstairs', STR_TO_DATE('November 5 2019', '%M %d %Y'), 340, 'An alternative cover edition for this ISBN can be found here.From the New York Times bestselling author of Then She Was Gone and Watching You comes another page-turning look inside one family’s past as buried secrets threaten to come to light. Be careful who you let in. Soon after her twenty-fifth birthday, Libby Jones returns home from work to find the letter she’s been waiting for her entire life. She rips it open with one driving thought: I am finally going to know who I am. She soon learns not only the identity of her birth parents, but also that she is the sole inheritor of their abandoned mansion on the banks of the Thames in London’s fashionable Chelsea neighborhood, worth millions. Everything in Libby’s life is about to change. But what she can’t possibly know is that others have been waiting for this day as well—and she is on a collision course to meet them. Twenty-five years ago, police were called to 16 Cheyne Walk with reports of a baby crying. When they arrived, they found', 'The_Family_Upstairs.png', 3, 15);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780316531269, 'The Chain', STR_TO_DATE('July 9 2019', '%M %d %Y'), 357, 'You just dropped off your child at the bus stop. A panicked stranger calls your phone. Your child has been kidnapped, and the stranger explains that their child has also been kidnapped, by a completely different stranger. The only way to get your child back is to kidnap another child within 24 hours. Your child will be released only when the next victim\'s parents kidnap yet another child, and most importantly, the stranger explains, if you don\'t kidnap a child, or if the next parents don\'t kidnap a child, your child will be murdered. You are now part of The Chain.', 'The_Chain.png', 3, 13);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000014, 'Disappearing Earth', STR_TO_DATE('May 14 2019', '%M %d %Y'), 264, 'Beautifully written, thought-provoking, intense and cleverly wrought, this is the most extraordinary first novel from a mesmerising new talent.One August afternoon, on the shoreline of the north-eastern edge of Russia, two sisters are abducted. In the ensuing weeks, then months, the police investigation turns up nothing. Echoes of the disappearance reverberate across a tightly woven community, with the fear and loss felt most deeply among its women.Set on the remote Siberian peninsula of Kamchatka, Disappearing Earth draws us into the world of an astonishing cast of characters, all connected by an unfathomable crime. We are transported to vistas of rugged beauty – densely wooded forests, open expanses of tundra, soaring volcanoes and the glassy seas that border Japan and Alaska – and into a region as complex as it is alluring, where social and ethnic tensions have long simmered, and where outsiders are often the first to be accused.In a story as propulsive as it is emotionally engaging', 'Disappearing_Earth.png', 3, 17);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780062834300, 'Searching for Sylvie Lee', STR_TO_DATE('June 4 2019', '%M %d %Y'), 336, 'A poignant and suspenseful drama that untangles the complicated ties binding three women—two sisters and their mother—in one Chinese immigrant family and explores what happens when the eldest daughter disappears, and a series of family secrets emerge, from the New York Times bestselling author of Girl in TranslationIt begins with a mystery. Sylvie, the beautiful, brilliant, successful older daughter of the Lee family, flies to the Netherlands for one final visit with her dying grandmother—and then vanishes.Amy, the sheltered baby of the Lee family, is too young to remember a time when her parents were newly immigrated and too poor to keep Sylvie. Seven years older, Sylvie was raised by a distant relative in a faraway, foreign place, and didn’t rejoin her family in America until age nine. Timid and shy, Amy has always looked up to her sister, the fierce and fearless protector who showered her with unconditional love.But what happened to Sylvie? Amy and her parents are distraught and des', 'Searching_for_Sylvie_Lee.png', 2, 13);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781542040358, 'I\'ll Never Tell', STR_TO_DATE('June 1 2019', '%M %d %Y'), 355, 'Deeply buried secrets make for a disturbing family reunion in bestselling author Catherine McKenzie’s tantalizing novel of psychological suspense.What happened to Amanda Holmes?Twenty years ago, she was found bludgeoned in a rowboat at the MacAllister family’s Camp Macaw. No one was ever charged with the crime.Now, after their parents’ sudden deaths, the MacAllister siblings return to camp to read the will and decide what to do with the prime real estate the camp occupies. Ryan needs to sell. Margaux hasn’t made up her mind. Mary believes in leaving well enough alone. Kate and Liddie—the twins—have opposing views. And Sean Booth, the groundskeeper, just hopes he still has a home when all is said and done.But it’s more complicated than a simple vote. The will stipulates that until they unravel the mystery of what happened to Amanda, they can’t settle the estate. Any one of them could have done it, and each one is holding a piece of the puzzle. Will they work together to finally discover', 'I\'ll_Never_Tell.png', 2, 14);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780316485616, 'The Night Fire', STR_TO_DATE('October 22 2019', '%M %d %Y'), 405, 'Harry Bosch and LAPD Detective Renee Ballard come together again on the murder case that obsessed Bosch\'s mentor, the man who trained him -- new from #1 New York Times bestselling author Michael Connelly\n\nBack when Harry Bosch was just a rookie homicide detective, he had an inspiring mentor who taught him to take the work personally and light the fire of relentlessness for every case. Now that mentor, John Jack Thompson, is dead, but after his funeral his widow hands Bosch a murder book that Thompson took with him when he left the LAPD 20 years before -- the unsolved killing of a troubled young man in an alley used for drug deals. Bosch brings the murder book to Renée Ballard and asks her to help him find what about the case lit Thompson\'s fire all those years ago. That will be their starting point. The bond between Bosch and Ballard tightens as they become a formidable investigation team. And they soon arrive at a worrying question: Did Thompson steal the murder book to work the c', 'The_Night_Fire.png', 2, 20);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780062858085, 'The Last Widow', STR_TO_DATE('August 20 2019', '%M %d %Y'), 446, ' New York Times bestselling author Karin Slaughter brings back Will Trent and Sara Linton in this superb and timely thriller full of devious twists, disturbing secrets, and shocking surprises you won’t see comingA mysterious kidnappingOn a hot summer night, a scientist from the Centers for Disease Control is grabbed by unknown assailants in a shopping center parking lot. Vanished into thin air, the authorities are desperate to save the doctor. A devastating explosionOne month later, the serenity of a sunny Sunday afternoon is shattered by the boom of a ground-shaking blast—followed by another seconds later. One of Atlanta’s busiest and most important neighborhood’s has been bombed—the location of Emory University, two major hospitals, the FBI headquarters, and the CDC.A diabolical enemyMedical examiner Sara Linton and her partner Will Trent, an investigator with the Georgia Bureau of Investigation, rush to the scene—and into the heart of a deadly conspiracy that threatens to destroy th', 'The_Last_Widow.png', 3, 16);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9780000000015, 'My Lovely Wife', STR_TO_DATE('March 26 2019', '%M %d %Y'), 374, 'Alternate cover edition of ISBN 9780451491725A couple\'s fifteen-year marriage has finally gotten too interesting...Our love story is simple. I met a gorgeous woman. We fell in love. We had kids. We moved to the suburbs. We told each other our biggest dreams, and our darkest secrets. And then we got bored.We look like a normal couple. We\'re your neighbors, the parents of your kid\'s friend, the acquaintances you keep meaning to get dinner with.We all have secrets to keeping a marriage alive.Ours just happens to be getting away with murder.', 'My_Lovely_Wife.png', 3, 16);
insert into books(isbn, title, date_of_publication, pages, synopsis, cover, wholesale_price, list_price) VALUES(9781476798622, 'The Line Between', STR_TO_DATE('January 29 2019', '%M %d %Y'), 384, 'An extinct disease re-emerges from the melting Alaskan permafrost to cause madness in its victims. For recent apocalyptic cult escapee Wynter Roth, it’s the end she’d always been told was coming.When Wynter Roth is turned out of New Earth, a self-contained doomsday cult on the American prairie, she emerges into a world poised on the brink of madness as a mysterious outbreak of rapid early onset dementia spreads across the nation.As Wynter struggles to start over in a world she’s been taught to regard as evil, she finds herself face-to-face with the apocalypse she’s feared all her life—until the night her sister shows up at her doorstep with a set of medical samples. That night, Wynter learns there’s something far more sinister at play and that these samples are key to understanding the disease.Now, as the power grid fails and the nation descends into chaos, Wynter must find a way to get the samples to a lab in Colorado. Uncertain who to trust, she takes up with former military man Chas', 'The_Line_Between.png', 1, 15);

insert into bookgenre(genre_id, isbn) VALUES(1, 9780000000000);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780765326355);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780765326362);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780765326379);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780000000001);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780439064866);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780439655484);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780000000002);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780439358071);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780439785969);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780545010221);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780316075558);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780316079914);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780000000003);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780316251334);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780316251303);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780756404079);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780756404734);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780000000004);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780000000005);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780000000006);
insert into bookgenre(genre_id, isbn) VALUES(1, 9780553804690);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780593099322);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780000000007);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780812550702);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780765377067);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780000000008);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780765377104);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780060929879);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780000000009);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780000000010);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780000000011);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780345418906);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780000000012);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780345379337);
insert into bookgenre(genre_id, isbn) VALUES(2, 9781401323585);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780804139021);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780553803709);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780385333849);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780000000013);
insert into bookgenre(genre_id, isbn) VALUES(2, 9780393312836);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781421501680);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781591169208);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781591826033);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781591169154);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781421508221);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781591164418);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781569319000);
insert into bookgenre(genre_id, isbn) VALUES(3, 9780316080842);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781569319475);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781421505855);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781892213013);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781569319017);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781421501086);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781591162209);
insert into bookgenre(genre_id, isbn) VALUES(3, 9780345470577);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781591828785);
insert into bookgenre(genre_id, isbn) VALUES(3, 9780345470584);
insert into bookgenre(genre_id, isbn) VALUES(3, 9784757518087);
insert into bookgenre(genre_id, isbn) VALUES(3, 9780345501332);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781421527550);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781413903171);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781421506234);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781612620244);
insert into bookgenre(genre_id, isbn) VALUES(3, 9781427814036);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780450040184);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780450411434);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780393970128);
insert into bookgenre(genre_id, isbn) VALUES(4, 9781416524304);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780450031069);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780385199575);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780450417399);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780143039983);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780307346605);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780345476876);
insert into bookgenre(genre_id, isbn) VALUES(4, 9781476727653);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780307348241);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780062200570);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780385603102);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780061147937);
insert into bookgenre(genre_id, isbn) VALUES(4, 9782253147695);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780450574580);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780061007224);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780143039976);
insert into bookgenre(genre_id, isbn) VALUES(4, 9780451528957);
insert into bookgenre(genre_id, isbn) VALUES(5, 9781250301697);
insert into bookgenre(genre_id, isbn) VALUES(5, 9780385544238);
insert into bookgenre(genre_id, isbn) VALUES(5, 9781501188770);
insert into bookgenre(genre_id, isbn) VALUES(5, 9781250105684);
insert into bookgenre(genre_id, isbn) VALUES(5, 9781250133731);
insert into bookgenre(genre_id, isbn) VALUES(5, 9781250317995);
insert into bookgenre(genre_id, isbn) VALUES(5, 9781538748466);
insert into bookgenre(genre_id, isbn) VALUES(5, 9781524745141);
insert into bookgenre(genre_id, isbn) VALUES(5, 9780374156022);
insert into bookgenre(genre_id, isbn) VALUES(5, 9781250120922);
insert into bookgenre(genre_id, isbn) VALUES(5, 9781250066213);
insert into bookgenre(genre_id, isbn) VALUES(5, 9781501190100);
insert into bookgenre(genre_id, isbn) VALUES(5, 9780316531269);
insert into bookgenre(genre_id, isbn) VALUES(5, 9780000000014);
insert into bookgenre(genre_id, isbn) VALUES(5, 9780062834300);
insert into bookgenre(genre_id, isbn) VALUES(5, 9781542040358);
insert into bookgenre(genre_id, isbn) VALUES(5, 9780316485616);
insert into bookgenre(genre_id, isbn) VALUES(5, 9780062858085);
insert into bookgenre(genre_id, isbn) VALUES(5, 9780000000015);
insert into bookgenre(genre_id, isbn) VALUES(5, 9781476798622);

insert into bookauthor(author_id, isbn) VALUES(1, 9780000000000);
insert into bookauthor(author_id, isbn) VALUES(2, 9780000000000);
insert into bookauthor(author_id, isbn) VALUES(3, 9780765326355);
insert into bookauthor(author_id, isbn) VALUES(3, 9780765326362);
insert into bookauthor(author_id, isbn) VALUES(3, 9780765326379);
insert into bookauthor(author_id, isbn) VALUES(4, 9780000000001);
insert into bookauthor(author_id, isbn) VALUES(5, 9780000000001);
insert into bookauthor(author_id, isbn) VALUES(4, 9780439064866);
insert into bookauthor(author_id, isbn) VALUES(5, 9780439064866);
insert into bookauthor(author_id, isbn) VALUES(4, 9780439655484);
insert into bookauthor(author_id, isbn) VALUES(5, 9780439655484);
insert into bookauthor(author_id, isbn) VALUES(4, 9780000000002);
insert into bookauthor(author_id, isbn) VALUES(5, 9780000000002);
insert into bookauthor(author_id, isbn) VALUES(4, 9780439358071);
insert into bookauthor(author_id, isbn) VALUES(5, 9780439358071);
insert into bookauthor(author_id, isbn) VALUES(4, 9780439785969);
insert into bookauthor(author_id, isbn) VALUES(5, 9780439785969);
insert into bookauthor(author_id, isbn) VALUES(4, 9780545010221);
insert into bookauthor(author_id, isbn) VALUES(6, 9780316075558);
insert into bookauthor(author_id, isbn) VALUES(6, 9780316079914);
insert into bookauthor(author_id, isbn) VALUES(6, 9780000000003);
insert into bookauthor(author_id, isbn) VALUES(6, 9780316251334);
insert into bookauthor(author_id, isbn) VALUES(6, 9780316251303);
insert into bookauthor(author_id, isbn) VALUES(7, 9780756404079);
insert into bookauthor(author_id, isbn) VALUES(7, 9780756404734);
insert into bookauthor(author_id, isbn) VALUES(8, 9780000000004);
insert into bookauthor(author_id, isbn) VALUES(9, 9780000000005);
insert into bookauthor(author_id, isbn) VALUES(9, 9780000000006);
insert into bookauthor(author_id, isbn) VALUES(9, 9780553804690);
insert into bookauthor(author_id, isbn) VALUES(10, 9780593099322);
insert into bookauthor(author_id, isbn) VALUES(11, 9780000000007);
insert into bookauthor(author_id, isbn) VALUES(12, 9780812550702);
insert into bookauthor(author_id, isbn) VALUES(13, 9780812550702);
insert into bookauthor(author_id, isbn) VALUES(14, 9780765377067);
insert into bookauthor(author_id, isbn) VALUES(14, 9780000000008);
insert into bookauthor(author_id, isbn) VALUES(14, 9780765377104);
insert into bookauthor(author_id, isbn) VALUES(15, 9780060929879);
insert into bookauthor(author_id, isbn) VALUES(16, 9780000000009);
insert into bookauthor(author_id, isbn) VALUES(17, 9780000000010);
insert into bookauthor(author_id, isbn) VALUES(17, 9780000000011);
insert into bookauthor(author_id, isbn) VALUES(17, 9780345418906);
insert into bookauthor(author_id, isbn) VALUES(17, 9780000000012);
insert into bookauthor(author_id, isbn) VALUES(17, 9780345379337);
insert into bookauthor(author_id, isbn) VALUES(18, 9781401323585);
insert into bookauthor(author_id, isbn) VALUES(19, 9780804139021);
insert into bookauthor(author_id, isbn) VALUES(20, 9780553803709);
insert into bookauthor(author_id, isbn) VALUES(21, 9780385333849);
insert into bookauthor(author_id, isbn) VALUES(22, 9780000000013);
insert into bookauthor(author_id, isbn) VALUES(23, 9780393312836);
insert into bookauthor(author_id, isbn) VALUES(24, 9781421501680);
insert into bookauthor(author_id, isbn) VALUES(25, 9781421501680);
insert into bookauthor(author_id, isbn) VALUES(26, 9781591169208);
insert into bookauthor(author_id, isbn) VALUES(27, 9781591169208);
insert into bookauthor(author_id, isbn) VALUES(28, 9781591826033);
insert into bookauthor(author_id, isbn) VALUES(29, 9781591826033);
insert into bookauthor(author_id, isbn) VALUES(30, 9781591169154);
insert into bookauthor(author_id, isbn) VALUES(31, 9781421508221);
insert into bookauthor(author_id, isbn) VALUES(32, 9781421508221);
insert into bookauthor(author_id, isbn) VALUES(33, 9781591164418);
insert into bookauthor(author_id, isbn) VALUES(34, 9781569319000);
insert into bookauthor(author_id, isbn) VALUES(35, 9781569319000);
insert into bookauthor(author_id, isbn) VALUES(36, 9780316080842);
insert into bookauthor(author_id, isbn) VALUES(32, 9780316080842);
insert into bookauthor(author_id, isbn) VALUES(37, 9781569319475);
insert into bookauthor(author_id, isbn) VALUES(38, 9781421505855);
insert into bookauthor(author_id, isbn) VALUES(32, 9781421505855);
insert into bookauthor(author_id, isbn) VALUES(39, 9781892213013);
insert into bookauthor(author_id, isbn) VALUES(40, 9781569319017);
insert into bookauthor(author_id, isbn) VALUES(41, 9781569319017);
insert into bookauthor(author_id, isbn) VALUES(42, 9781421501086);
insert into bookauthor(author_id, isbn) VALUES(43, 9781591162209);
insert into bookauthor(author_id, isbn) VALUES(44, 9781591162209);
insert into bookauthor(author_id, isbn) VALUES(45, 9780345470577);
insert into bookauthor(author_id, isbn) VALUES(46, 9780345470577);
insert into bookauthor(author_id, isbn) VALUES(45, 9781591828785);
insert into bookauthor(author_id, isbn) VALUES(47, 9781591828785);
insert into bookauthor(author_id, isbn) VALUES(45, 9780345470584);
insert into bookauthor(author_id, isbn) VALUES(48, 9780345470584);
insert into bookauthor(author_id, isbn) VALUES(49, 9784757518087);
insert into bookauthor(author_id, isbn) VALUES(50, 9780345501332);
insert into bookauthor(author_id, isbn) VALUES(48, 9780345501332);
insert into bookauthor(author_id, isbn) VALUES(51, 9781421527550);
insert into bookauthor(author_id, isbn) VALUES(32, 9781421527550);
insert into bookauthor(author_id, isbn) VALUES(52, 9781413903171);
insert into bookauthor(author_id, isbn) VALUES(53, 9781413903171);
insert into bookauthor(author_id, isbn) VALUES(54, 9781413903171);
insert into bookauthor(author_id, isbn) VALUES(55, 9781421506234);
insert into bookauthor(author_id, isbn) VALUES(56, 9781421506234);
insert into bookauthor(author_id, isbn) VALUES(57, 9781612620244);
insert into bookauthor(author_id, isbn) VALUES(58, 9781612620244);
insert into bookauthor(author_id, isbn) VALUES(59, 9781427814036);
insert into bookauthor(author_id, isbn) VALUES(60, 9780450040184);
insert into bookauthor(author_id, isbn) VALUES(60, 9780450411434);
insert into bookauthor(author_id, isbn) VALUES(61, 9780393970128);
insert into bookauthor(author_id, isbn) VALUES(62, 9780393970128);
insert into bookauthor(author_id, isbn) VALUES(60, 9781416524304);
insert into bookauthor(author_id, isbn) VALUES(60, 9780450031069);
insert into bookauthor(author_id, isbn) VALUES(60, 9780385199575);
insert into bookauthor(author_id, isbn) VALUES(60, 9780450417399);
insert into bookauthor(author_id, isbn) VALUES(63, 9780143039983);
insert into bookauthor(author_id, isbn) VALUES(64, 9780143039983);
insert into bookauthor(author_id, isbn) VALUES(65, 9780307346605);
insert into bookauthor(author_id, isbn) VALUES(66, 9780345476876);
insert into bookauthor(author_id, isbn) VALUES(60, 9781476727653);
insert into bookauthor(author_id, isbn) VALUES(60, 9780307348241);
insert into bookauthor(author_id, isbn) VALUES(67, 9780062200570);
insert into bookauthor(author_id, isbn) VALUES(68, 9780385603102);
insert into bookauthor(author_id, isbn) VALUES(67, 9780061147937);
insert into bookauthor(author_id, isbn) VALUES(60, 9782253147695);
insert into bookauthor(author_id, isbn) VALUES(60, 9780450574580);
insert into bookauthor(author_id, isbn) VALUES(69, 9780061007224);
insert into bookauthor(author_id, isbn) VALUES(63, 9780143039976);
insert into bookauthor(author_id, isbn) VALUES(70, 9780143039976);
insert into bookauthor(author_id, isbn) VALUES(71, 9780451528957);
insert into bookauthor(author_id, isbn) VALUES(72, 9780451528957);
insert into bookauthor(author_id, isbn) VALUES(73, 9781250301697);
insert into bookauthor(author_id, isbn) VALUES(74, 9780385544238);
insert into bookauthor(author_id, isbn) VALUES(75, 9781501188770);
insert into bookauthor(author_id, isbn) VALUES(76, 9781250105684);
insert into bookauthor(author_id, isbn) VALUES(77, 9781250133731);
insert into bookauthor(author_id, isbn) VALUES(78, 9781250317995);
insert into bookauthor(author_id, isbn) VALUES(79, 9781538748466);
insert into bookauthor(author_id, isbn) VALUES(80, 9781524745141);
insert into bookauthor(author_id, isbn) VALUES(81, 9780374156022);
insert into bookauthor(author_id, isbn) VALUES(82, 9781250120922);
insert into bookauthor(author_id, isbn) VALUES(83, 9781250066213);
insert into bookauthor(author_id, isbn) VALUES(84, 9781501190100);
insert into bookauthor(author_id, isbn) VALUES(85, 9780316531269);
insert into bookauthor(author_id, isbn) VALUES(86, 9780000000014);
insert into bookauthor(author_id, isbn) VALUES(87, 9780062834300);
insert into bookauthor(author_id, isbn) VALUES(88, 9781542040358);
insert into bookauthor(author_id, isbn) VALUES(89, 9780316485616);
insert into bookauthor(author_id, isbn) VALUES(90, 9780062858085);
insert into bookauthor(author_id, isbn) VALUES(91, 9780000000015);
insert into bookauthor(author_id, isbn) VALUES(92, 9781476798622);

INSERT INTO users(user_id,title,first_name,last_name,company_name,address_1,address_2,city,province,country,postal_code,home_phone,cell_phone,email, password, is_manager) VALUES( '1', 'Mr', 'John', 'Doe', 'Invera', '123 road avenue', 'second address', 'montreal', 'QC', 'Canada', 'h1h1h1', '0000000000', '1111111111', 'cst.send@gmail.com', 'dawsoncollege', false);
INSERT INTO users(user_id,title,first_name,last_name,company_name,address_1,address_2,city,province,country,postal_code,home_phone,cell_phone,email, password, is_manager) VALUES( '2', 'Mrs', 'Jane', 'Doe', 'Bookify', '456 avenue boulevard', '', 'toronto', 'TO', 'Canada', 'h2h2h2', '3333333333', '4444444444', 'cst.receive@gmail.com', 'collegedawson', true);
ALTER TABLE users AUTO_INCREMENT=3;

insert into orders(order_id, user_id, billing_address) VALUES( 1, 1, '123 road avenue');
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780143039983, 15, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780062858085, 16, null, 5, 6, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780316531269, 13, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780439358071, 16, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780345418906, 14, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780316251303, 16, null, 5, 9.975, false );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9781612620244, 18, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780345470584, 14, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9781421527550, 18, null, 5, 6, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780143039976, 14, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780316080842, 20, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9781542040358, 14, null, 5, 6, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780000000008, 16, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780316079914, 12, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9781421506234, 16, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780765326379, 10, null, 5, 6, false );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9781569319475, 19, null, 5, 6, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 1, 9780345470577, 17, 13, null, null, true );

insert into orders(order_id, user_id, billing_address) VALUES( 2, 2, '456 avenue boulevard');
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9782253147695, 16, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781524745141, 15, null, 5, 6, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9780553803709, 18, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9780316251303, 16, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781250066213, 12, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781591169208, 16, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9780000000015, 16, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781538748466, 11, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781427814036, 19, null, 5, 6, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781591162209, 14, 13, null, null, false );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781501190100, 15, null, 5, 6, false );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781250301697, 12, null, 5, 6, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9780812550702, 16, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9780385333849, 20, null, 5, 6, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9780804139021, 14, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781476727653, 10, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781612620244, 18, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781250120922, 14, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781421505855, 18, null, 5, 9.975, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781250133731, 14, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9780000000001, 14, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781413903171, 18, null, 5, 6, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9780000000011, 18, 13, null, null, true );
insert into bookorder(order_id, isbn, amount_paid_pretax, HST_TAX, PST_TAX, GST_TAX, enabled) VALUES( 2, 9781892213013, 12, 13, null, null, false );

insert into file_formats(file_format_id, format) VALUES(1, "epub");
insert into file_formats(file_format_id, format) VALUES(2, "pdf");
insert into file_formats(file_format_id, format) VALUES(3, "mobi");
ALTER TABLE file_formats AUTO_INCREMENT=4;

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780000000000, './Good_Omens:_The_Nice_and_Accurate_Prophecies_of_Agnes_Nutter,_Witch.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780000000000, './Good_Omens:_The_Nice_and_Accurate_Prophecies_of_Agnes_Nutter,_Witch.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000000, './Good_Omens:_The_Nice_and_Accurate_Prophecies_of_Agnes_Nutter,_Witch.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780765326355, './The_Way_of_Kings.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780765326355, './The_Way_of_Kings.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780765326362, './Words_of_Radiance.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780765326362, './Words_of_Radiance.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780765326379, './Oathbringer.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780765326379, './Oathbringer.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780765326379, './Oathbringer.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000001, './Harry_Potter_and_the_Sorcerer\'s_Stone.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780000000001, './Harry_Potter_and_the_Sorcerer\'s_Stone.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780439064866, './Harry_Potter_and_the_Chamber_of_Secrets.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780439064866, './Harry_Potter_and_the_Chamber_of_Secrets.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780439064866, './Harry_Potter_and_the_Chamber_of_Secrets.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780439655484, './Harry_Potter_and_the_Prisoner_of_Azkaban.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780439655484, './Harry_Potter_and_the_Prisoner_of_Azkaban.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780439655484, './Harry_Potter_and_the_Prisoner_of_Azkaban.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780000000002, './Harry_Potter_and_the_Goblet_of_Fire.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780000000002, './Harry_Potter_and_the_Goblet_of_Fire.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000002, './Harry_Potter_and_the_Goblet_of_Fire.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780439358071, './Harry_Potter_and_the_Order_of_the_Phoenix.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780439358071, './Harry_Potter_and_the_Order_of_the_Phoenix.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780439358071, './Harry_Potter_and_the_Order_of_the_Phoenix.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780439785969, './Harry_Potter_and_the_Half-Blood_Prince.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780545010221, './Harry_Potter_and_the_Deathly_Hallows.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780545010221, './Harry_Potter_and_the_Deathly_Hallows.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780316075558, './The_Black_Prism.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780316079914, './The_Blinding_Knife.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000003, './The_Broken_Eye.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780316251334, './The_Blood_Mirror.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780316251334, './The_Blood_Mirror.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780316251303, './The_Burning_White.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780756404079, './The_Name_of_the_Wind.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780756404079, './The_Name_of_the_Wind.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780756404079, './The_Name_of_the_Wind.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780756404734, './The_Wise_Man\'s_Fear.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780756404734, './The_Wise_Man\'s_Fear.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000004, './The_Priory_of_the_Orange_Tree.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780000000004, './The_Priory_of_the_Orange_Tree.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000005, './The_Lies_of_Locke_Lamora.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000006, './Red_Seas_Under_Red_Skies.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780000000006, './Red_Seas_Under_Red_Skies.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780553804690, './The_Republic_of_Thieves.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780593099322, './Dune.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780000000007, './1984.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780000000007, './1984.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000007, './1984.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780812550702, './Ender\'s_Game.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780765377067, './The_Three-Body_Problem.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780000000008, './The_Dark_Forest.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780765377104, './Death\'s_End.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780765377104, './Death\'s_End.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780765377104, './Death\'s_End.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780060929879, './Brave_New_World.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780060929879, './Brave_New_World.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780060929879, './Brave_New_World.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780000000009, './Fahrenheit_451.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780000000009, './Fahrenheit_451.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000009, './Fahrenheit_451.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780000000010, './The_Hitchhiker\'s_Guide_to_the_Galaxy.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000010, './The_Hitchhiker\'s_Guide_to_the_Galaxy.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780000000010, './The_Hitchhiker\'s_Guide_to_the_Galaxy.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780000000011, './The_Restaurant_at_the_End_of_the_Universe.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780345418906, './Life,_the_Universe_and_Everything.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780345418906, './Life,_the_Universe_and_Everything.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000012, './So_Long,_and_Thanks_for_All_the_Fish.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780345379337, './Mostly_Harmless.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781401323585, './And_Another_Thing....pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781401323585, './And_Another_Thing....epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780804139021, './The_Martian.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780804139021, './The_Martian.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780553803709, './I,_Robot.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780385333849, './Slaughterhouse-Five.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780385333849, './Slaughterhouse-Five.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780385333849, './Slaughterhouse-Five.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780000000013, './Flowers_for_Algernon.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780393312836, './A_Clockwork_Orange.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780393312836, './A_Clockwork_Orange.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780393312836, './A_Clockwork_Orange.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781421501680, './Death_Note,_Vol._1:_Boredom.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781591169208, './Fullmetal_Alchemist,_Vol._1.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781591826033, './Fruits_Basket,_Vol._1.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781591826033, './Fruits_Basket,_Vol._1.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781591826033, './Fruits_Basket,_Vol._1.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781591169154, './Ouran_High_School_Host_Club,_Vol._1.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781591169154, './Ouran_High_School_Host_Club,_Vol._1.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781421508221, './Vampire_Knight,_Vol._1.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781421508221, './Vampire_Knight,_Vol._1.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781591164418, './Bleach,_Volume_01.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781569319000, './Naruto,_Vol._01:_Uzumaki_Naruto.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781569319000, './Naruto,_Vol._01:_Uzumaki_Naruto.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781569319000, './Naruto,_Vol._01:_Uzumaki_Naruto.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780316080842, './Black_Butler,_Vol._1.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781569319475, './InuYasha:_Turning_Back_Time.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781569319475, './InuYasha:_Turning_Back_Time.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781421505855, './Skip_Beat!,_Vol._01.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781421505855, './Skip_Beat!,_Vol._01.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781892213013, './Sailor_Moon,_Vol._1.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781892213013, './Sailor_Moon,_Vol._1.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781569319017, './One_Piece,_Volume_1:_Romance_Dawn.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781569319017, './One_Piece,_Volume_1:_Romance_Dawn.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781421501086, './Nana,_Vol._1.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781421501086, './Nana,_Vol._1.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781421501086, './Nana,_Vol._1.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781591162209, './Rurouni_Kenshin,_Volume_01.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780345470577, './Tsubasa:_RESERVoir_CHRoNiCLE,_Vol._1.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781591828785, './Cardcaptor_Sakura,_Vol._1.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780345470584, './xxxHolic,_Vol._1.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9784757518087, './Pandora_Hearts_1巻.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780345501332, './Fairy_Tail,_Vol._01.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780345501332, './Fairy_Tail,_Vol._01.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781421527550, './Kimi_ni_Todoke:_From_Me_to_You,_Vol._1.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781421527550, './Kimi_ni_Todoke:_From_Me_to_You,_Vol._1.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781413903171, './Yotsuba&!,_Vol._1.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781421506234, './D.Gray-man,_Vol._1.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781612620244, './Attack_on_Titan,_Vol._1.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781612620244, './Attack_on_Titan,_Vol._1.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781612620244, './Attack_on_Titan,_Vol._1.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781427814036, './Maid-sama!_Vol._01.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781427814036, './Maid-sama!_Vol._01.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780450040184, './The_Shining.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780450411434, './It.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780393970128, './Dracula.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780393970128, './Dracula.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781416524304, './Carrie.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780450031069, './\'Salem\'s_Lot.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780450031069, './\'Salem\'s_Lot.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780450031069, './\'Salem\'s_Lot.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780385199575, './The_Stand.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780385199575, './The_Stand.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780450417399, './Misery.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780450417399, './Misery.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780143039983, './The_Haunting_of_Hill_House.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780307346605, './World_War_Z:_An_Oral_History_of_the_Zombie_War.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780345476876, './Interview_with_the_Vampire.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780345476876, './Interview_with_the_Vampire.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780345476876, './Interview_with_the_Vampire.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781476727653, './Doctor_Sleep.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780307348241, './Cujo.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780062200570, './NOS4A2.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780062200570, './NOS4A2.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780062200570, './NOS4A2.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780385603102, './House_of_Leaves.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780385603102, './House_of_Leaves.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780061147937, './Heart-Shaped_Box.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9782253147695, './Christine.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780450574580, './Needful_Things.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780450574580, './Needful_Things.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780061007224, './The_Exorcist.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780061007224, './The_Exorcist.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780061007224, './The_Exorcist.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780143039976, './We_Have_Always_Lived_in_the_Castle.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780451528957, './The_Strange_Case_of_Dr._Jekyll_and_Mr._Hyde.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780451528957, './The_Strange_Case_of_Dr._Jekyll_and_Mr._Hyde.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780451528957, './The_Strange_Case_of_Dr._Jekyll_and_Mr._Hyde.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781250301697, './The_Silent_Patient.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780385544238, './My_Sister,_the_Serial_Killer.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780385544238, './My_Sister,_the_Serial_Killer.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780385544238, './My_Sister,_the_Serial_Killer.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781501188770, './The_Turn_of_the_Key.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781501188770, './The_Turn_of_the_Key.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781250105684, './The_Lost_Man.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781250133731, './An_Anonymous_Girl.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781250133731, './An_Anonymous_Girl.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781250317995, './The_Whisper_Man.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781538748466, './Run_Away.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781524745141, './Lock_Every_Door.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781524745141, './Lock_Every_Door.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781524745141, './Lock_Every_Door.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780374156022, './Miracle_Creek.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780374156022, './Miracle_Creek.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781250120922, './The_Mother-in-Law.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9781250120922, './The_Mother-in-Law.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781250066213, './A_Better_Man.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781250066213, './A_Better_Man.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781501190100, './The_Family_Upstairs.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780316531269, './The_Chain.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780000000014, './Disappearing_Earth.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000014, './Disappearing_Earth.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780000000014, './Disappearing_Earth.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780062834300, './Searching_for_Sylvie_Lee.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780062834300, './Searching_for_Sylvie_Lee.epub');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781542040358, './I\'ll_Never_Tell.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9781542040358, './I\'ll_Never_Tell.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780316485616, './The_Night_Fire.pdf');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780316485616, './The_Night_Fire.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780316485616, './The_Night_Fire.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780062858085, './The_Last_Widow.mobi');

insert into book_files(file_format_id, isbn, file_location) VALUES(3, 9780000000015, './My_Lovely_Wife.mobi');
insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9780000000015, './My_Lovely_Wife.epub');
insert into book_files(file_format_id, isbn, file_location) VALUES(2, 9780000000015, './My_Lovely_Wife.pdf');

insert into book_files(file_format_id, isbn, file_location) VALUES(1, 9781476798622, './The_Line_Between.epub');

insert into reviews(user_id, isbn, rating, review) VALUES( 1, 9780000000008, 1, 'The Dark Forest: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 1, 9780316531269, 1, 'The Chain: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 1, 9781421527550, 3, 'Kimi ni Todoke: From Me to You, Vol. 1: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 1, 9780765326379, 3, 'Oathbringer: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 1, 9780316079914, 4, 'The Blinding Knife: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 1, 9780062858085, 4, 'The Last Widow: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 1, 9781542040358, 2, 'I\'ll Never Tell: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 1, 9780345470577, 1, 'Tsubasa: RESERVoir CHRoNiCLE, Vol. 1: test review' );

insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9781501190100, 5, 'The Family Upstairs: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9780553803709, 1, 'I, Robot: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9781612620244, 3, 'Attack on Titan, Vol. 1: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9781591169208, 5, 'Fullmetal Alchemist, Vol. 1: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9780000000001, 5, 'Harry Potter and the Sorcerer\'s Stone: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9781250066213, 2, 'A Better Man: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9781421505855, 2, 'Skip Beat!, Vol. 01: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9781892213013, 3, 'Sailor Moon, Vol. 1: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9781591162209, 4, 'Rurouni Kenshin, Volume 01: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9781524745141, 5, 'Lock Every Door: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9781250301697, 2, 'The Silent Patient: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9781427814036, 1, 'Maid-sama! Vol. 01: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9781476727653, 2, 'Doctor Sleep: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9781250120922, 3, 'The Mother-in-Law: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9780000000011, 1, 'The Restaurant at the End of the Universe: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9780316251303, 4, 'The Burning White: test review' );
insert into reviews(user_id, isbn, rating, review) VALUES( 2, 9780385333849, 4, 'Slaughterhouse-Five: test review' );

