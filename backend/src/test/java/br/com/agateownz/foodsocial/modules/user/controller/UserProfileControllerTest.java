package br.com.agateownz.foodsocial.modules.user.controller;

import br.com.agateownz.foodsocial.config.ApplicationProfiles;
import br.com.agateownz.foodsocial.modules.shared.annotations.MockMvcContextConfiguration;
import br.com.agateownz.foodsocial.modules.shared.controller.AbstractControllerTest;
import br.com.agateownz.foodsocial.modules.user.UserProfileMockBuilders.ModifyUserProfileRequestMock;
import br.com.agateownz.foodsocial.modules.user.UserProfileMockBuilders.UserProfileWithPictureResponseMock;
import br.com.agateownz.foodsocial.modules.user.dto.request.ModifyUserProfileRequest;
import br.com.agateownz.foodsocial.modules.user.dto.response.UserProfileResponse;
import br.com.agateownz.foodsocial.modules.user.service.UserProfileService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import static br.com.agateownz.foodsocial.modules.user.UserProfileMockBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(ApplicationProfiles.TEST)
@WebMvcTest(UserProfileController.class)
@MockMvcContextConfiguration
class UserProfileControllerTest extends AbstractControllerTest {

    private static final String ENDPOINT = "/users/profile";

    @MockBean
    private UserProfileService userProfileService;

    @BeforeEach
    public void setup() {
        when(userProfileService.getFromAuthenticatedUser())
            .thenReturn(UserProfileWithPictureResponseMock.valid());

        doAnswer(invocationOnMock -> {
            var request = invocationOnMock.<ModifyUserProfileRequest>getArgument(0);

            return UserProfileResponse.builder()
                .displayName(request.getDisplayName().orElseThrow())
                .bio(request.getBio().orElseThrow())
                .website(request.getWebsite().orElseThrow())
                .build();
        }).when(userProfileService).save(any());

        doReturn(profilePictureResponse())
            .when(userProfileService).saveProfilePicture(any());
    }

    @DisplayName("GET /users/profile")
    @Nested
    @WithMockUser
    class GetUserProfileTest {

        @DisplayName("should return user profile if authenticated")
        @Test
        public void success() throws Exception {
            mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk());
        }

        @DisplayName("should return 403 if not authenticated")
        @Test
        @WithAnonymousUser
        public void fail() throws Exception {
            mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isForbidden());
        }
    }

    @DisplayName("POST /users/profile")
    @Nested
    @WithMockUser
    class PostUserProfileTest {

        @DisplayName("should return the new user profile if authenticated")
        @Test
        public void successPost() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(ModifyUserProfileRequestMock.valid())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName", is(VALID_CHANGED_DISPLAY_NAME)))
                .andExpect(jsonPath("$.website", is(VALID_CHANGED_WEBSITE)))
                .andExpect(jsonPath("$.bio", is(VALID_CHANGED_BIO)));
        }

        @DisplayName("should return 400 if displayName size is not valid")
        @Test
        public void displayNameFail() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(ModifyUserProfileRequestMock.invalidDisplayName())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("displayName size must be between 0 and 60")));
        }

        @DisplayName("should return 400 if website size is not valid")
        @Test
        public void websiteFail() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(ModifyUserProfileRequestMock.invalidWebsite())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("website size must be between 0 and 100")));
        }

        @DisplayName("should return 400 if bio size is not valid")
        @Test
        public void bioFail() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(ModifyUserProfileRequestMock.invalidBio())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("bio size must be between 0 and 200")));
        }

        @DisplayName("should return 403 if not authenticated")
        @Test
        @WithAnonymousUser
        public void fail() throws Exception {
            mockMvc.perform(post(ENDPOINT))
                .andExpect(status().isForbidden());
        }
    }

    @DisplayName("PUT /users/profile")
    @Nested
    @WithMockUser
    class PutUserProfileTest {

        @DisplayName("should return the updated user profile if authenticated")
        @Test
        public void successPut() throws Exception {
            mockMvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(ModifyUserProfileRequestMock.valid())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName", is(VALID_CHANGED_DISPLAY_NAME)))
                .andExpect(jsonPath("$.website", is(VALID_CHANGED_WEBSITE)))
                .andExpect(jsonPath("$.bio", is(VALID_CHANGED_BIO)));
        }

        @DisplayName("should return 400 if displayName size is not valid")
        @Test
        public void displayNameFail() throws Exception {
            mockMvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(ModifyUserProfileRequestMock.invalidDisplayName())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("displayName size must be between 0 and 60")));
        }

        @DisplayName("should return 400 if website size is not valid")
        @Test
        public void websiteFail() throws Exception {
            mockMvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(ModifyUserProfileRequestMock.invalidWebsite())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("website size must be between 0 and 100")));
        }

        @DisplayName("should return 400 if bio size is not valid")
        @Test
        public void bioFail() throws Exception {
            mockMvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(ModifyUserProfileRequestMock.invalidBio())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("bio size must be between 0 and 200")));
        }

        @DisplayName("should return 403 if not authenticated")
        @Test
        @WithAnonymousUser
        public void fail() throws Exception {
            mockMvc.perform(put(ENDPOINT))
                .andExpect(status().isForbidden());
        }
    }

    @DisplayName("POST /users/profile/picture")
    @Nested
    @WithMockUser
    class PostUserProfilePictureTest {

        @DisplayName("should return content uri if authenticated")
        @Test
        public void success() throws Exception {
            mockMvc.perform(multipart(ENDPOINT + "/picture")
                .file(profilePictureMultipartFile())
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", is(VALID_PROFILE_PICTURE_UUID)))
                .andExpect(jsonPath("$.contentUri", is(VALID_PROFILE_PICTURE_URI)));
        }

        @DisplayName("should return 403 if not authenticated")
        @Test
        @WithAnonymousUser
        public void fail() throws Exception {
            mockMvc.perform(multipart(ENDPOINT + "/picture")
                .file(profilePictureMultipartFile())
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isForbidden());
        }
    }

    @DisplayName("DELETE /users/profile/picture")
    @Nested
    @WithMockUser
    class DeleteUserProfilePictureTest {

        @DisplayName("should return 204 if image deleted")
        @Test
        public void success() throws Exception {
            mockMvc.perform(delete(ENDPOINT + "/picture"))
                .andExpect(status().isNoContent());
        }

        @DisplayName("should return 403 if not authenticated")
        @Test
        @WithAnonymousUser
        public void fail() throws Exception {
            mockMvc.perform(post(ENDPOINT + "/picture"))
                .andExpect(status().isForbidden());
        }
    }

}