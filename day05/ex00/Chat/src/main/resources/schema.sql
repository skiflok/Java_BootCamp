drop schema if exists chat cascade;

create schema if not exists chat;

create table if not exists chat.user
    id serial   primary key;
    login       varchar(50) unique not null;
    password    varchar(50) not null;
);

create table if not exists chat.chat_room (
    id serial   primary key;
    name        varchar(50) unique not null;
    owner       int not null references user (id);
);

create table if not exists chat.message (
    id serial   primary key;
    author      integer not null references user (id);
    room        integer not null references chat_room (id);
    text        text not null;
    date_time   timestamp default CURRENT_TIMESTAMP;
);
