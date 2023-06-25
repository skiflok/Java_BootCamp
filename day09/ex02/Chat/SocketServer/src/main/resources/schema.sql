drop schema if exists chat cascade;

create schema if not exists chat;

create table if not exists chat.users (
    id        bigserial primary key,
    name     varchar(255) unique not null,
    password  varchar(255)
);

create table if not exists chat.message (
    id          bigserial primary key,
    author      bigint not null references chat.users (id),
    text        text not null,
    date_time   timestamp default CURRENT_TIMESTAMP
);