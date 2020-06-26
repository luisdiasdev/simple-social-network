package br.com.agateownz.foodsocial.modules.user.service;

import br.com.agateownz.foodsocial.modules.content.dto.response.ContentResponse;
import br.com.agateownz.foodsocial.modules.content.enums.ContentDiscriminator;
import br.com.agateownz.foodsocial.modules.content.service.ContentService;
import br.com.agateownz.foodsocial.modules.shared.service.AuthenticationService;
import br.com.agateownz.foodsocial.modules.shared.service.MaterialColorGeneratorService;
import br.com.agateownz.foodsocial.modules.user.dto.request.ModifyUserProfileRequest;
import br.com.agateownz.foodsocial.modules.user.dto.response.UserProfileResponse;
import br.com.agateownz.foodsocial.modules.user.dto.response.UserProfileWithPictureResponse;
import br.com.agateownz.foodsocial.modules.user.mapper.UserProfileMapper;
import br.com.agateownz.foodsocial.modules.user.model.User;
import br.com.agateownz.foodsocial.modules.user.model.UserProfile;
import br.com.agateownz.foodsocial.modules.user.repository.UserProfileRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static br.com.agateownz.foodsocial.modules.user.exceptions.UserExceptions.USER_NOT_FOUND;

@Service
public class UserProfileService {

    @Autowired
    private UserService userService;
    @Autowired
    private ContentService contentService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private MaterialColorGeneratorService materialColorGeneratorService;
    @Autowired
    private UserProfileMapper userProfileMapper;

    @Transactional
    public UserProfileResponse save(ModifyUserProfileRequest request) {
        var user = userService.findById(authenticationService.getAuthenticatedUserId());
        var profile = createOrUpdateUserProfile(request, user);
        var savedProfile = userProfileRepository.save(profile);
        return userProfileMapper.userProfileToUserProfileResponse(savedProfile);
    }

    private UserProfile createOrUpdateUserProfile(ModifyUserProfileRequest request, User user) {
        return userProfileRepository.findByUserId(authenticationService.getAuthenticatedUserId())
            .map(profile -> profile.update(request))
            .orElseGet(() -> UserProfile.of(
                request,
                user,
                materialColorGeneratorService.getRandomMaterialColor()));
    }

    @Transactional
    public ContentResponse saveProfilePicture(MultipartFile file) {
        var userId = authenticationService.getAuthenticatedUserId();
        var content = contentService.save(ContentDiscriminator.PROFILE_PICTURE, file);
        userProfileRepository.update(userId, content.getUuid());
        return new ContentResponse(content.getUuid(), content.getUri());
    }

    @Transactional
    public void removeProfilePicture() {
        var userId = authenticationService.getAuthenticatedUserId();
        var userProfile = userProfileRepository.findById(userId)
            .orElseThrow(() -> USER_NOT_FOUND);
        userProfileRepository.update(userId, null);
        contentService.delete(userProfile.getImage());
    }

    @Transactional
    public UserProfileWithPictureResponse getFromAuthenticatedUser() {
        return userProfileRepository.findByUserId(authenticationService.getAuthenticatedUserId())
            .map(userProfileMapper::userProfileToUserProfileWithPictureResponse)
            .orElseGet(UserProfileWithPictureResponse::empty);
    }
}
