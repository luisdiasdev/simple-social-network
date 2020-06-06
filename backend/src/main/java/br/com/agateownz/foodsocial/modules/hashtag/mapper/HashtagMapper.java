package br.com.agateownz.foodsocial.modules.hashtag.mapper;

import br.com.agateownz.foodsocial.config.mapper.MapStructConfig;
import br.com.agateownz.foodsocial.modules.hashtag.dto.response.HashtagResponse;
import br.com.agateownz.foodsocial.modules.hashtag.model.Hashtag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface HashtagMapper {

    @Mapping(source = "hashtag", target = "value")
    HashtagResponse hashtagToHashtagResponse(Hashtag hashtag);
}
