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
        ('Bernardine Settles', 'heaven')
ON CONFLICT (login) DO NOTHING;

insert into chat.chat_room (name, owner)
values  ('dennis', '9'),
        ('notes', '7'),
        ('rug', '7'),
        ('contains', '1'),
        ('fort', '4'),
        ('fist', '1'),
        ('teams', '5'),
        ('orbit', '5'),
        ('palestinian', '9'),
        ('calcium', '4')
ON CONFLICT (name) DO NOTHING;

insert into chat.message (author, room, text, date_time)
values   ('2', '2', 'acne wheel jpg vic goal', '2023-03-11'),
         ('9', '4', 'cage victims ccd plugin twins', '2023-04-15'),
         ('6', '3', 'elections bend wagon happiness int', '2023-03-19'),
         ('5', '9', 'ld reading lyric bras apparel', '2023-03-26'),
         ('6', '2', 'briefly consists delicious team prefer', '2023-01-07'),
         ('1', '9', 'sega hurt smilies virtually filter', '2023-01-17'),
         ('5', '6', 'declined ears activity seattle injured', '2023-05-08'),
         ('8', '9', 'continue white eve sustainable actually', '2023-02-07'),
         ('7', '2', 'inflation separate consortium casual apnic', '2023-04-27'),
         ('2', '9', 'vc featuring coffee males attitude', '2023-04-29');
