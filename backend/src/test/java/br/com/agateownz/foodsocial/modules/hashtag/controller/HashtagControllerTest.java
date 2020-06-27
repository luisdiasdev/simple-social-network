package br.com.agateownz.foodsocial.modules.hashtag.controller;

import br.com.agateownz.foodsocial.config.ApplicationProfiles;
import br.com.agateownz.foodsocial.modules.hashtag.HashtagMockBuilders;
import br.com.agateownz.foodsocial.modules.hashtag.HashtagMockBuilders.HashtagResponseMock;
import br.com.agateownz.foodsocial.modules.hashtag.service.HashtagService;
import br.com.agateownz.foodsocial.modules.shared.annotations.MockMvcContextConfiguration;
import br.com.agateownz.foodsocial.modules.shared.controller.AbstractControllerTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static br.com.agateownz.foodsocial.modules.hashtag.HashtagMockBuilders.INVALID_SEARCH;
import static br.com.agateownz.foodsocial.modules.hashtag.HashtagMockBuilders.VALID_ID;
import static br.com.agateownz.foodsocial.modules.hashtag.HashtagMockBuilders.VALID_SEARCH;
import static br.com.agateownz.foodsocial.modules.hashtag.HashtagMockBuilders.VALID_VALUE;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(ApplicationProfiles.TEST)
@WebMvcTest(HashtagController.class)
@MockMvcContextConfiguration
@WithMockUser
class HashtagControllerTest extends AbstractControllerTest {

    private static final String ENDPOINT = "/hashtags";

    @MockBean
    private HashtagService hashtagService;

    @BeforeEach
    public void setup() {
        when(hashtagService.searchHashtags(VALID_SEARCH))
            .thenReturn(HashtagResponseMock.list());
    }

    @DisplayName("GET /hashtags?search= should return 403 if not authenticated")
    @Test
    @WithAnonymousUser
    public void getHashtagsAuthentication() throws Exception {
        mockMvc.perform(get(ENDPOINT)
            .param("search", VALID_SEARCH))
            .andExpect(status().isForbidden());
    }

    @DisplayName("GET /hashtags?search= should return list of hashtags if search has results")
    @Test
    public void getHashtagsSuccess() throws Exception {
        mockMvc.perform(get(ENDPOINT)
            .param("search", VALID_SEARCH))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(VALID_ID.intValue())))
            .andExpect(jsonPath("$[0].value", is(VALID_VALUE)));
    }

    @DisplayName("GET /hashtags?search= should return empty list if search has no results")
    @Test
    public void getHashtagsEmpty() throws Exception {
        mockMvc.perform(get(ENDPOINT)
            .param("search", INVALID_SEARCH))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", empty()));
    }
}