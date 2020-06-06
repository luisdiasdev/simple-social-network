package br.com.agateownz.foodsocial.modules.user.mapper;

import br.com.agateownz.foodsocial.config.mapper.MapStructConfig;
import br.com.agateownz.foodsocial.modules.user.dto.response.CreateUserResponse;
import br.com.agateownz.foodsocial.modules.user.dto.response.DefaultUserResponse;
import br.com.agateownz.foodsocial.modules.user.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface UserMapper {

    CreateUserResponse userToCreateUserResponse(User user);

    DefaultUserResponse userToDefaultUserResponse(User user);
}
