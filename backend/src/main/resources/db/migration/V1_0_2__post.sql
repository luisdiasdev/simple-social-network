create sequence post_seq start with 1 increment by 1;

create table fs_post (
    id bigint not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    message varchar(1000),
    user_id bigint not null,
    primary key (id));

alter table fs_post add constraint fk_user_post foreign key (user_id) references fs_user;