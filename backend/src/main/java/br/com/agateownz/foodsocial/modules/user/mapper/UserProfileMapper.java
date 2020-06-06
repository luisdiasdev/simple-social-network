package br.com.agateownz.foodsocial.modules.user.mapper;

import br.com.agateownz.foodsocial.config.mapper.MapStructConfig;
import br.com.agateownz.foodsocial.modules.user.dto.response.UserProfileResponse;
import br.com.agateownz.foodsocial.modules.user.dto.response.UserProfileWithPictureResponse;
import br.com.agateownz.foodsocial.modules.user.model.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface UserProfileMapper {

    UserProfileResponse userProfileToUserProfileResponse(UserProfile userProfile);

    @Mapping(source = "image.contentUri", target = "imageUri")
    UserProfileWithPictureResponse userProfileToUserProfileWithPictureResponse(UserProfile userProfile);
}
