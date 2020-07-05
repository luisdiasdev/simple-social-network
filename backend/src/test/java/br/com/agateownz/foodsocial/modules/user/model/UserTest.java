package br.com.agateownz.foodsocial.modules.user.model;

import br.com.agateownz.foodsocial.modules.user.UserMockBuilders.CreateUserRequestMock;
import br.com.agateownz.foodsocial.modules.user.UserMockBuilders.UserMock;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static br.com.agateownz.foodsocial.modules.user.UserMockBuilders.VALID_EMAIL;
import static br.com.agateownz.foodsocial.modules.user.UserMockBuilders.VALID_PASSWORD;
import static br.com.agateownz.foodsocial.modules.user.UserMockBuilders.VALID_USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserTest {

    @DisplayName("should encode user password")
    @Test
    public void encodePassword() {
        var passwordEncoder = mock(PasswordEncoder.class);
        when(passwordEncoder.encode(any()))
            .then(invocationOnMock -> {
                var password = invocationOnMock.<String>getArgument(0);
                var bytes = new byte[32];
                new SecureRandom(password.getBytes()).nextBytes(bytes);
                return new String(bytes, StandardCharsets.UTF_8);
            });
        var user = UserMock.valid();
        user.setPassword(VALID_PASSWORD);
        user.encodePassword(passwordEncoder);

        assertThat(user)
            .extracting(User::getPassword)
            .isNotEqualTo(VALID_PASSWORD);
    }

    @DisplayName("should create user from request")
    @Test
    public void createUserRequest() {
        var request = CreateUserRequestMock.valid();
        var user = User.of(request);

        assertThat(user)
            .extracting(User::getUsername, User::getEmail, User::getPassword)
            .containsExactly(VALID_USERNAME, VALID_EMAIL, VALID_PASSWORD);
    }
}