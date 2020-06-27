package br.com.agateownz.foodsocial.modules.user.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileWithPictureResponse {

    private String displayName;
    private String website;
    private String bio;
    private String avatarColor;
    private String initials;
    private String imageUri;

    public static UserProfileWithPictureResponse empty() {
        return new UserProfileWithPictureResponse();
    }
}
