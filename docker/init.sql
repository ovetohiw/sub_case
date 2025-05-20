create table sys_user
(
    id    serial primary key,
    name  varchar,
    email varchar
);

create table sys_subscribe
(
    id   int primary key,
    name varchar
);

create table sys_subscribers
(
    user_id      int not null,
    subscribe_id int not null
);

insert into sys_subscribe(id, name)
values (1, 'Яндекс.Плюс'),
       (2, 'VK Музыка'),
       (3, 'Netflix'),
       (4, 'YouTube Premium'),
       (5, 'Twitch');

insert into sys_user (name, email)
values ('Ivan', 'ivan.id@yandex.ru');

insert into sys_subscribers (user_id, subscribe_id)
values (1, 1),
       (1, 2),
       (1, 5);