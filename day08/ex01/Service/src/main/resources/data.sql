INSERT INTO chat.users (email)
SELECT CONCAT('user', seq.num, '@example.com')
FROM (
    SELECT 1 AS num UNION ALL
    SELECT 2 AS num UNION ALL
    SELECT 3 AS num UNION ALL
    SELECT 4 AS num UNION ALL
    SELECT 5 AS num UNION ALL
    SELECT 6 AS num UNION ALL
    SELECT 7 AS num UNION ALL
    SELECT 8 AS num UNION ALL
    SELECT 9 AS num UNION ALL
    SELECT 10 AS num
) AS seq;