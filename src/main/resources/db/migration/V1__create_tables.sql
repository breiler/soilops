create table user (
    id integer primary key autoincrement,
    name varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    email varchar(255) not null,
    provider varchar(255) not null,
    image_url varchar(255)
);

create unique index idx_user_username on user (username);

create table device (
    id integer primary key autoincrement,
    place_id integer not null,
    uuid varchar(255) not null,
    key varchar(255),
    created timestamp not null,
    name varchar(255) not null
);
create unique index idx_device_uuid on device (uuid);

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
    device_id integer not null,
    uuid varchar(255) not null,
    created timestamp not null,
    temperature decimal(10,3) not null,
    moisture decimal(10,3) not null,
    light decimal(10,3) not null
);
create unique index idx_observation_uuid on observation (uuid);
