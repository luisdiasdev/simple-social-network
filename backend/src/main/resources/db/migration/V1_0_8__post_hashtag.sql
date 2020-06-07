create sequence hashtag_seq start with 1 increment by 1;

create table fs_hashtag (
    id bigint not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    last_used timestamp not null,
    hashtag varchar(255) not null,
    primary key (id));

create table fs_post_hashtag (
    created_at timestamp not null,
    updated_at timestamp not null,
    post_id bigint not null,
    hashtag_id bigint not null,
    primary key (hashtag_id, post_id));

alter table fs_post_hashtag add constraint fk_post_hashtag_post_id foreign key (post_id) references fs_post;
alter table fs_post_hashtag add constraint fk_post_hashtag_hashtag_id foreign key (hashtag_id) references fs_hashtag;