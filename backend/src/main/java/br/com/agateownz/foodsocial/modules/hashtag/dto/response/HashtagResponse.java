package br.com.agateownz.foodsocial.modules.hashtag.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class HashtagResponse {

    private Long id;
    private String value;
}
