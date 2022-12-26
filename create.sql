create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);
create table account (id binary(255) not null, account_number varchar(255) not null, balance decimal(19,2) not null, customer_id binary(255) not null, primary key (id)) engine=InnoDB;
create table customer (id binary(255) not null, birth_date date not null, create_date date not null, first_name varchar(255) not null, gender integer not null, last_name varchar(255) not null, user_id binary(255), primary key (id)) engine=InnoDB;
create table transaction (id binary(255) not null, amount decimal(19,2) not null, create_date date not null, account_id binary(255) not null, transaction_type_id binary(255), primary key (id)) engine=InnoDB;
create table transaction_type (id binary(255) not null, type varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id binary(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table account add constraint FKnnwpo0lfq4xai1rs6887sx02k foreign key (customer_id) references customer (id);
alter table customer add constraint FKj8dlm21j202cadsbfkoem0s58 foreign key (user_id) references user (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FKnl0vpl01y6vu03hkpi4xupugo foreign key (transaction_type_id) references transaction_type (id);