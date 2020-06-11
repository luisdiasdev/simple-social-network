package br.com.agateownz.foodsocial.modules.user.dto.response;

import lombok.Data;

@Data
public class MentionUserResponse {

    private final Long id;
    private final String value;
    private final String displayName;
    private final String avatarColor;
    private final String imageUri;
}
