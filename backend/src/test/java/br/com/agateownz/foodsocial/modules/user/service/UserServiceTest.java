package br.com.agateownz.foodsocial.modules.user.service;

import br.com.agateownz.foodsocial.config.ApplicationProfiles;
import br.com.agateownz.foodsocial.modules.shared.exception.EntityNotFoundException;
import br.com.agateownz.foodsocial.modules.shared.service.AuthenticationService;
import br.com.agateownz.foodsocial.modules.user.UserMockBuilders.*;
import br.com.agateownz.foodsocial.modules.user.dto.response.CreateUserResponse;
import br.com.agateownz.foodsocial.modules.user.dto.response.DefaultUserResponse;
import br.com.agateownz.foodsocial.modules.user.dto.response.MentionUserResponse;
import br.com.agateownz.foodsocial.modules.user.mapper.UserMapper;
import br.com.agateownz.foodsocial.modules.user.mapper.UserMapperImpl;
import br.com.agateownz.foodsocial.modules.user.model.User;
import br.com.agateownz.foodsocial.modules.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static br.com.agateownz.foodsocial.modules.user.UserMockBuilders.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

@ActiveProfiles(ApplicationProfiles.TEST)
@SpringBootTest(
    classes = {UserService.class, UserMapperImpl.class},
    webEnvironment = WebEnvironment.NONE
)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
        doAnswer(invocationOnMock -> {
            var request = invocationOnMock.<User>getArgument(0);
            request.setId(new Random().nextLong());
            return request;
        }).when(userRepository).save(any());

        doAnswer(invocationOnMock -> new Random().nextLong())
            .when(authenticationService).getAuthenticatedUserId();

        doReturn(Optional.of(UserMock.valid()))
            .when(userRepository).findByUsername(VALID_USERNAME);

        doReturn(Optional.of(UserMock.valid(VALID_ID)))
            .when(userRepository).findById(VALID_ID);

        doAnswer(invocationOnMock -> {
            var list = invocationOnMock.<List<Long>>getArgument(0);
            if (ObjectUtils.isEmpty(list) || list.size() == 1 && list.contains(INVALID_ID)) {
                return List.<User>of();
            }
            return list.stream().map(UserMock::valid).collect(Collectors.toList());
        }).when(userRepository).findByIdIn(anyList());

        doAnswer(invocationOnMock -> {
            var input = invocationOnMock.<String>getArgument(1);

            if (StringUtils.isBlank(input)
                || input.equals("%" + INVALID_SEARCH + "%")
                || input.equals("%%")) {
                return List.<MentionUserResponse>of();
            }

            return List.of(MentionUserResponseMock.single(VALID_ID));
        }).when(userRepository).findUsersToMention(anyLong(), anyString());
    }

    @DisplayName("save should return the newly created user")
    @Test
    public void saveTest() {
        var response = userService.save(CreateUserRequestMock.valid());

        assertThat(response)
            .extracting(CreateUserResponse::getId)
            .isNotNull();

        assertThat(response)
            .extracting(
                CreateUserResponse::getUsername,
                CreateUserResponse::getEmail)
            .containsExactly(VALID_USERNAME, VALID_EMAIL);
    }

    @DisplayName("findByUsername")
    @Nested
    class findByUserNameTest {

        @DisplayName("should return the user response if exists")
        @Test
        public void findByUserNameSuccess() {
            var response = userService.findByUsername(VALID_USERNAME);

            assertThat(response)
                .extracting(DefaultUserResponse::getId)
                .isNotNull();

            assertThat(response)
                .extracting(
                    DefaultUserResponse::getUsername,
                    DefaultUserResponse::getCreatedAt)
                .containsExactly(VALID_USERNAME, VALID_CREATED_AT);
        }

        @DisplayName("should throw an exception if not found")
        @Test
        public void findByUserNameFail() {
            assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> userService.findByUsername(INVALID_USERNAME))
                .withMessage("The resource User was not found.");
        }
    }

    @DisplayName("findById")
    @Nested
    class findByIdTest {

        @DisplayName("should return the user if exists")
        @Test
        public void findByIdSuccess() {
            var response = userService.findById(VALID_ID);

            assertThat(response)
                .extracting(User::getId, User::getUsername, User::getEmail)
                .containsExactly(VALID_ID, VALID_USERNAME, VALID_EMAIL);
        }

        @DisplayName("should throw an exception if not found")
        @Test
        public void findByIdFail() {
            assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> userService.findById(INVALID_ID))
                .withMessage("The resource User was not found.");
        }
    }

    @DisplayName("findByIds")
    @Nested
    class findByIdsTest {

        @DisplayName("should return list of users with the given ids")
        @Test
        public void findByIdsSuccess() {
            var response = userService.findByIds(List.of(VALID_ID));

            assertThat(response)
                .hasSize(1)
                .extracting(User::getId, User::getUsername, User::getEmail)
                .containsExactly(tuple(VALID_ID, VALID_USERNAME, VALID_EMAIL));
        }

        @DisplayName("should return empty list if no user with given ids found")
        @Test
        public void findByIdsFail() {
            var response = userService.findByIds(List.of(INVALID_ID));

            assertThat(response).isEmpty();
        }

        @DisplayName("should return empty list if provided an empty list")
        @Test
        public void findByIdsEmptyList() {
            var response = userService.findByIds(List.of());

            assertThat(response).isEmpty();
        }
    }

    @DisplayName("findUsersToMention")
    @Nested
    class findUsersToMentionTest {

        @DisplayName("should return list of users that can be mentioned")
        @Test
        public void findUsersToMentionSuccess() {
            var response = userService.findUsersToMention(VALID_SEARCH);

            assertThat(response)
                .extracting(MentionUserResponse::getId)
                .containsExactly(VALID_ID);
        }

        @DisplayName("should return empty list if not users can be mentioned")
        @Test
        public void findUsersToMentionFail() {
            var response = userService.findUsersToMention(INVALID_SEARCH);

            assertThat(response).isEmpty();
        }

        @DisplayName("should return empty list if provided an empty list")
        @Test
        public void findUsersToMentionEmptyList() {
            var response = userService.findUsersToMention("");

            assertThat(response).isEmpty();
        }
    }
}