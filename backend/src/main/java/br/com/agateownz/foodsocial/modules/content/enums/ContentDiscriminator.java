package br.com.agateownz.foodsocial.modules.content.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentDiscriminator {
    PROFILE_PICTURE(true),
    POST_PICTURE(false);

    private final boolean isPublic;
}
