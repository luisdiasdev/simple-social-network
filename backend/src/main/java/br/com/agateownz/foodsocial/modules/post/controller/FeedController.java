package br.com.agateownz.foodsocial.modules.post.controller;

import br.com.agateownz.foodsocial.modules.post.dto.response.FeedResponse;
import br.com.agateownz.foodsocial.modules.post.service.FeedService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.agateownz.foodsocial.config.swagger.SwaggerConstants.COOKIE_AUTH;

@Validated
@RequestMapping("/feed")
@RestController
@Tag(name = "feed")
@SecurityRequirement(name = COOKIE_AUTH)
public class FeedController {

    @Autowired
    private FeedService feedService;

    /**
     * Returns the feed for the current logged user
     *
     * @param pageable The pagination offset
     * @return A page with a list of posts to display
     */
    @GetMapping
    public Page<FeedResponse> get(Pageable pageable) {
        return feedService.getFeed(pageable);
    }
}
