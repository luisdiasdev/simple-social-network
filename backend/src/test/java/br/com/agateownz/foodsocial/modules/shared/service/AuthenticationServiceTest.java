package br.com.agateownz.foodsocial.modules.shared.service;

import br.com.agateownz.foodsocial.config.ApplicationProfiles;
import br.com.agateownz.foodsocial.config.security.JwtUserToken;
import br.com.agateownz.foodsocial.modules.shared.dto.AuthenticatedUser;
import br.com.agateownz.foodsocial.modules.shared.exception.UnauthorizedException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ActiveProfiles(ApplicationProfiles.TEST)
@SpringBootTest
class AuthenticationServiceTest {

    private static final Long AUTHENTICATION_USER_ID = 100L;
    private static final String AUTHENTICATION_USER_NAME = "auth.user";

    @Autowired
    private AuthenticationService authenticationService;

    @BeforeEach
    public void beforeEach() {
        TestSecurityContextHolder.clearContext();
    }

    @DisplayName("getAuthenticatedUser should throw exception if not authenticated")
    @Test
    public void getAuthenticatedUserTestException() {
        assertThat(authenticationService.getAuthenticatedUser()).isEmpty();
    }

    @DisplayName("getAuthenticatedUser should return authenticated user")
    @Test
    public void getAuthenticatedUserTestOk() {
        mockAuthentication(mockJwtAuthentication());

        assertThat(authenticationService.getAuthenticatedUser())
            .isNotEmpty()
            .containsInstanceOf(AuthenticatedUser.class);
    }

    @DisplayName("getAuthenticatedUserId should throw exception if not authenticated")
    @Test
    public void getAuthenticatedUserIdTestException() {
        assertThatExceptionOfType(UnauthorizedException.class)
            .isThrownBy(() -> authenticationService.getAuthenticatedUserId());
    }

    @DisplayName("getAuthenticatedUserId should return user id if authenticated")
    @Test
    public void getAuthenticatedUserIdTestOk() {
        mockAuthentication(mockJwtAuthentication());

        var userId = authenticationService.getAuthenticatedUserId();

        assertThat(userId).isEqualTo(AUTHENTICATION_USER_ID);
    }

    private JwtUserToken mockJwtAuthentication() {
        return new JwtUserToken(AUTHENTICATION_USER_NAME, List.of(), AUTHENTICATION_USER_ID);
    }

    private void mockAuthentication(Authentication authentication) {
        TestSecurityContextHolder.setAuthentication(authentication);
    }
}