insert into user (id, username, password) values (1, 'breiler', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6');
insert into place (id, created, user_id, uuid, name) values (2, '2020-01-01 01:00:00.000', 1, '1234', 'My first place');
insert into thing (id, created, place_id, uuid, name) values (3, '2020-01-01 01:00:00.000', 2, '1234', 'Thing 1');
insert into observation (id, thing_id, uuid, created, temperature, moisture, light) values (3, 3, '1234', '2020-01-01 00:00:00.000', 21.5, 55.2, 60);
insert into observation (id, thing_id, uuid, created, temperature, moisture, light) values (4, 3, '2345', '2020-01-01 01:00:00.000', 22.5, 54.2, 70);
insert into observation (id, thing_id, uuid, created, temperature, moisture, light) values (5, 3, '3456', '2020-01-01 02:00:00.000', 23.5, 53.2, 100);

insert into place (id, created, user_id, uuid, name) values (4, '2020-01-01 01:00:00.000', 1, '4321', 'My second place');
insert into thing (id, created, place_id, uuid, name) values (5, '2020-01-01 01:00:00.000', 4, '43211', 'Second thing 1');
insert into thing (id, created, place_id, uuid, name) values (6, '2020-01-01 01:00:00.000', 4, '43212', 'Second thing 2');

insert into user (id, username, password) values (10, 'muckel', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6');
insert into place (id, created, user_id, uuid, name) values (11, '2020-01-01 01:00:00.000', 10,   '5432', 'My first place');
insert into thing (id, created, place_id, uuid, name) values (12, '2020-01-01 01:00:00.000', 11, '5432', 'Thing 1');
