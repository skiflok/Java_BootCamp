--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3 (Debian 15.3-1.pgdg110+1)
-- Dumped by pg_dump version 15.3 (Debian 15.3-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: chat; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA chat;


ALTER SCHEMA chat OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: chat_room; Type: TABLE; Schema: chat; Owner: postgres
--

CREATE TABLE chat.chat_room (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    owner integer NOT NULL
);


ALTER TABLE chat.chat_room OWNER TO postgres;

--
-- Name: chat_room_id_seq; Type: SEQUENCE; Schema: chat; Owner: postgres
--

CREATE SEQUENCE chat.chat_room_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE chat.chat_room_id_seq OWNER TO postgres;

--
-- Name: chat_room_id_seq; Type: SEQUENCE OWNED BY; Schema: chat; Owner: postgres
--

ALTER SEQUENCE chat.chat_room_id_seq OWNED BY chat.chat_room.id;


--
-- Name: message; Type: TABLE; Schema: chat; Owner: postgres
--

CREATE TABLE chat.message (
    id integer NOT NULL,
    author integer NOT NULL,
    room integer NOT NULL,
    text text NOT NULL,
    date_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE chat.message OWNER TO postgres;

--
-- Name: message_id_seq; Type: SEQUENCE; Schema: chat; Owner: postgres
--

CREATE SEQUENCE chat.message_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE chat.message_id_seq OWNER TO postgres;

--
-- Name: message_id_seq; Type: SEQUENCE OWNED BY; Schema: chat; Owner: postgres
--

ALTER SEQUENCE chat.message_id_seq OWNED BY chat.message.id;


--
-- Name: user; Type: TABLE; Schema: chat; Owner: postgres
--

CREATE TABLE chat."user" (
    id integer NOT NULL,
    login character varying(50) NOT NULL,
    password character varying(50) NOT NULL
);


ALTER TABLE chat."user" OWNER TO postgres;

--
-- Name: user_id_seq; Type: SEQUENCE; Schema: chat; Owner: postgres
--

CREATE SEQUENCE chat.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE chat.user_id_seq OWNER TO postgres;

--
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: chat; Owner: postgres
--

ALTER SEQUENCE chat.user_id_seq OWNED BY chat."user".id;


--
-- Name: chat_room id; Type: DEFAULT; Schema: chat; Owner: postgres
--

ALTER TABLE ONLY chat.chat_room ALTER COLUMN id SET DEFAULT nextval('chat.chat_room_id_seq'::regclass);


--
-- Name: message id; Type: DEFAULT; Schema: chat; Owner: postgres
--

ALTER TABLE ONLY chat.message ALTER COLUMN id SET DEFAULT nextval('chat.message_id_seq'::regclass);


--
-- Name: user id; Type: DEFAULT; Schema: chat; Owner: postgres
--

ALTER TABLE ONLY chat."user" ALTER COLUMN id SET DEFAULT nextval('chat.user_id_seq'::regclass);


--
-- Data for Name: chat_room; Type: TABLE DATA; Schema: chat; Owner: postgres
--

COPY chat.chat_room (id, name, owner) FROM stdin;
1	dennis	9
2	notes	7
3	rug	7
4	contains	1
5	fort	4
6	fist	1
7	teams	5
8	orbit	5
9	palestinian	9
10	calcium	4
\.


--
-- Data for Name: message; Type: TABLE DATA; Schema: chat; Owner: postgres
--

COPY chat.message (id, author, room, text, date_time) FROM stdin;
1	2	2	acne wheel jpg vic goal	2023-03-11 00:00:00
2	9	4	cage victims ccd plugin twins	2023-04-15 00:00:00
3	6	3	elections bend wagon happiness int	2023-03-19 00:00:00
4	5	9	ld reading lyric bras apparel	2023-03-26 00:00:00
5	6	2	briefly consists delicious team prefer	2023-01-07 00:00:00
6	1	9	sega hurt smilies virtually filter	2023-01-17 00:00:00
7	5	6	declined ears activity seattle injured	2023-05-08 00:00:00
8	8	9	continue white eve sustainable actually	2023-02-07 00:00:00
9	7	2	inflation separate consortium casual apnic	2023-04-27 00:00:00
10	2	9	vc featuring coffee males attitude	2023-04-29 00:00:00
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: chat; Owner: postgres
--

COPY chat."user" (id, login, password) FROM stdin;
1	Cornelia Sisk	88888888
2	Ilene Brant	motorola
3	Gretta Fournier	turtle
4	Stuart Gilson	sandra
5	Sylvia Howard	doctor
6	Teresita Song	maximus
7	Leonarda Knowlton	december
8	Allene Sylvester	vision
9	Yuki Varner	passw0rd
10	Bernardine Settles	heaven
\.


--
-- Name: chat_room_id_seq; Type: SEQUENCE SET; Schema: chat; Owner: postgres
--

SELECT pg_catalog.setval('chat.chat_room_id_seq', 10, true);


--
-- Name: message_id_seq; Type: SEQUENCE SET; Schema: chat; Owner: postgres
--

SELECT pg_catalog.setval('chat.message_id_seq', 10, true);


--
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: chat; Owner: postgres
--

SELECT pg_catalog.setval('chat.user_id_seq', 20, true);


--
-- Name: chat_room chat_room_name_key; Type: CONSTRAINT; Schema: chat; Owner: postgres
--

ALTER TABLE ONLY chat.chat_room
    ADD CONSTRAINT chat_room_name_key UNIQUE (name);


--
-- Name: chat_room chat_room_pkey; Type: CONSTRAINT; Schema: chat; Owner: postgres
--

ALTER TABLE ONLY chat.chat_room
    ADD CONSTRAINT chat_room_pkey PRIMARY KEY (id);


--
-- Name: message message_pkey; Type: CONSTRAINT; Schema: chat; Owner: postgres
--

ALTER TABLE ONLY chat.message
    ADD CONSTRAINT message_pkey PRIMARY KEY (id);


--
-- Name: user user_login_key; Type: CONSTRAINT; Schema: chat; Owner: postgres
--

ALTER TABLE ONLY chat."user"
    ADD CONSTRAINT user_login_key UNIQUE (login);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: chat; Owner: postgres
--

ALTER TABLE ONLY chat."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- Name: chat_room chat_room_owner_fkey; Type: FK CONSTRAINT; Schema: chat; Owner: postgres
--

ALTER TABLE ONLY chat.chat_room
    ADD CONSTRAINT chat_room_owner_fkey FOREIGN KEY (owner) REFERENCES chat."user"(id);


--
-- Name: message message_author_fkey; Type: FK CONSTRAINT; Schema: chat; Owner: postgres
--

ALTER TABLE ONLY chat.message
    ADD CONSTRAINT message_author_fkey FOREIGN KEY (author) REFERENCES chat."user"(id);


--
-- Name: message message_room_fkey; Type: FK CONSTRAINT; Schema: chat; Owner: postgres
--

ALTER TABLE ONLY chat.message
    ADD CONSTRAINT message_room_fkey FOREIGN KEY (room) REFERENCES chat.chat_room(id);


--
-- PostgreSQL database dump complete
--

