package br.com.agateownz.foodsocial.modules.post;

import br.com.agateownz.foodsocial.modules.post.PostMockBuilders.PostResponseMock;
import br.com.agateownz.foodsocial.modules.post.dto.response.FeedResponse;
import br.com.agateownz.foodsocial.modules.user.dto.response.UserProfileWithPictureResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class FeedMockBuilders {

    public static final Integer VALID_PAGE = 1;
    public static final Integer VALID_PAGE_TOTAL_ELEMENTS = 1500;
    public static final Integer EMPTY_PAGE = 15;


    private static final Long INITIAL_POST_ID = 150L;
    private static final Long INITIAL_USER_ID = 1000L;

    public static class FeedResponseMock {

        public static Page<FeedResponse> page(Pageable pageable) {
            var content = LongStream.range(INITIAL_POST_ID, INITIAL_POST_ID + pageable.getPageSize())
                .boxed()
                .map(i -> valid(i, INITIAL_USER_ID + i))
                .collect(Collectors.toList());
            return new PageImpl<>(content, pageable, VALID_PAGE_TOTAL_ELEMENTS);
        }

        public static Page<FeedResponse> empty(Pageable pageable) {
            return new PageImpl<>(List.of(), pageable, VALID_PAGE_TOTAL_ELEMENTS);
        }

        private static FeedResponse valid(Long id, Long userId) {
            return new FeedResponse(
                PostResponseMock.create(id, userId),
                UserProfileWithPictureResponse.empty()
            );
        }
    }
}
