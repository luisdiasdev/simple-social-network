package br.com.agateownz.foodsocial.modules.user;

import br.com.agateownz.foodsocial.modules.content.dto.response.ContentResponse;
import br.com.agateownz.foodsocial.modules.user.dto.request.ModifyUserProfileRequest;
import br.com.agateownz.foodsocial.modules.user.dto.response.UserProfileWithPictureResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

public class UserProfileMockBuilders {

    public static final String VALID_IMAGE_URI = "http://google.com/images/image.png";
    public static final String VALID_DISPLAY_NAME = "Joseph Gordon Levitt";
    public static final String VALID_CHANGED_DISPLAY_NAME = "Joseph Gordon L.";
    public static final String VALID_CHANGED_WEBSITE = "http://google.com";
    public static final String VALID_CHANGED_BIO = "It's my life, it's now or never";
    public static final String VALID_AVATAR_COLOR = "#FF0F0F";
    public static final String VALID_PROFILE_PICTURE_UUID = "2af22cd7-1b9d-4e15-a582-2d5ad7f24472";
    public static final String VALID_PROFILE_PICTURE_URI = "http://content.com/image.png";

    public static final String INVALID_CHANGED_DISPLAY_NAME = VALID_CHANGED_DISPLAY_NAME.repeat(10);
    public static final String INVALID_CHANGED_WEBSITE = VALID_CHANGED_WEBSITE.repeat(10);
    public static final String INVALID_CHANGED_BIO = VALID_CHANGED_BIO.repeat(10);

    public static MockMultipartFile profilePictureMultipartFile() throws Exception {
        return new MockMultipartFile(
            "file",
            new ClassPathResource("data/image.png").getInputStream()
        );
    }

    public static ContentResponse profilePictureResponse() {
        return new ContentResponse(VALID_PROFILE_PICTURE_UUID, VALID_PROFILE_PICTURE_URI);
    }

    public static class UserProfileWithPictureResponseMock {

        public static UserProfileWithPictureResponse valid() {
            return UserProfileWithPictureResponse.builder()
                .avatarColor(VALID_AVATAR_COLOR)
                .displayName(VALID_DISPLAY_NAME)
                .imageUri(VALID_IMAGE_URI)
                .initials("JG")
                .build();
        }
    }

    public static class ModifyUserProfileRequestMock {

        public static ModifyUserProfileRequest valid() {
            return new ModifyUserProfileRequest(
                VALID_CHANGED_DISPLAY_NAME,
                VALID_CHANGED_WEBSITE,
                VALID_CHANGED_BIO
            );
        }

        public static ModifyUserProfileRequest invalidDisplayName() {
            return new ModifyUserProfileRequest(
                INVALID_CHANGED_DISPLAY_NAME,
                VALID_CHANGED_WEBSITE,
                VALID_CHANGED_BIO
            );
        }

        public static ModifyUserProfileRequest invalidWebsite() {
            return new ModifyUserProfileRequest(
                VALID_DISPLAY_NAME,
                INVALID_CHANGED_WEBSITE,
                VALID_CHANGED_BIO
            );
        }

        public static ModifyUserProfileRequest invalidBio() {
            return new ModifyUserProfileRequest(
                VALID_DISPLAY_NAME,
                VALID_CHANGED_WEBSITE,
                INVALID_CHANGED_BIO
            );
        }
    }
}
