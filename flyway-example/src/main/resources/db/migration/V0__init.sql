drop table if exists member;

create table member (
    id          bigint      not null auto_increment primary key,
    name        varchar(50) not null
);

insert into member (id, name)
values (1, 'jae');