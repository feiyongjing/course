create table permission
(
    id         serial primary key,
    name       varchar(50) not null,
    role_id    integer not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    status varchar(10) not null default 'OK'
);

insert into permission(name, role_id) values
('登录用户',1),
('登录用户',2),
('登录用户',3),
('课程管理',2),
('课程管理',3),
('管理用户',3);
