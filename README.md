# P0BankAPI
table
create table client (client_id int primary key generated always as identity,
	first_name varchar(50), last_name varchar(50), created_date bigint);
create table account (account_id int primary key generated always as identity, client_id int, balance numeric, created_date bigint, is_checking boolean,
	constraint fk_account_client foreign key(client_id) references client(client_id) ON DELETE cascade );
