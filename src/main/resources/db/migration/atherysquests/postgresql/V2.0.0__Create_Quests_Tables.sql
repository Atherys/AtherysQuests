create schema if not exists atherysquests;

create table atherysquests.attempted_quest (
    id bigserial not null,
    first_timestamp int8,
    questId varchar(255),
    times_completed int4,
    timestamp int8,
    primary key (id)
);

create table atherysquests.Quester_ongoingQuests (
    SimpleQuester_id uuid not null,
    ongoingQuests text
);

create table atherysquests.QuestLocation (
    id bigserial not null,
    location varchar(255),
    questId varchar(255),
    radius float8 not null,
    type int4,
    primary key (id)
);

create table atherysquests.SimpleQuester (id uuid not null, primary key (id));