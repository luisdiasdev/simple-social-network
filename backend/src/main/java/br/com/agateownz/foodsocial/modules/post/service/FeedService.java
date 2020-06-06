package br.com.agateownz.foodsocial.modules.post.service;

import br.com.agateownz.foodsocial.modules.post.dto.response.FeedResponse;
import br.com.agateownz.foodsocial.modules.post.mapper.FeedMapper;
import br.com.agateownz.foodsocial.modules.shared.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
public class FeedService {

    @Autowired
    private PostService postService;
    @Autowired
    private FeedMapper feedMapper;
    @Autowired
    private AuthenticationService authenticationService;

    @Transactional
    public Page<FeedResponse> getFeed(Pageable pageable) {
        var result = postService.getFeedForUser(authenticationService.getAuthenticatedUserId(), pageable);
        var resultMapped = result.stream()
                .map(p -> feedMapper.mapFrom(p, p.getUser().getUserProfile()))
                .collect(Collectors.toList());
        return new PageImpl<>(resultMapped, pageable, resultMapped.size());
    }
}
