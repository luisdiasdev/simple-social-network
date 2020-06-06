create table circle_user_profile (
    user_id bigint not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    bio varchar(200),
    display_name varchar(60),
    website varchar(100),
    primary key (user_id));

alter table circle_user_profile add constraint fk_user_profile foreign key (user_id) references circle_user;