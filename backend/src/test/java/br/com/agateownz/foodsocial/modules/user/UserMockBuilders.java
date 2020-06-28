package br.com.agateownz.foodsocial.modules.user;

import br.com.agateownz.foodsocial.modules.user.dto.request.CreateUserRequest;
import br.com.agateownz.foodsocial.modules.user.dto.response.CreateUserResponse;
import br.com.agateownz.foodsocial.modules.user.dto.response.DefaultUserResponse;
import br.com.agateownz.foodsocial.modules.user.dto.response.MentionUserResponse;
import br.com.agateownz.foodsocial.modules.user.model.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


public class UserMockBuilders {

    public static final Long VALID_ID = 100L;
    public static final String VALID_USERNAME = "therock";
    public static final String VALID_EMAIL = "therockemail@provider.com";
    public static final String VALID_PASSWORD = "thesecurepasswordrocks";
    public static final LocalDateTime VALID_CREATED_AT = LocalDateTime.of(2020, 6, 25, 15, 30);
    public static final String VALID_CREATED_AT_STRING = "25-06-2020 15:30:00";
    public static final String VALID_SEARCH = "existinguser";

    public static final Long INVALID_ID = 101L;
    public static final String INVALID_USERNAME = "a";
    public static final String INVALID_EMAIL = "this-is-not-a-valid-email";
    public static final String INVALID_SEARCH = "nonexistinguser";

    public static class UserMock {

        public static User valid() {
            return valid(new Random().nextLong());
        }

        public static User valid(Long id) {
            var user = User.builder()
                .id(id)
                .email(VALID_EMAIL)
                .username(VALID_USERNAME)
                .build();
            user.setCreatedAt(VALID_CREATED_AT);
            return user;
        }
    }

    public static class CreateUserRequestMock {

        public static CreateUserRequest valid() {
            return new CreateUserRequest(VALID_USERNAME, VALID_EMAIL, VALID_PASSWORD);
        }

        public static CreateUserRequest invalidUsername() {
            return new CreateUserRequest(INVALID_USERNAME, VALID_EMAIL, VALID_PASSWORD);
        }

        public static CreateUserRequest nullUsername() {
            return new CreateUserRequest(null, VALID_EMAIL, VALID_PASSWORD);
        }

        public static CreateUserRequest invalidEmail() {
            return new CreateUserRequest(VALID_USERNAME, INVALID_EMAIL, VALID_PASSWORD);
        }

        public static CreateUserRequest nullEmail() {
            return new CreateUserRequest(VALID_USERNAME, null, VALID_PASSWORD);
        }

        public static CreateUserRequest nullPassword() {
            return new CreateUserRequest(VALID_USERNAME, VALID_EMAIL, null);
        }
    }

    public static class CreateUserResponseMock {

        public static CreateUserResponse valid() {
            return new CreateUserResponse(VALID_ID, VALID_EMAIL, VALID_USERNAME);
        }
    }

    public static class UserResponseMock {

        public static DefaultUserResponse valid() {
            return new DefaultUserResponse(VALID_ID, VALID_USERNAME, VALID_CREATED_AT);
        }
    }

    public static class MentionUserResponseMock {

        public static List<MentionUserResponse> list() {
            return LongStream.range(0, 5)
                .boxed()
                .map(MentionUserResponseMock::single)
                .collect(Collectors.toList());
        }

        public static MentionUserResponse single(Long id) {
            return new MentionUserResponse(
                id,
                "username-" + id,
                "displayName-" + id,
                "#FFFFFF",
                null
            );
        }
    }
}
