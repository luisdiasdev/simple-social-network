package br.com.agateownz.foodsocial.modules.user;

import br.com.agateownz.foodsocial.modules.user.dto.request.CreateUserRequest;

public class UserMockBuilders {

    public static final String VALID_USERNAME = "therock";
    public static final String VALID_EMAIL = "therockemail@provider.com";
    public static final String VALID_PASSWORD = "thesecurepasswordrocks";

    public static final String INVALID_USERNAME = "a";
    public static final String INVALID_EMAIL = "this-is-not-a-valid-email";

    public static class UserCreateRequestMock {

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

}
