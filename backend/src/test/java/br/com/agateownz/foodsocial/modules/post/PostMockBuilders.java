package br.com.agateownz.foodsocial.modules.post;

import br.com.agateownz.foodsocial.modules.content.dto.response.ContentResponse;
import br.com.agateownz.foodsocial.modules.post.dto.request.CreatePostRequest;
import br.com.agateownz.foodsocial.modules.post.dto.response.PostResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.mock.web.MockMultipartFile;

import static br.com.agateownz.foodsocial.modules.shared.MultipartFileMockBuilders.mockMultipartFile;

public class PostMockBuilders {

    public static final Long VALID_POST_ID = 355L;
    public static final Long VALID_POST_USER_ID = 150L;
    public static final String VALID_POST_PICTURE_UUID = "2f92d0b4-147d-4347-a716-d5a60b46acc9";
    public static final String VALID_POST_PICTURE_URI = "http://post.picture.com/image.png";
    public static final List<String> VALID_POST_PICTURE_LIST = List.of(VALID_POST_PICTURE_UUID);

    public static final Long INVALID_POST_ID = 356L;
    public static final String INVALID_POST_PICTURE_UUID = "7d086e95-0cc0-4c75-9c06-e7b76a2b6485";

    private static final Map POST_MAP = Map.of(
        "ops", List.of(
            Map.of(
                "insert", "Gandalf",
                "attributes", Map.of("bold", true)
            ),
            Map.of(
                "insert", " the "
            ),
            Map.of(
                "insert", "Gray",
                "attributes", Map.of("color", "#cccccc")
            )
        )
    );

    public static MockMultipartFile postPictureMultipartFile() throws Exception {
        return mockMultipartFile("file");
    }

    public static ContentResponse postPictureResponse() {
        return new ContentResponse(VALID_POST_PICTURE_UUID, VALID_POST_PICTURE_URI);
    }

    public static class CreatePostRequestMock {

        public static CreatePostRequest valid() {
            return new CreatePostRequest(POST_MAP, VALID_POST_PICTURE_LIST);
        }

        public static CreatePostRequest blankMessage() {
            return new CreatePostRequest(Map.of(), VALID_POST_PICTURE_LIST);
        }

        public static CreatePostRequest nullMessage() {
            return new CreatePostRequest(null, VALID_POST_PICTURE_LIST);
        }

        public static CreatePostRequest emptyPictureString() {
            return new CreatePostRequest(POST_MAP, List.of(""));
        }
    }

    public static class PostResponseMock {

        public static PostResponse create(Long id, Long userId) {
            return new PostResponse(
                id, userId, POST_MAP, Set.of(VALID_POST_PICTURE_UUID), LocalDateTime.now());
        }
    }
}
