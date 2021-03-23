drop database if exists MyBlog;
create database MyBlog character set UTF8mb4;
use MyBlog;

create table user(
    id int primary key auto_increment,
    username varchar(20) not null unique ,
    password varchar(50) not null,
    nickname varchar(20),
    sex bit,
    birthday date,
    head varchar(50)
);



create table article(
    id int primary key auto_increment,
    title varchar(20) not null,
    content mediumtext not null,
    create_time timestamp default now(),
    view_count int default 0,
    user_id int,
    foreign key(user_id) references user(id)
);

insert into user(username,password) value ('唐三藏','1');
insert into user(username,password) value ('孙悟空','2');
insert into user(username,password) value ('猪悟能','3');
insert into user(username,password) value ('沙悟净','4');

insert into article(title, content,user_id) value ('图解HTTP','public....',1);
insert into article(title, content,user_id) value ('深入理解JVM','public....',2);
insert into article(title, content,user_id) value ('MYSQL技术内幕','public....',3);
insert into article(title, content,user_id) value ('图解TCP/IP','public....',2);
insert into article(title, content,user_id) value ('现代操作系统','public....',4);
insert into article(title, content,user_id) value ('JAVA面试宝典','public....',3);

-- 主外键关联的表，默认创建的主外键约束是restrict严格模式，
-- 比如从表中

-- # select id, username, password, nickname, sex, birthday, head from user where username='a';
--
-- select id,title from article where user_id=1;
-- select id from user where id=1;