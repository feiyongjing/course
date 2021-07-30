create table "session"
(
    id         serial primary key,
    cookie     varchar(50) unique not null,
    user_id    int not null
);

insert into session(id,cookie,user_id) values
(1,'pupils',1),
(2,'teacher',2),
(3,'admin',3);

alter sequence session_id_seq restart with 4;