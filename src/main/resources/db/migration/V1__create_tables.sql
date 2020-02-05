create table user (
    id integer primary key autoincrement,
    username varchar(255) not null,
    password varchar(255) not null
);

create unique index idx_user_username on user (username);

create table thing (
    id integer primary key autoincrement,
    place_id integer not null,
    uuid varchar(255) not null,
    created timestamp not null,
    name varchar(255) not null
);
create unique index idx_thing_uuid on thing (uuid);

create table place (
    id integer primary key autoincrement,
    user_id integer not null,
    uuid varchar(255) not null,
    created timestamp not null,
    name varchar(255) not null
);
create unique index idx_place_uuid on place (uuid);

create table observation (
    id integer primary key autoincrement,
    thing_id integer not null,
    uuid varchar(255) not null,
    created timestamp not null,
    temperature decimal(10,3) not null,
    moisture decimal(10,3) not null,
    light decimal(10,3) not null
);
create unique index idx_observation_uuid on observation (uuid);
