drop schema if exists chat cascade;

create schema if not exists chat;

create table if not exists chat.user (
    id          bigserial primary key,
    login       varchar(50) unique not null,
    password    varchar(50) not null
);

create table if not exists chat.chat_room (
    id          bigserial primary key,
    name        varchar(50) unique not null,
    owner       bigint not null references chat.user (id)
);

create table if not exists chat.message (
    id          bigserial primary key,
    author      bigint not null references chat.user (id),
    room        bigint not null references chat.chat_room (id),
    text        text not null,
    date_time   timestamp default CURRENT_TIMESTAMP
);
