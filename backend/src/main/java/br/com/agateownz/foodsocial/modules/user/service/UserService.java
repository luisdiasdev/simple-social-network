package br.com.agateownz.foodsocial.modules.user.service;

import br.com.agateownz.foodsocial.modules.shared.service.AuthenticationService;
import br.com.agateownz.foodsocial.modules.user.dto.request.UserCreateRequest;
import br.com.agateownz.foodsocial.modules.user.dto.response.CreateUserResponse;
import br.com.agateownz.foodsocial.modules.user.dto.response.DefaultUserResponse;
import br.com.agateownz.foodsocial.modules.user.dto.response.MentionUserResponse;
import br.com.agateownz.foodsocial.modules.user.mapper.UserMapper;
import br.com.agateownz.foodsocial.modules.user.model.User;
import br.com.agateownz.foodsocial.modules.user.repository.CustomUserRepository;
import br.com.agateownz.foodsocial.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static br.com.agateownz.foodsocial.modules.user.exceptions.UserExceptions.USER_NOT_FOUND;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private CustomUserRepository customUserRepository;

    @Transactional
    public CreateUserResponse save(UserCreateRequest request) {
        var user = User.of(request);
        user.encodePassword(passwordEncoder);
        var savedUser = userRepository.save(user);
        return userMapper.userToCreateUserResponse(savedUser);
    }

    public DefaultUserResponse findByUsername(String name) {
        return userRepository.findByUsername(name)
                .map(userMapper::userToDefaultUserResponse)
                .orElseThrow(() -> USER_NOT_FOUND);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> USER_NOT_FOUND);
    }

    public List<User> findByIds(List<Long> ids) {
        return userRepository.findByIdIn(ids);
    }

    public List<MentionUserResponse> findUsersToMention(String search) {
        var searchWithLike = "%".concat(search).concat("%");
        return customUserRepository.findMentionableUsers(
                authenticationService.getAuthenticatedUserId(), searchWithLike);
    }
}
