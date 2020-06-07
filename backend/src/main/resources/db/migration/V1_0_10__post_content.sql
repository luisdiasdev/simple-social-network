create table fs_post_content (
    created_at timestamp not null,
    updated_at timestamp not null,
    content_id varchar(255) not null,
    post_id bigint not null,
    primary key (content_id, post_id));

alter table fs_post_content add constraint fk_post_content_content_id foreign key (content_id) references fs_content;
alter table fs_post_content add constraint fk_post_content_post_id foreign key (post_id) references fs_post;