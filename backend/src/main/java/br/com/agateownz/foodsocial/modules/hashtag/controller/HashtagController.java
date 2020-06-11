package br.com.agateownz.foodsocial.modules.hashtag.controller;

import br.com.agateownz.foodsocial.modules.hashtag.dto.response.HashtagResponse;
import br.com.agateownz.foodsocial.modules.hashtag.service.HashtagService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.agateownz.foodsocial.config.swagger.SwaggerConstants.COOKIE_AUTH;

@Validated
@RestController
@RequestMapping("/hashtags")
@Tag(name = "hashtags")
@SecurityRequirement(name = COOKIE_AUTH)
public class HashtagController {

    @Autowired
    private HashtagService hashtagService;

    /**
     * Searches for hashtags
     *
     * @param search The hashtag value to search
     * @return A list of hashtags
     */
    @GetMapping
    public Set<HashtagResponse> search(@NotBlank String search) {
        return hashtagService.searchHashtags(search);
    }
}
