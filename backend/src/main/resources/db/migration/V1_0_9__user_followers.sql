create table fs_user_follower (
    created_at timestamp not null,
    updated_at timestamp not null,
    user_id bigint not null,
    follower_id bigint not null,
    constraint valid_users check (user_id <> follower_id),
    primary key (follower_id, user_id)
);

create table fs_user_following (
    created_at timestamp not null,
    updated_at timestamp not null,
    user_id bigint not null,
    following_id bigint not null,
    constraint valid_users check (user_id <> following_id),
    primary key (following_id, user_id)
);

alter table fs_user_follower add constraint fk_user_follower_user_id foreign key (user_id) references fs_user;
alter table fs_user_follower add constraint fk_user_follower_follower_id foreign key (follower_id) references fs_user;
alter table fs_user_following add constraint fk_user_following_user_id foreign key (user_id) references fs_user;
alter table fs_user_following add constraint fk_user_following_following_id foreign key (following_id) references fs_user;