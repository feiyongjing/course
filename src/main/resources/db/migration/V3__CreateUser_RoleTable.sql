create table user_role
(
    id         serial primary key,
    user_id    integer not null,
    role_id    integer not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    status varchar(10) not null default 'ok'
);

insert into user_role(id, user_id, role_id) values
(1,1,1),
(2,2,2),
(3,3,3);
