drop table if exists note;
create table note (
    id bigint auto_increment primary key,
    title varchar(255) not null,
    content varchar(255) not null,
    created_at timestamp default current_timestamp
);