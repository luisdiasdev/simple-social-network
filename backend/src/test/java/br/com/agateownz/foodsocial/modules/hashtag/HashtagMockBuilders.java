package br.com.agateownz.foodsocial.modules.hashtag;

import br.com.agateownz.foodsocial.modules.hashtag.dto.response.HashtagResponse;
import java.util.Set;

public class HashtagMockBuilders {

    public static final String VALID_SEARCH = "stayhome";
    public static final Long VALID_ID = 15L;
    public static final String VALID_VALUE = "stayhome2020";

    public static final String INVALID_SEARCH = "invalid";

    public static class HashtagResponseMock {

        public static Set<HashtagResponse> list() {
            return Set.of(new HashtagResponse(VALID_ID, VALID_VALUE));
        }
    }
}
