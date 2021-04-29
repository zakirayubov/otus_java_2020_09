create table users
(
    id       bigserial not null primary key,
    name     varchar(255),
    login    varchar(255),
    password varchar(255)
);

insert into users VALUES (1, 'user1', 'login1', 'pass1');
insert into users VALUES (2, 'user2', 'login2', 'pass2');
insert into users VALUES (3, 'user3', 'login3', 'pass3');
insert into users VALUES (4, 'user4', 'login4', 'pass4');
insert into users VALUES (5, 'user5', 'login5', 'pass5');
