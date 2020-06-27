package br.com.agateownz.foodsocial.modules.post.controller;

import br.com.agateownz.foodsocial.config.ApplicationProfiles;
import br.com.agateownz.foodsocial.modules.post.FeedMockBuilders.FeedResponseMock;
import br.com.agateownz.foodsocial.modules.post.service.FeedService;
import br.com.agateownz.foodsocial.modules.shared.annotations.MockMvcContextConfiguration;
import br.com.agateownz.foodsocial.modules.shared.controller.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import static br.com.agateownz.foodsocial.modules.post.FeedMockBuilders.EMPTY_PAGE;
import static br.com.agateownz.foodsocial.modules.post.FeedMockBuilders.VALID_PAGE;
import static br.com.agateownz.foodsocial.modules.post.FeedMockBuilders.VALID_PAGE_TOTAL_ELEMENTS;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(ApplicationProfiles.TEST)
@WebMvcTest(FeedController.class)
@MockMvcContextConfiguration
@WithMockUser
class FeedControllerTest extends AbstractControllerTest {

    private static final String ENDPOINT = "/feed";

    @MockBean
    private FeedService feedService;

    @BeforeEach
    public void setup() {
        when(feedService.getFeed(any()))
            .then(invocationOnMock -> {
                var pageable = invocationOnMock.<Pageable>getArgument(0);
                if (pageable.getPageNumber() == VALID_PAGE) {
                    return FeedResponseMock.page(pageable);
                }
                return FeedResponseMock.empty(pageable);
            });
    }

    @DisplayName("GET /feed?page= should return feed content if authenticated")
    @Test
    public void getFeedSuccess() throws Exception {
        mockMvc.perform(get(ENDPOINT).param("page", VALID_PAGE.toString()))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.totalElements", is(VALID_PAGE_TOTAL_ELEMENTS)))
            .andExpect(jsonPath("$.number", is(VALID_PAGE)))
            .andExpect(jsonPath("$.content", hasSize(10)));
    }

    @DisplayName("GET /feed?page= should return empty feed if not more content available")
    @Test
    public void getFeedEmpty() throws Exception {
        mockMvc.perform(get(ENDPOINT).param("page", EMPTY_PAGE.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalElements", is(VALID_PAGE_TOTAL_ELEMENTS)))
            .andExpect(jsonPath("$.number", is(EMPTY_PAGE)))
            .andExpect(jsonPath("$.content", empty()));
    }

    @DisplayName("GET /feed?page= should return 403 if not authenticated")
    @Test
    @WithAnonymousUser
    public void getFeedAuthentication() throws Exception {
        mockMvc.perform(get(ENDPOINT).param("page", VALID_PAGE.toString()))
            .andExpect(status().isForbidden());
    }

}