package br.com.agateownz.foodsocial.modules.user.controller;

import br.com.agateownz.foodsocial.config.ApplicationProfiles;
import br.com.agateownz.foodsocial.modules.shared.annotations.MockMvcContextConfiguration;
import br.com.agateownz.foodsocial.modules.shared.controller.AbstractControllerTest;
import br.com.agateownz.foodsocial.modules.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static br.com.agateownz.foodsocial.modules.user.UserMockBuilders.UserCreateRequestMock;
import static org.hamcrest.Matchers.is;
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

    @DisplayName("POST /users - should save a user without needing authentication")
    @Test
    public void saveUserTestSuccess() throws Exception {
        mockMvc.perform(post(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(UserCreateRequestMock.valid())))
            .andExpect(status().isCreated());
    }

    @DisplayName("POST /users - should not save a user if username is not valid")
    @Test
    public void saveUserTestUsernameNotValid() throws Exception {
        mockMvc.perform(post(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(UserCreateRequestMock.invalidUsername())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0].message", is("username size must be between 4 and 30")));
    }

    @DisplayName("POST /users - should not save a user if username is null")
    @Test
    public void saveUserTestUsernameNull() throws Exception {
        mockMvc.perform(post(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(UserCreateRequestMock.nullUsername())))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0].message", is("username must not be null")));
    }

    @DisplayName("POST /users - should not save a user if email is not valid")
    @Test
    public void saveUserTestEmailNotValid() throws Exception {
        mockMvc.perform(post(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(UserCreateRequestMock.invalidEmail())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0].message", is("email must be a well-formed email address")));
    }

    @DisplayName("POST /users - should not save a user if email is null")
    @Test
    public void saveUserTestEmailNull() throws Exception {
        mockMvc.perform(post(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(UserCreateRequestMock.nullEmail())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0].message", is("email must not be null")));
    }

    @DisplayName("POST /users - should not save a user if password is null")
    @Test
    public void saveUserTestPasswordNull() throws Exception {
        mockMvc.perform(post(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(UserCreateRequestMock.nullPassword())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0].message", is("password must not be null")));
    }

}