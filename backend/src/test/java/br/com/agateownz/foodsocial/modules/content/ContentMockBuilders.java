package br.com.agateownz.foodsocial.modules.content;

import br.com.agateownz.foodsocial.modules.content.dto.response.InternalContentResponse;
import br.com.agateownz.foodsocial.modules.shared.dto.MockStoreObject;
import org.mockito.Mock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;

public class ContentMockBuilders {

    private static final String IMAGE_RESOURCE = "data/image.png";
    public static final String VALID_UUID = "fe33df83-49fe-4b71-bf6b-a726c2d18a19";
    public static final String VALID_CONTENT_TYPE = MediaType.IMAGE_PNG_VALUE;
    public static final Long VALID_CONTENT_LENGTH = 134561223L;

    public static final String INVALID_UUID = "44644b55-cb77-4e58-98cf-f35d4e2444a7";

    public static class InternalContentResponseMock {

        public static InternalContentResponse valid() {
            return new InternalContentResponse(
                VALID_CONTENT_TYPE,
                VALID_CONTENT_LENGTH,
                new MockStoreObject(new ClassPathResource(IMAGE_RESOURCE)));
        }
    }
}
