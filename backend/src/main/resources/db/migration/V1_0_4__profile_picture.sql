alter table circle_user_profile add column user_profile_image_id varchar(255);

alter table circle_user_profile add constraint fk_user_profile_image
foreign key (user_profile_image_id) references circle_content;