-- drop table bulletins;
-- drop table bulletin_replies;


create table bulletins (
    id varchar(20) not null,
    title varchar(100) not null,
    body varchar(256) not null,
    read_count int null,
    created_on  TIMESTAMP  not null,
    userid varchar(80) not null,
    password varchar(25)  not null,
    name varchar(25) not null,
    email varchar(80) not null,
    constraint pk_bulletins primary key (id)
);

create table bulletin_replies (
    id varchar(20) not null,
    name varchar(100) not null,
    body varchar(256) not null,
    created_on  TIMESTAMP  not null,
    bulletin_id int not null,
    constraint pk_bulletin_replies primary key (id),
    constraint fk_bulletin_replies_1 foreign key (bulletin_id)
        references bulletins (id)
);

CREATE TABLE sequence
(
    name               varchar(30)  not null,
    nextid             int          not null,
    constraint pk_sequence primary key (name)
);
