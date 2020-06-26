package br.com.agateownz.foodsocial.modules.user;

import br.com.agateownz.foodsocial.modules.user.dto.request.UserCreateRequest;

public class UserMockBuilders {

    public static final String VALID_USERNAME = "therock";
    public static final String VALID_EMAIL = "therockemail@provider.com";
    public static final String VALID_PASSWORD = "thesecurepasswordrocks";

    public static final String INVALID_USERNAME = "a";
    public static final String INVALID_EMAIL = "this-is-not-a-valid-email";

    public static class UserCreateRequestMock {

        public static UserCreateRequest valid() {
            return new UserCreateRequest(VALID_USERNAME, VALID_EMAIL, VALID_PASSWORD);
        }

        public static UserCreateRequest invalidUsername() {
            return new UserCreateRequest(INVALID_USERNAME, VALID_EMAIL, VALID_PASSWORD);
        }

        public static UserCreateRequest nullUsername() {
            return new UserCreateRequest(null, VALID_EMAIL, VALID_PASSWORD);
        }

        public static UserCreateRequest invalidEmail() {
            return new UserCreateRequest(VALID_USERNAME, INVALID_EMAIL, VALID_PASSWORD);
        }

        public static UserCreateRequest nullEmail() {
            return new UserCreateRequest(VALID_USERNAME, null, VALID_PASSWORD);
        }

        public static UserCreateRequest nullPassword() {
            return new UserCreateRequest(VALID_USERNAME, VALID_EMAIL, null);
        }
    }

}
