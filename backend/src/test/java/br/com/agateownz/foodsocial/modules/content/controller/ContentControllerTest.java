package br.com.agateownz.foodsocial.modules.content.controller;

import br.com.agateownz.foodsocial.config.ApplicationProfiles;
import br.com.agateownz.foodsocial.modules.content.ContentMockBuilders.InternalContentResponseMock;
import br.com.agateownz.foodsocial.modules.content.service.ContentService;
import br.com.agateownz.foodsocial.modules.shared.annotations.MockMvcContextConfiguration;
import br.com.agateownz.foodsocial.modules.shared.controller.AbstractControllerTest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import static br.com.agateownz.foodsocial.modules.content.ContentMockBuilders.INVALID_UUID;
import static br.com.agateownz.foodsocial.modules.content.ContentMockBuilders.VALID_CONTENT_LENGTH;
import static br.com.agateownz.foodsocial.modules.content.ContentMockBuilders.VALID_CONTENT_TYPE;
import static br.com.agateownz.foodsocial.modules.content.ContentMockBuilders.VALID_UUID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(ApplicationProfiles.TEST)
@WebMvcTest(ContentController.class)
@MockMvcContextConfiguration
@WithMockUser
class ContentControllerTest extends AbstractControllerTest {

    private static final String ENDPOINT = "/contents";

    @MockBean
    private ContentService contentService;

    @BeforeEach
    public void setup() {
        when(contentService.getInternalContentByUuid(VALID_UUID))
            .thenReturn(Optional.of(InternalContentResponseMock.valid()));
    }

    @DisplayName("GET /contents/{uuid} should return content if authenticated")
    @Test
    public void getContentSuccess() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/{uuid}", VALID_UUID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(VALID_CONTENT_TYPE))
            .andExpect(header().longValue(HttpHeaders.CONTENT_LENGTH, VALID_CONTENT_LENGTH));
    }

    @DisplayName("GET /contents/{uuid} should return 404 if content does not exist")
    @Test
    public void getContentNotFound() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/{uuid}", INVALID_UUID))
            .andExpect(status().isNotFound());
    }

    @DisplayName("GET /contents/{uuid} should return 403 if not authenticated")
    @Test
    @WithAnonymousUser
    public void getContentAuthentication() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/{uuid}", VALID_UUID))
            .andExpect(status().isForbidden());
    }
}