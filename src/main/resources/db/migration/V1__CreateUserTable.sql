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

insert into "user"(username,encrypted_password) values
('张三',''),
('李四',''),
('王五','');

-- alter sequence id_seq restrict with 4;