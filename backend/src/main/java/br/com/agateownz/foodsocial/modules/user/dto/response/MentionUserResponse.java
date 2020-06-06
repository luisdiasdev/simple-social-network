package br.com.agateownz.foodsocial.modules.user.dto.response;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class MentionUserResponse {

    private Long id;
    private String value;
    private String displayName;
    private String avatarColor;
    private String initials;
    private String imageUri;

    public MentionUserResponse(Long id,
                               String value,
                               String displayName,
                               String avatarColor,
                               String imageUri) {
        this.id = id;
        this.value = value;
        this.displayName = displayName;
        this.avatarColor = avatarColor;
        this.imageUri = imageUri;
    }
}
