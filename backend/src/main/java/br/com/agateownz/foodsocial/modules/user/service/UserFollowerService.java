package br.com.agateownz.foodsocial.modules.user.service;

import br.com.agateownz.foodsocial.modules.user.model.UserFollower;
import br.com.agateownz.foodsocial.modules.user.model.UserFollowerId;
import br.com.agateownz.foodsocial.modules.user.model.UserFollowing;
import br.com.agateownz.foodsocial.modules.user.model.UserFollowingId;
import br.com.agateownz.foodsocial.modules.user.repository.UserFollowerRepository;
import br.com.agateownz.foodsocial.modules.user.repository.UserFollowingRepository;
import br.com.agateownz.foodsocial.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static br.com.agateownz.foodsocial.modules.user.exceptions.UserExceptions.USER_NOT_FOUND;

@Service
public class UserFollowerService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserFollowerRepository userFollowerRepository;
    @Autowired
    private UserFollowingRepository userFollowingRepository;

    @Transactional
    public void follow(Long userId, Long followerId) {
        var sourceUser = userRepository.findById(userId).orElseThrow(() -> USER_NOT_FOUND);
        var targetUser = userRepository.findById(followerId).orElseThrow(() -> USER_NOT_FOUND);

        var sourceUserFollowing = UserFollowing.builder()
                .id(new UserFollowingId(sourceUser, targetUser))
                .build();
        var targetUserFollower = UserFollower.builder()
                .id(new UserFollowerId(targetUser, sourceUser))
                .build();
        userFollowerRepository.save(targetUserFollower);
        userFollowingRepository.save(sourceUserFollowing);
    }

}
