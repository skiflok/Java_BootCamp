insert into chat.user (login, password)
values  ('Cornelia Sisk', '88888888'),
        ('Ilene Brant', 'motorola'),
        ('Gretta Fournier', 'turtle'),
        ('Stuart Gilson', 'sandra'),
        ('Sylvia Howard', 'doctor'),
        ('Teresita Song', 'maximus'),
        ('Leonarda Knowlton', 'december'),
        ('Allene Sylvester', 'vision'),
        ('Yuki Varner', 'passw0rd'),
        ('Bernardine Settles', 'heaven'),
ON CONFLICT (login) DO NOTHING;

-- https://www.coderstool.com/sql-test-data-generator