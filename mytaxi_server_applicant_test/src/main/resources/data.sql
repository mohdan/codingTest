/**
 * CREATE Script for init of DB
 */

-- Create 4 manufacturer

insert into manufacturer(id, date_created, deleted, name, model) values (1, now(), false, 'BMW', 'BMWModel1');

insert into manufacturer(id, date_created, deleted, name, model) values (2, now(), false, 'BMW', 'BMWModel2');

insert into manufacturer(id, date_created, deleted, name, model) values (3, now(), false, 'Audi', 'AudiModel1');

insert into manufacturer(id, date_created, deleted, name, model) values (4, now(), false, 'Audi', 'AudiModel2');


-- Create 2 OFFDUTY cars

insert into car (id, date_created, deleted, car_status, license_plate, seat_count, convertible, rating, engine_type, manufacturer_id) 
values (1, now(), false, 'OFFDUTY','MH11-1111', 2, false, 'TWO_STAR', 'GAS', 2);

insert into car (id, date_created, deleted, car_status, license_plate, seat_count, convertible, rating, engine_type, manufacturer_id) 
values (2, now(), false, 'OFFDUTY','MH11-2222', 4, false, 'FOUR_STAR', 'ELECTRIC', 4);

-- Create 1 FREE cars

insert into car (id, date_created, deleted, car_status, license_plate, seat_count, convertible, rating, engine_type, manufacturer_id) 
values (3, now(), false, 'FREE', 'MH22-1111', 2, false, 'ONE_STAR', 'GAS', 1);

insert into car (id, date_created, deleted, car_status, license_plate, seat_count, convertible, rating, engine_type, manufacturer_id) 
values (4, now(), false, 'FREE', 'MH22-2222', 4, false, 'THREE_STAR', 'ELECTRIC', 3);

-- Create 2 BUSY cars

insert into car (id, date_created, deleted, car_status, license_plate, seat_count, convertible, rating, engine_type, manufacturer_id) 
values (5, now(), false, 'BUSY', 'MH33-1111', 2, false, 'FIVE_STAR', 'GAS', 1);

insert into car (id, date_created, deleted, car_status, license_plate, seat_count, convertible, rating, engine_type, manufacturer_id) 
values (6, now(), false, 'BUSY', 'MH33-2222', 4, false, 'FIVE_STAR', 'ELECTRIC', 4);



-- Create 3 OFFLINE drivers

-- insert into driver (id, date_created, deleted, online_status, password, username) values (1, now(), false, 'OFFLINE',
-- 'ksqUMZHZzPumnxLmMhj/Xg==', 'driver01');

-- insert into driver (id, date_created, deleted, online_status, password, username) values (2, now(), false, 'OFFLINE',
-- 'CQXQXUOI7KGrNJP6p+qqRg==', 'driver02');


insert into driver (id, date_created, deleted, online_status, password, username) values (1, now(), false, 'OFFLINE',
 /*driver01pw*/'$2a$10$hojQiMeTUkcu9lLUTddlgez3o1qUiIOekCsuYgjYNhuJp1QhsD2yy', 'driver01');

insert into driver (id, date_created, deleted, online_status, password, username) values (2, now(), false, 'OFFLINE',
/*driver02pw*/'$2a$10$WawqBq2id9PSOgUWUGanVO6yTCwkWbc7r2WHwvv7HJb3KXe5HsyDC', 'driver02');

insert into driver (id, date_created, deleted, online_status, password, username) values (3, now(), false, 'OFFLINE',
/*driver03pw*/'$2a$10$B.VmubM1dimSP6QVVkli6e5Z8gb0XGvD4VoyVmhJqvh2nY0pWCeJ2', 'driver03');


-- Create 3 ONLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username, car_id) values (4, now(), false, 'ONLINE',
/*driver04pw*/'$2a$10$BjkMR8flZkjoUYNuIhR.HOy1PHxrrGwQXjJp17/cdHakmnuEUlau2','driver04',  4);

insert into driver (id, date_created, deleted, online_status, password, username, car_id) values (5, now(), false, 'ONLINE',
/*driver05pw*/'$2a$10$7AOm3BnYAP63lMA7ZL8HWOwBvgQnZkqYvSRENSaKU8XoE9z/P5RRC','driver05', 5);

insert into driver (id, date_created, deleted, online_status, password, username, car_id) values (6, now(), false, 'ONLINE',
/*driver06pw*/'$2a$10$55c8Lfw56siwE.akIHVntOYYkcwI.Di26Ws6jgnjrcFz/jI2GBgm2', 'driver06', 3);

-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (7,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'OFFLINE',
/*driver07pw*/'$2a$10$ieOWpjHafyyXwhvUv2xXEeXfYXwBctXH1qJQIZ5GMngyvuQQ/dgLS', 'driver07');

-- Create 1 ONLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username, car_id)
values
 (8,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'ONLINE',
/*driver08pw*/'$2a$10$xNkrdU3BZJwiOTuB0UBrBecysuGV3X5S1hhfL49FOWRlnR4Oy/UO6', 'driver08', 6);



