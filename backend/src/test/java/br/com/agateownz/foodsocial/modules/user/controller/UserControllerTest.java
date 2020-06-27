package br.com.agateownz.foodsocial.modules.user.controller;

import br.com.agateownz.foodsocial.config.ApplicationProfiles;
import br.com.agateownz.foodsocial.modules.shared.annotations.MockMvcContextConfiguration;
import br.com.agateownz.foodsocial.modules.shared.controller.AbstractControllerTest;
import br.com.agateownz.foodsocial.modules.user.UserMockBuilders;
import br.com.agateownz.foodsocial.modules.user.UserMockBuilders.*;
import br.com.agateownz.foodsocial.modules.user.service.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import static br.com.agateownz.foodsocial.modules.user.UserMockBuilders.*;
import static br.com.agateownz.foodsocial.modules.user.exceptions.UserExceptions.USER_NOT_FOUND;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(ApplicationProfiles.TEST)
@WebMvcTest(UserController.class)
@MockMvcContextConfiguration
class UserControllerTest extends AbstractControllerTest {

    private static final String ENDPOINT = "/users";

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        when(userService.save(ArgumentMatchers.any()))
            .thenReturn(CreateUserResponseMock.valid());
        doThrow(USER_NOT_FOUND)
            .when(userService).findByUsername(INVALID_USERNAME);
        when(userService.findByUsername(VALID_USERNAME))
            .thenReturn(UserResponseMock.valid());
        when(userService.findUsersToMention(VALID_SEARCH))
            .thenReturn(MentionUserResponseMock.list());
        when(userService.findUsersToMention(INVALID_SEARCH))
            .thenReturn(List.of());
    }

    @DisplayName("POST /users")
    @Nested
    class SaveUserTest {

        @DisplayName("should save a user without needing authentication")
        @Test
        public void saveUserTestSuccess() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(CreateUserRequestMock.valid())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(UserMockBuilders.VALID_ID.intValue())))
                .andExpect(jsonPath("$.username", is(UserMockBuilders.VALID_USERNAME)))
                .andExpect(jsonPath("$.email", is(UserMockBuilders.VALID_EMAIL)));
        }

        @DisplayName("should not save a user if username is not valid")
        @Test
        public void saveUserTestUsernameNotValid() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(CreateUserRequestMock.invalidUsername())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("username size must be between 4 and 30")));
        }

        @DisplayName("should not save a user if username is null")
        @Test
        public void saveUserTestUsernameNull() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(CreateUserRequestMock.nullUsername())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("username must not be null")));
        }

        @DisplayName("should not save a user if email is not valid")
        @Test
        public void saveUserTestEmailNotValid() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(CreateUserRequestMock.invalidEmail())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("email must be a well-formed email address")));
        }

        @DisplayName("should not save a user if email is null")
        @Test
        public void saveUserTestEmailNull() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(CreateUserRequestMock.nullEmail())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("email must not be null")));
        }

        @DisplayName("should not save a user if password is null")
        @Test
        public void saveUserTestPasswordNull() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(CreateUserRequestMock.nullPassword())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("password must not be null")));
        }
    }

    @DisplayName("GET /users/{name}")
    @Nested
    @WithMockUser
    class FindUserByNameTest {

        @DisplayName("should return user information if it exists")
        @Test
        public void findUserByNameTestSuccess() throws Exception {
            mockMvc.perform(get(ENDPOINT + "/{username}", VALID_USERNAME))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(VALID_ID.intValue())))
                .andExpect(jsonPath("$.username", is(VALID_USERNAME)))
                .andExpect(jsonPath("$.createdAt", is(VALID_CREATED_AT_STRING)));
        }

        @DisplayName("should return 403 if not authenticated")
        @Test
        @WithAnonymousUser
        public void findUserByNameTestFailAuthentication() throws Exception{
            mockMvc.perform(get(ENDPOINT + "/{username}", VALID_USERNAME))
                .andExpect(status().isForbidden());
        }

        @DisplayName("should return 404 if user not found")
        @Test
        public void findUserByNameTestFailNotFound() throws Exception {
            mockMvc.perform(get(ENDPOINT + "/{username}", INVALID_USERNAME))
                .andExpect(status().isNotFound());
        }
    }

    @DisplayName("GET /users/name?search=")
    @Nested
    @WithMockUser
    class FindUsersToMentionTest {

        @DisplayName("should return list of users available to mention")
        @Test
        public void findUsersToMentionTestSuccess() throws Exception {
            mockMvc.perform(get(ENDPOINT + "/name")
                .param("search", VALID_SEARCH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
        }

        @DisplayName("should return 403 if not authenticated")
        @Test
        @WithAnonymousUser
        public void findUsersToMentionTestAuthentication() throws Exception {
            mockMvc.perform(get(ENDPOINT + "/name")
                .param("search", VALID_SEARCH))
                .andDo(print())
                .andExpect(status().isForbidden());
        }

        @DisplayName("should return empty list if no users available")
        @Test
        public void findUsersToMentionTestEmpty() throws Exception {
            mockMvc.perform(get(ENDPOINT + "/name")
                .param("search", INVALID_SEARCH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", empty()));
        }
    }
}