drop schema if exists chat cascade;

create schema if not exists chat;

create table if not exists chat.users (
    id    bigserial primary key,
    email varchar(255)
);
