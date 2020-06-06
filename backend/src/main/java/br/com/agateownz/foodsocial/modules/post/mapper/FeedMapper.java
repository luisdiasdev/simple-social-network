package br.com.agateownz.foodsocial.modules.post.mapper;


import br.com.agateownz.foodsocial.config.mapper.MapStructConfig;
import br.com.agateownz.foodsocial.modules.post.dto.response.FeedResponse;
import br.com.agateownz.foodsocial.modules.post.model.Post;
import br.com.agateownz.foodsocial.modules.user.mapper.UserProfileMapper;
import br.com.agateownz.foodsocial.modules.user.model.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class,
        uses = {PostMapper.class, UserProfileMapper.class})
public interface FeedMapper {

    @Mapping(source = "post", target = "post")
    @Mapping(source = "userProfile", target = "user")
    FeedResponse mapFrom(Post post, UserProfile userProfile);
}
