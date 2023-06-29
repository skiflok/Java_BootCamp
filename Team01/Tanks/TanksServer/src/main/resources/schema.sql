drop schema if exists game cascade;
create schema if not exists game;

create table if not exists game.statistics (
  id serial not null,

  playerId bigint not null,
  playerShotsCount bigint,
  playerHitCount bigint,
  playerBoboCount bigint,

  enemyId bigint not null,
  enemyShotsCount bigint,
  enemyHitCount bigint,
  enemyBoboCount bigint
);
