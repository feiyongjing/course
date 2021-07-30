create table "user"
(
    id         serial primary key,
    username       varchar(10) unique not null,
    encrypted_password        varchar(100) not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    status varchar(10) not null default 'ok'
);

-- status 表示数据的状态 ok 和 deleted

insert into "user"(id,username,encrypted_password) values
(1,'张三',''),
(2,'李四',''),
(3,'王五','');

alter sequence user_id_seq restart with 4;