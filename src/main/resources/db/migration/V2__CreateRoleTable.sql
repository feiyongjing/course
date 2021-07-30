create table role
(
    id         serial primary key,
    name       varchar(10) unique not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    status varchar(10) not null default 'ok'
);

insert into role(id,name) values
(1,'学生'),
(2,'老师'),
(3,'管理员');

alter sequence role_id_seq restart with 4;