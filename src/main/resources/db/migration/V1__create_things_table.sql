create table thing (
    id integer primary key autoincrement,
    place_id integer not null,
    uuid varchar(255) not null,
    name varchar(255) not null
);

create table place (
    id integer primary key autoincrement,
    uuid varchar(255) not null,
    name varchar(255) not null
);