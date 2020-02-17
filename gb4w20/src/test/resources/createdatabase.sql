-- Only use on your own machine
create database gb4w20;
use gb4w20;

create user gb4w20@'localhost' identified with mysql_native_password by 'pencil3tuna' require none;
create user gb4w20@'%' identified with mysql_native_password by 'pencil3tuna' require none;

grant all on gb4w20.* to gb4w20@'localhost';
grant all on gb4w20.* to gb4w20@'%';
