package br.com.agateownz.foodsocial.modules.post.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MentionType {

    @JsonProperty("@")
    USER("@"),
    @JsonProperty("#")
    HASHTAG("#");

    private final String mentionCharacter;
}
