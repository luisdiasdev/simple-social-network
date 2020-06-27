package br.com.agateownz.foodsocial.modules.post;

import br.com.agateownz.foodsocial.modules.post.dto.response.PostResponse;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public class PostMockBuilders {

    public static class PostResponseMock {

        public static PostResponse create(Long id, Long userId) {
            return new PostResponse(id, userId, Map.of(), Set.of(), LocalDateTime.now());
        }
    }
}
