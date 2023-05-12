drop table if exists phone_data;
drop table if exists email_data;
drop table if exists account;
drop table if exists "user";

create table "user" (
	id bigserial,
	name varchar(500) not null,
	date_of_birth date not null,
	password varchar(500) not null,
	constraint user_pk primary key (id),
	constraint user_password_check check (length(password) >= 8)
);

create table account (
	id bigserial,
	user_id bigint not null,
	deposit decimal(10, 2) not null,
	balance decimal(10, 2) not null,
	constraint account_pk primary key (id),
	constraint account_user_fk foreign key (user_id) references "user" (id) on delete cascade,
	constraint account_user_uniq unique (user_id),
	constraint account_balance_check check (balance >= 0 and deposit >= 0)
);

create table email_data (
	id bigserial,
	user_id bigint not null,
	email varchar(200) not null,
	constraint email_data_pk primary key (id),
	constraint email_data_user_fk foreign key (user_id) references "user" (id) on delete cascade,
	constraint email_uniq unique (email)
);

create table phone_data (
	id bigserial,
	user_id bigint not null,
	phone varchar(13) not null,
	constraint phone_data_pk primary key (id),
	constraint phone_data_user_fk foreign key (user_id) references "user" (id) on delete cascade,
	constraint phone_uniq unique (phone)
);

create or replace procedure increment_balance(inc integer, maxInc integer) as '
	update account set balance = balance + balance * inc / 100 where balance + balance * inc / 100 < deposit * maxInc / 100
' language sql;
