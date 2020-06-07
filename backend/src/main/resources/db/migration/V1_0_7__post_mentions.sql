create table fs_post_mention (
    created_at timestamp not null,
    updated_at timestamp not null,
    post_id bigint not null,
    user_id bigint not null,
    primary key (post_id, user_id)
);

alter table fs_post_mention add constraint fk_post_mention_post_id foreign key (post_id) references fs_post;
alter table fs_post_mention add constraint fk_post_mention_user_id foreign key (user_id) references fs_user;