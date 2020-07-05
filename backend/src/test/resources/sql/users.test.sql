INSERT INTO fs_user
(id, created_at, updated_at, email, password, username)
VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'johndoe@mailprovider.com', 'somepassword', 'johndoe');

INSERT INTO fs_user_profile
(user_id, created_at, updated_at, bio, display_name, website, user_profile_image_id, avatar_color)
VALUES(1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '', 'John Doe', '', null, '#000000');

INSERT INTO fs_user
(id, created_at, updated_at, email, password, username)
VALUES (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'robinsparkles@mailprovider.com', 'somepassword', 'robinsparkles');

INSERT INTO fs_user_profile
(user_id, created_at, updated_at, bio, display_name, website, user_profile_image_id, avatar_color)
VALUES(2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '', 'Robin Sparkles', '', null, '#000000');

INSERT INTO fs_user
(id, created_at, updated_at, email, password, username)
VALUES (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'tedmosby@mailprovider.com', 'somepassword', 'tedmosby');

INSERT INTO fs_user_profile
(user_id, created_at, updated_at, bio, display_name, website, user_profile_image_id, avatar_color)
VALUES(3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '', 'Ted Mosby', '', null, '#000000');

-- Following structure
-- User 1 follows user 2
-- User 2 follows user 1
INSERT INTO fs_user_follower
(created_at, updated_at, user_id, follower_id)
VALUES(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2);
INSERT INTO fs_user_following
(created_at, updated_at, user_id, following_id)
VALUES(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2);

INSERT INTO fs_user_follower
(created_at, updated_at, user_id, follower_id)
VALUES(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 1);
INSERT INTO fs_user_following
(created_at, updated_at, user_id, following_id)
VALUES(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 1);

-- User 2 follows user 3
INSERT INTO fs_user_follower
(created_at, updated_at, user_id, follower_id)
VALUES(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3);
INSERT INTO fs_user_following
(created_at, updated_at, user_id, following_id)
VALUES(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3);

-- user 3 follows user 2
INSERT INTO fs_user_follower
(created_at, updated_at, user_id, follower_id)
VALUES(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 2);
INSERT INTO fs_user_following
(created_at, updated_at, user_id, following_id)
VALUES(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 2);
