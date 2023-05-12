truncate table "user" cascade;

insert into "user" (id, name, date_of_birth, password) values 
	(1, 'Джон', '1975-05-25', '$2a$04$1HL0E7bdDxKaVhKFJCzJ..8668dcTuFuQAujRHVPnav1TIJgGorqS'); -- john-pass
insert into account (id, user_id, deposit, balance) values
	(1, 1, 100, 100);
insert into email_data (user_id, email) values
	(1, 'john@gmail.com');
insert into phone_data (user_id, phone) values
	(1, '79995462200');

insert into "user" (id, name, date_of_birth, password) values 
	(2, 'Пол', '1977-09-14', '$2a$04$Y.4jUIXWDipUQJbqQar18.z2I0O6LPOBmMm0jJjg9W9puFg8Dbnga'); -- pol-pass
insert into account (id, user_id, deposit, balance) values
	(2, 2, 50, 50);
insert into email_data (user_id, email) values
	(2, 'pol@gmail.com');
insert into email_data (user_id, email) values
	(2, 'pol@mail.ru');
insert into phone_data (user_id, phone) values
	(2, '79995461100');

insert into "user" (id, name, date_of_birth, password) values 
	(3, 'Полина', '1978-04-20', '$2a$04$53616PvKk4JH5atLN864iuhpsGDWjywdby.wegTasQ4sZ1q9tEC2.'); -- polina-pass
insert into account (id, user_id, deposit, balance) values
	(3, 3, 150, 150);
insert into email_data (user_id, email) values
	(3, 'polina@gmail.com');
insert into phone_data (user_id, phone) values
	(3, '79995461101');
insert into phone_data (user_id, phone) values
	(3, '79995461102');

insert into "user" (id, name, date_of_birth, password) values 
	(4, 'Ирина', '1984-07-11', '$2a$04$4iTlDz6VWaAs/FSQQM.zE.OCl3j8YOlVs7K/uWgcWReBbjMXu6bkq'); -- irina-pass
insert into account (id, user_id, deposit, balance) values
	(4, 4, 1000, 1000);
insert into email_data (user_id, email) values
	(4, 'irina@gmail.com');
insert into email_data (user_id, email) values
	(4, 'irina@mail.ru');
insert into email_data (user_id, email) values
	(4, 'irina@yandex.ru');
insert into phone_data (user_id, phone) values
	(4, '79995463301');
insert into phone_data (user_id, phone) values
	(4, '79995463302');

