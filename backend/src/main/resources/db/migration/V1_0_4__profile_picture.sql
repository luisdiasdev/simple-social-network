alter table fs_user_profile add column user_profile_image_id varchar(255);

alter table fs_user_profile add constraint fk_user_profile_image
foreign key (user_profile_image_id) references fs_content;