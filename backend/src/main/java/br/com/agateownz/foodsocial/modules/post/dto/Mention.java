package br.com.agateownz.foodsocial.modules.post.dto;

import br.com.agateownz.foodsocial.modules.post.enums.MentionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Mention {

    private Long index;
    private Long id;
    @JsonProperty("value")
    private String name;
    @JsonProperty("denotationChar")
    private MentionType type;

    public boolean isUserMention() {
        return MentionType.USER.equals(type);
    }

    public boolean isHashtagMention() {
        return MentionType.HASHTAG.equals(type);
    }
}
