create sequence user_seq start with 1 increment by 1;

create table circle_user (
    id bigint not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    email varchar(255) not null,
    password varchar(255) not null,
    username varchar(30) not null,
    primary key (id));

alter table circle_user add constraint uk_username_email unique (username, email);
alter table circle_user add constraint uk_email unique (email);