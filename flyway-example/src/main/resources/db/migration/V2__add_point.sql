drop table if exists point;

create table point (
    id          bigint      not null auto_increment primary key,
    dirX        int not null,
    dirY        int not null
);

insert into point (id, dirX, dirY)
values (1, 10, 20);