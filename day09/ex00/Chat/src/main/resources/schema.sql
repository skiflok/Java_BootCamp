drop schema if exists chat cascade;

create schema if not exists chat;

create table if not exists chat.users (
    id        bigserial primary key,
    name     varchar(255) unique not null,
    password  varchar(255)
);
