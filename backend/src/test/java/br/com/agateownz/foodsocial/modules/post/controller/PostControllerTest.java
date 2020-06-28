package br.com.agateownz.foodsocial.modules.post.controller;

import br.com.agateownz.foodsocial.config.ApplicationProfiles;
import br.com.agateownz.foodsocial.modules.post.PostMockBuilders.*;
import br.com.agateownz.foodsocial.modules.post.service.PostService;
import br.com.agateownz.foodsocial.modules.shared.annotations.MockMvcContextConfiguration;
import br.com.agateownz.foodsocial.modules.shared.controller.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import static br.com.agateownz.foodsocial.modules.content.exceptions.ContentExceptions.CONTENT_NOT_FOUND;
import static br.com.agateownz.foodsocial.modules.post.PostMockBuilders.*;
import static br.com.agateownz.foodsocial.modules.post.exceptions.PostExceptions.POST_NOT_FOUND;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(ApplicationProfiles.TEST)
@WebMvcTest(PostController.class)
@MockMvcContextConfiguration
@WithMockUser
class PostControllerTest extends AbstractControllerTest {

    private static final String ENDPOINT = "/posts";

    @MockBean
    private PostService postService;

    @BeforeEach
    public void setup() {
        doReturn(PostResponseMock.create(VALID_POST_ID, VALID_POST_USER_ID))
            .when(postService).save(any());
        doReturn(PostResponseMock.create(VALID_POST_ID, VALID_POST_USER_ID))
            .when(postService).getById(VALID_POST_ID);

        doThrow(POST_NOT_FOUND)
            .when(postService).removePost(INVALID_POST_ID);
        doThrow(POST_NOT_FOUND)
            .when(postService).getById(INVALID_POST_ID);

        doThrow(CONTENT_NOT_FOUND)
            .when(postService).removePostPicture(INVALID_POST_PICTURE_UUID);

        doReturn(postPictureResponse())
            .when(postService).savePostPicture(any());
    }

    @DisplayName("GET /posts/{id}")
    @Nested
    @WithMockUser
    class GetPostByIdTest {

        @DisplayName("should return post data if it exists")
        @Test
        public void success() throws Exception {
            mockMvc.perform(get(ENDPOINT + "/{id}", VALID_POST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(VALID_POST_ID.intValue())))
                .andExpect(jsonPath("$.userId", is(VALID_POST_USER_ID.intValue())));
        }

        @DisplayName("should return 404 if post not found")
        @Test
        public void notFound() throws Exception {
            mockMvc.perform(get(ENDPOINT + "/{id}", INVALID_POST_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[0].message", is("The resource Post was not found.")));
        }

        @DisplayName("should return 403 if not authenticated")
        @Test
        @WithAnonymousUser
        public void fail() throws Exception {
            mockMvc.perform(get(ENDPOINT + "/{id}", VALID_POST_ID))
                .andExpect(status().isForbidden());
        }
    }

    @DisplayName("POST /posts")
    @Nested
    @WithMockUser
    class SavePostTest {

        @DisplayName("should return saved post details if successful")
        @Test
        public void success() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .content(toJsonString(CreatePostRequestMock.valid()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(VALID_POST_ID.intValue())))
                .andExpect(jsonPath("$.userId", is(VALID_POST_USER_ID.intValue())));
        }

        @DisplayName("should return 400 if message is blank (ex: empty json)")
        @Test
        public void blankMessage() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .content(toJsonString(CreatePostRequestMock.blankMessage()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("message size must be greater than 1")));
        }

        @DisplayName("should return 400 if message is null (ex: null or undefined in json)")
        @Test
        public void nullMessage() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .content(toJsonString(CreatePostRequestMock.nullMessage()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("message must not be null")));
        }

        @DisplayName("should return 400 if picture string is empty")
        @Test
        public void emptyPictureString() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .content(toJsonString(CreatePostRequestMock.emptyPictureString()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("pictures[0] must not be empty")));
        }

        @DisplayName("should return 403 if not authenticated")
        @Test
        @WithAnonymousUser
        public void fail() throws Exception {
            mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(CreatePostRequestMock.valid())))
                .andExpect(status().isForbidden());
        }
    }

    @DisplayName("DELETE /posts/{id}")
    @Nested
    @WithMockUser
    class DeletePostByIdTest {

        @DisplayName("should return 204 if post was deleted")
        @Test
        public void valid() throws Exception {
            mockMvc.perform(delete(ENDPOINT + "/{id}", VALID_POST_ID))
                .andExpect(status().isNoContent());
        }

        @DisplayName("should return 404 if post is not owned by user or not found")
        @Test
        public void notFound() throws Exception {
            mockMvc.perform(delete(ENDPOINT + "/{id}", INVALID_POST_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[0].message", is("The resource Post was not found.")));
        }

        @DisplayName("should return 403 if not authenticated")
        @Test
        @WithAnonymousUser
        public void fail() throws Exception {
            mockMvc.perform(delete(ENDPOINT + "/{id}", VALID_POST_ID))
                .andExpect(status().isForbidden());
        }
    }

    @DisplayName("POST /posts/picture")
    @Nested
    @WithMockUser
    class SavePostPictureTest {

        @DisplayName("should return content uri if authenticated")
        @Test
        public void success() throws Exception {
            mockMvc.perform(multipart(ENDPOINT + "/picture")
                .file(postPictureMultipartFile())
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", is(VALID_POST_PICTURE_UUID)))
                .andExpect(jsonPath("$.contentUri", is(VALID_POST_PICTURE_URI)));
        }

        @DisplayName("should return 403 if not authenticated")
        @Test
        @WithAnonymousUser
        public void fail() throws Exception {
            mockMvc.perform(multipart(ENDPOINT + "/picture")
                .file(postPictureMultipartFile()))
                .andExpect(status().isForbidden());
        }
    }

    @DisplayName("DELETE /posts/picture/{uuid}")
    @Nested
    @WithMockUser
    class DeletePostPictureTest {

        @DisplayName("should return 204 if post picture was deleted")
        @Test
        public void valid() throws Exception {
            mockMvc.perform(delete(ENDPOINT + "/picture/{uuid}", VALID_POST_PICTURE_UUID))
                .andExpect(status().isNoContent());
        }

        @DisplayName("should return 404 if post picture is not owned by user or not found")
        @Test
        public void notFound() throws Exception {
            mockMvc.perform(delete(ENDPOINT + "/picture/{uuid}", INVALID_POST_PICTURE_UUID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[0].message", is("The resource Content was not found.")));
        }

        @DisplayName("should return 403 if not authenticated")
        @Test
        @WithAnonymousUser
        public void fail() throws Exception {
            mockMvc.perform(delete(ENDPOINT + "/picture/{uuid}", VALID_POST_PICTURE_UUID))
                .andExpect(status().isForbidden());
        }
    }
}