create table circle_content (
    uuid varchar(255) not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    content_discriminator varchar(255) not null,
    content_type varchar(255) not null,
    path varchar(255) not null,
    user_id bigint not null,
    primary key (uuid));

alter table circle_content add constraint fk_user_content foreign key (user_id) references circle_user;