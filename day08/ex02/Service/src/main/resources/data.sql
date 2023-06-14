--INSERT INTO chat.users (email)
--SELECT CONCAT('user', seq.num, '@example.com')
--FROM (
--    SELECT 1 AS num UNION ALL
--    SELECT 2 AS num UNION ALL
--    SELECT 3 AS num UNION ALL
--    SELECT 4 AS num UNION ALL
--    SELECT 5 AS num UNION ALL
--    SELECT 6 AS num UNION ALL
--    SELECT 7 AS num UNION ALL
--    SELECT 8 AS num UNION ALL
--    SELECT 9 AS num UNION ALL
--    SELECT 10 AS num
--) AS seq;

insert into chat.users(email, password) values
('user1@gmail.com', 'Ys*#coyS)3'),
('user2@gmail.com', 'wgILmT1EgT'),
('user3@gmail.com', 'wLzY#TSs18'),
('user4@gmail.com', 'qv#2SxrfXw'),
('user5@gmail.com', 'LyG2@1fOMk'),
('user6@gmail.com', 'Ys*#coyS)3'),
('user7@gmail.com', 'wgILmT1EgT'),
('user8@gmail.com', 'wLzY#TSs18'),
('user9@gmail.com', 'qv#2SxrfXw'),
('user10@gmail.com', 'LyG2@1fOMk');