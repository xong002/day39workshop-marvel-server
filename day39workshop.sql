create database marvel;
use marvel;

create table characters (
	id int not null,
    name varchar(255) not null,
    thumbnail varchar(255),
    constraint pk_id primary key(id)
);

create table comments (
	id int not null,
    comments mediumtext,
    char_id int not null,
    constraint pk_id primary key(id),
    constraint fk_charid foreign key(char_id) references characters(id)
);

select * from characters;
select * from comments;

alter table comments 
add timestamp timestamp default(current_timestamp()); 

alter table comments
modify column id int not null auto_increment;

insert into comments (comments, char_id) values ('text', 1009157);
delete from comments where id = 1;


