package br.com.agateownz.foodsocial.modules.post.mapper;

import br.com.agateownz.foodsocial.config.mapper.FromJson;
import br.com.agateownz.foodsocial.config.mapper.MapStructConfig;
import br.com.agateownz.foodsocial.config.mapper.StringToMapConverter;
import br.com.agateownz.foodsocial.modules.post.dto.response.PostResponse;
import br.com.agateownz.foodsocial.modules.post.model.Post;
import br.com.agateownz.foodsocial.modules.post.model.PostContent;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class, uses = StringToMapConverter.class)
public abstract class PostMapper {

    @Mapping(source = "post.user.id", target = "userId")
    @Mapping(target = "message", qualifiedBy = FromJson.class)
    @Mapping(source = "post.pictures", target = "pictures")
    @Mapping(source = "post.createdAt", target = "createdAt")
    public abstract PostResponse postToPostResponse(Post post);

    abstract Set<String> postContentSetToStringList(Set<PostContent> pictures);

    String postContentToString(PostContent postContent) {
        return postContent.getId().getContent().getUri();
    }
}
